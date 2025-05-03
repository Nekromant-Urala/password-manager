package com.ural.manager.security.encryption.factory;

import com.ural.manager.security.encryption.key.Argon2KeyGenerator;
import com.ural.manager.security.encryption.key.PBKDF2KeyGenerator;
import com.ural.manager.security.encryption.key.SecretKeyGenerator;
import com.ural.manager.security.encryption.spec.KeyGenerator;

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
}
