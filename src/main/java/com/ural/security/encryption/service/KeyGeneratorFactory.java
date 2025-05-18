package com.ural.security.encryption.service;

import com.ural.security.encryption.key.Argon2KeyGenerator;
import com.ural.security.encryption.key.PBKDF2KeyGenerator;
import com.ural.security.encryption.key.SecretKeyGenerator;
import com.ural.security.encryption.spec.KeyGenerator;

public class KeyGeneratorFactory {
    /**
     * @param keyGenerator наименование алгоритма, который хотите применить.
     * @return Возвращает объект класса алгоритма создания секретного ключа
     */
    public static SecretKeyGenerator createKeyGenerator(KeyGenerator keyGenerator) {
        return switch (keyGenerator.getName()) {
            case "Argon2" -> new Argon2KeyGenerator();
            case "PBKDF2" -> new PBKDF2KeyGenerator();
            default ->
                    throw new IllegalArgumentException("Данный алгоритм не поддерживается: " + keyGenerator.getName());
        };
    }

    public static KeyGenerator getKeyGenerator(String nameGenerator) {
        return switch (nameGenerator) {
            case "Argon2" -> KeyGenerator.ARGON2;
            case "PBKDF2" -> KeyGenerator.PBKDF2;
            default -> throw new IllegalArgumentException("Данный алгоритм не поддерживается: " + nameGenerator);
        };
    }
}
