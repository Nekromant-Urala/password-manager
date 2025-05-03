package com.ural.manager.security.encryption.service;

import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

import com.ural.manager.security.encryption.RandomGenerator;
import com.ural.manager.security.encryption.SymmetricCipher;
import com.ural.manager.security.encryption.factory.CipherFactory;
import com.ural.manager.security.encryption.factory.KeyGeneratorFactory;
import com.ural.manager.security.encryption.key.SecretKeyGenerator;
import com.ural.manager.security.encryption.spec.CipherAlgorithm;
import com.ural.manager.security.encryption.spec.KeyGenerator;


public class EncryptionService {
    private final int iterationCount;
    private final SymmetricCipher cipher;
    private final SecretKeyGenerator keyGenerator;
    private final RandomGenerator generator = arrayLength -> {
        byte[] nonce = new byte[arrayLength];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    };

    public EncryptionService(CipherAlgorithm cipher, KeyGenerator keyGenerator, int iterationCount) {
        this.cipher = CipherFactory.createCipher(cipher);
        this.keyGenerator = KeyGeneratorFactory.createKeyGenerator(keyGenerator);
        this.iterationCount = iterationCount;
    }

    /**
     * @param password       пароль (данные), которые необходимо зашифровать
     * @param masterPassword мастер-пароль, с помощью которого создается секретный ключ. Обязательно нужен
     * @return Возвращает зашифрованные данные в виде массива байт в кодировке BASE64
     * @throws Exception
     */
    public byte[] encrypt(byte[] password, String masterPassword) throws Exception {
        // создание случайных последовательностей байт для "соли" и IV (nonce)
        byte[] salt = generator.getRandomBytes(cipher.getSpec().getSaltLengthByte());
        byte[] nonce = generator.getRandomBytes(cipher.getSpec().getNonceLengthByte());

        // создание секретного ключа
        SecretKey key = keyGenerator.generateSecretKey(masterPassword.toCharArray(), salt, iterationCount, cipher.getName());
        // шифрование пароля
        byte[] encryptedText = cipher.encrypt(password, key, nonce);
        byte[] encryptedTextWithMeta = ByteBuffer.allocate(nonce.length + salt.length + encryptedText.length)
                .put(salt)
                .put(nonce)
                .put(encryptedText)
                .array();

        return Base64.getEncoder().encode(encryptedTextWithMeta);
    }

    /**
     * @param encryptedTextWithMeta зашифрованный пароль(данные), которые необходимо расшифровать
     * @param masterPassword        мастер-пароль, с помощью которого создается секретный ключ. Обязательно нужен
     * @return Возвращает расшифрованные данные в виде массива байт
     * @throws Exception
     */
    public byte[] decrypt(byte[] encryptedTextWithMeta, String masterPassword) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedTextWithMeta);
        ByteBuffer buffer = ByteBuffer.wrap(decoded);

        // извлекаем "соль"
        byte[] salt = new byte[cipher.getSpec().getSaltLengthByte()];
        buffer.get(salt);
        byte[] nonce = new byte[cipher.getSpec().getNonceLengthByte()];
        buffer.get(nonce);
        byte[] encryptedPassword = new byte[buffer.remaining()];
        buffer.get(encryptedPassword);

        // создаем секретный ключ
        SecretKey key = keyGenerator.generateSecretKey(masterPassword.toCharArray(), salt, iterationCount, cipher.getName());
        return cipher.decrypt(encryptedPassword, key, nonce);
    }
}
