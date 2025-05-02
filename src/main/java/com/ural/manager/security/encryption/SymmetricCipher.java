package com.ural.manager.security.encryption;

import javax.crypto.SecretKey;
import java.security.SecureRandom;

public interface SymmetricCipher {

    /**
     * @param byteArrayToEncrypt передаётся строку, которую необходимо зашифровать
     * @param key                секретный (закрытый) ключ для шифровки текста
     * @return Возвращает зашифрованный текст в виде массива байт в кодировке BASE64
     * @throws Exception
     */
    byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key) throws Exception;

    /**
     * @param byteArrayToDecrypt передаётся зашифрованная строка, которую необходимо расшифровать
     * @param key                секретный (закрытый) ключ для шифровки текста
     * @return Возвращает расшифрованный текст в виде массива байт в кодировке BASE64
     * @throws Exception
     */
    byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key) throws Exception;
}
