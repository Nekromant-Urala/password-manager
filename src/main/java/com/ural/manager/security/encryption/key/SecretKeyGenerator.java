package com.ural.manager.security.encryption.key;

import javax.crypto.SecretKey;

@FunctionalInterface
public interface SecretKeyGenerator {
    /**
     * @param keyWord        ключевое слово, по которому будет создаваться секретный ключ
     * @param salt           "соль" уникальная случайная последовательность бай
     * @param iterationCount количество итераций, которое должен совершить алгоритм
     * @param algorithmName  наименование алгоритма, который необходимо применить для генерации секретного ключа
     * @return Возвращает секретный ключ необходимы для шифрования и дешифрования данных
     */
    SecretKey generateSecretKey(char[] keyWord, byte[] salt, int iterationCount, String algorithmName) throws Exception;
}
