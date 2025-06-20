package com.ural.security.encryption.key;


import javax.crypto.SecretKey;
import java.security.KeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@FunctionalInterface
public interface SecretKeyGenerator {
    /**
     * @param keyWord        ключевое слово, по которому будет создаваться секретный ключ
     * @param salt           "соль" уникальная случайная последовательность бай
     * @param iterationCount количество итераций, которое должен совершить алгоритм
     * @param algorithmName  наименование алгоритма, который применяется для шифрования данных
     * @return Возвращает секретный ключ необходимы для шифрования и дешифрования данных
     */
    SecretKey generateSecretKey(char[] keyWord, byte[] salt, int iterationCount, String algorithmName) throws KeyException, InvalidKeySpecException, NoSuchAlgorithmException;
}
