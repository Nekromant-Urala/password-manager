package com.ural.manager.security.encryption;

import com.ural.manager.security.encryption.spec.AesGcmSpec;
import com.ural.manager.security.encryption.spec.AlgorithmSpec;
import com.ural.manager.security.encryption.spec.CipherAlgorithm;

import javax.crypto.SecretKey;
import java.security.SecureRandom;

public interface SymmetricCipher {

    /**
     * @param byteArrayToEncrypt передаётся строку, которую необходимо зашифровать
     * @param key                секретный (закрытый) ключ для шифровки текста
     * @param nonce              уникальная последовательность байт (IV)
     * @return Возвращает зашифрованный текст в виде массива байт
     * @throws Exception
     */
    byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws Exception;

    /**
     * @param byteArrayToDecrypt передаётся зашифрованная строка, которую необходимо расшифровать
     * @param key                секретный (закрытый) ключ для шифровки текста
     * @param nonce              уникальная последовательность байт (IV)
     * @return Возвращает расшифрованный текст в виде массива байт
     * @throws Exception
     */
    byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws Exception;

    /**
     * @return Возвращает название алгоритма в виде строки
     */
    String getName();

    /**
     * @return Возвращает конфигурацию алгоритма в виде Enumerate
     */
    AlgorithmSpec getSpec();
}
