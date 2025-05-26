package com.ural.security.encryption.service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.ural.security.encryption.RandomGenerator;
import com.ural.security.encryption.SymmetricCipher;
import com.ural.security.encryption.cipher.aes.AesSpec;
import com.ural.security.encryption.key.SecretKeyGenerator;
import com.ural.security.encryption.spec.CipherAlgorithm;
import com.ural.security.encryption.spec.KeyGenerator;
import org.bouncycastle.openssl.EncryptionException;


public class EncryptionService {
    private final int iterationCount;
    private final SymmetricCipher cipher;
    private final SecretKeyGenerator keyGenerator;
    private static final int DEFAULT_ITERATIONS = 30_000;
    private static final RandomGenerator generator = arrayLength -> {
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
     * @param data       пароль (данные), которые необходимо зашифровать
     * @param masterPassword мастер-пароль, с помощью которого создается секретный ключ. Обязательно нужен
     * @return Возвращает зашифрованные данные в виде массива байт в кодировке BASE64
     * @throws Exception
     */
    public byte[] encrypt(byte[] data, char[] masterPassword) throws EncryptionException, InvalidKeySpecException, NoSuchAlgorithmException, KeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        // создание случайных последовательностей байт для "соли" и IV (nonce)
        byte[] salt = generator.getRandomBytes(cipher.getSpec().getSaltLengthByte());
        byte[] nonce = generator.getRandomBytes(cipher.getSpec().getNonceLengthByte());

        // создание секретного ключа
        SecretKey key = keyGenerator.generateSecretKey(masterPassword, salt, iterationCount, cipher.getName());
        // шифрование пароля
        byte[] encryptedText = cipher.encrypt(data, key, nonce);
        byte[] encryptedTextWithMeta = ByteBuffer.allocate(nonce.length + salt.length + encryptedText.length)
                .put(salt)
                .put(nonce)
                .put(encryptedText)
                .array();

        return Base64.getEncoder().encode(encryptedTextWithMeta);
    }

    /**
     * Метод предназначен для воссоздания хэша пароля, хранящегося в базе
     *
     * @param userPassword пароль введенный пользователем в окно инициализации
     * @param salt         "соль", при которой создавался секретный ключ
     * @param nonce        IV, который использовался при шифровании пароля
     * @return Возвращает массив байт полученный при заданных данных
     */
    public byte[] encrypt(char[] userPassword, byte[] salt, byte[] nonce) throws EncryptionException, InvalidKeySpecException,
            NoSuchAlgorithmException, KeyException,
            InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        // создание секретного ключа
        SecretKey key = keyGenerator.generateSecretKey(userPassword, salt, iterationCount, cipher.getName());
        // воссоздание хеша
        return cipher.encrypt(convertArray(userPassword), key, nonce);
    }

    private byte[] convertArray(char[] chars) {
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars));
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }

    /**
     * @param passwordWithMeta хеш пароля хранящегося в базе
     * @return Возвращает лист массивов байт. В такой же последовательности, в которой они хранятся
     */
    public List<byte[]> getSaltAndNonceFromMeta(String passwordWithMeta) {
        byte[] decode = Base64.getDecoder().decode(passwordWithMeta);
        ByteBuffer buffer = ByteBuffer.wrap(decode);
        byte[] salt = new byte[cipher.getSpec().getSaltLengthByte()];
        buffer.get(salt);
        byte[] nonce = new byte[cipher.getSpec().getNonceLengthByte()];
        buffer.get(nonce);
        byte[] password = new byte[buffer.remaining()];
        buffer.get(password);
        buffer.clear();
        return List.of(salt, nonce, password);
    }

    /**
     * @param encryptedTextWithMeta зашифрованный пароль(данные), которые необходимо расшифровать
     * @param masterPassword        мастер-пароль, с помощью которого создается секретный ключ. Обязательно нужен
     * @return Возвращает расшифрованные данные в виде массива байт
     * @throws Exception
     */
    public byte[] decrypt(byte[] encryptedTextWithMeta, char[] masterPassword) throws KeyException, EncryptionException,
            InvalidKeySpecException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
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
        SecretKey key = keyGenerator.generateSecretKey(masterPassword, salt, iterationCount, cipher.getName());
        return cipher.decrypt(encryptedPassword, key, nonce);
    }

    public static byte[] encryptDefault(byte[] data, char[] masterPassword) throws EncryptionException, InvalidKeySpecException, NoSuchAlgorithmException,
            KeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        // создание случайных последовательностей байт для "соли" и IV (nonce)
        byte[] salt = generator.getRandomBytes(AesSpec.AES.getSaltLengthByte());
        byte[] nonce = generator.getRandomBytes(AesSpec.AES.getNonceLengthByte());

        // создание секретного ключа
        SecretKey key = KeyGeneratorFactory.createKeyGenerator(KeyGenerator.PBKDF2)
                .generateSecretKey(masterPassword, salt, DEFAULT_ITERATIONS, CipherAlgorithm.AES_MODE.getName());
        // шифрование пароля
        byte[] encryptedText = CipherFactory.createCipher(CipherAlgorithm.AES_MODE).encrypt(data, key, nonce);
        byte[] encryptedTextWithMeta = ByteBuffer.allocate(nonce.length + salt.length + encryptedText.length)
                .put(salt)
                .put(nonce)
                .put(encryptedText)
                .array();

        return Base64.getEncoder().encode(encryptedTextWithMeta);
    }

    public static byte[] decryptDefault(byte[] encryptedTextWithMeta, char[] masterPassword) throws KeyException, EncryptionException,
            InvalidKeySpecException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] decoded = Base64.getDecoder().decode(encryptedTextWithMeta);
        ByteBuffer buffer = ByteBuffer.wrap(decoded);

        // извлекаем "соль"
        byte[] salt = new byte[AesSpec.AES.getSaltLengthByte()];
        buffer.get(salt);
        byte[] nonce = new byte[AesSpec.AES.getNonceLengthByte()];
        buffer.get(nonce);
        byte[] encryptedPassword = new byte[buffer.remaining()];
        buffer.get(encryptedPassword);

        // создаем секретный ключ
        SecretKey key = KeyGeneratorFactory.createKeyGenerator(KeyGenerator.PBKDF2)
                .generateSecretKey(masterPassword, salt, DEFAULT_ITERATIONS, CipherAlgorithm.AES_MODE.getName());
        return CipherFactory.createCipher(CipherAlgorithm.AES_MODE).decrypt(encryptedPassword, key, nonce);
    }
}
