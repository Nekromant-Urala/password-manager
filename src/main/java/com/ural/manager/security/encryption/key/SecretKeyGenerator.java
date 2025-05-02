package com.ural.manager.security.encryption.key;

import javax.crypto.SecretKey;

public interface SecretKeyGenerator {
    /**
     * @param keyWord ключевое слово, по которому будет создаваться секретный ключ
     * @param salt    "соль" уникальная случайная последовательность бай
     * @return Возвращает секретный ключ необходимы для шифрования и дешифрования данных
     */
    SecretKey generateSecretKey(byte[] keyWord, byte[] salt);
}
