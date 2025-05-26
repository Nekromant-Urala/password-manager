package com.ural.security.encryption;

import com.ural.security.encryption.spec.AlgorithmSpec;
import org.bouncycastle.openssl.EncryptionException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface SymmetricCipher {

    /**
     * @param byteArrayToEncrypt передаётся строку, которую необходимо зашифровать
     * @param key                секретный (закрытый) ключ для шифровки текста
     * @param nonce              уникальная последовательность байт (IV)
     * @return Возвращает зашифрованный текст в виде массива байт
     * @throws Exception
     */
    byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws EncryptionException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    /**
     * @param byteArrayToDecrypt передаётся зашифрованная строка, которую необходимо расшифровать
     * @param key                секретный (закрытый) ключ для шифровки текста
     * @param nonce              уникальная последовательность байт (IV)
     * @return Возвращает расшифрованный текст в виде массива байт
     * @throws Exception
     */
    byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws EncryptionException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException;

    /**
     * @return Возвращает название алгоритма в виде строки
     */
    String getName();

    /**
     * @return Возвращает конфигурацию алгоритма в виде Enumerate
     */
    AlgorithmSpec getSpec();
}
