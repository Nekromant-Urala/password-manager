package com.ural.manager.security.encryption.factory;

import com.ural.manager.security.encryption.SymmetricCipher;
import com.ural.manager.security.encryption.cipher.aes.AdvancedEncryptionStandard;
import com.ural.manager.security.encryption.cipher.chacha20.ChaCha20;
import com.ural.manager.security.encryption.spec.CipherAlgorithm;


public class CipherFactory {
    /**
     * @param algorithm наименование алгоритма, который хотите применить
     * @return Возвращает объект класса алгоритма шифрования
     */
    public static SymmetricCipher createCipher(CipherAlgorithm algorithm) {
        return switch (algorithm.getName()) {
            case "AES" -> new AdvancedEncryptionStandard();
            case "ChaCha20" -> new ChaCha20();
            default -> throw new IllegalArgumentException("Данный алгоритм не поддерживается: " + algorithm.getName());
        };
    }
}
