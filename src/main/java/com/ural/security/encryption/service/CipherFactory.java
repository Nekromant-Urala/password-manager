package com.ural.security.encryption.service;

import com.ural.security.encryption.SymmetricCipher;
import com.ural.security.encryption.cipher.aes.AdvancedEncryptionStandard;
import com.ural.security.encryption.cipher.chacha20.ChaCha20;
import com.ural.security.encryption.cipher.des.TripleDataEncryptionStandard;
import com.ural.security.encryption.cipher.twofish.TwoFish;
import com.ural.security.encryption.spec.CipherAlgorithm;

import static com.ural.security.encryption.spec.CipherAlgorithm.*;


public class CipherFactory {
    private static final String AES = "AES";
    private static final String CHACHA20 = "ChaCha20";
    private static final String TWOFISH = "TwoFish";
    private static final String DES = "3DES";


    /**
     * @param algorithm наименование алгоритма, который хотите применить
     * @return Возвращает объект класса алгоритма шифрования
     */
    public static SymmetricCipher createCipher(CipherAlgorithm algorithm) {
        return switch (algorithm) {
            case AES_MODE -> new AdvancedEncryptionStandard();
            case CHACHA20_MODE -> new ChaCha20();
            case TWOFISH_MODE -> new TwoFish();
            case DES_MODE -> new TripleDataEncryptionStandard();
        };
    }

    /**
     *
     * @param nameCipher наименование алгоритма шифрования
     * @return Возвращает конфигурацию алгоритма в виде Enumerate
     */
    public static CipherAlgorithm getNameCipher(String nameCipher){
        return switch (nameCipher) {
            case AES -> AES_MODE;
            case CHACHA20 -> CHACHA20_MODE;
            case TWOFISH -> TWOFISH_MODE;
            case DES -> DES_MODE;
            default -> throw new IllegalArgumentException("Данный алгоритм не поддреживается" + nameCipher);
        };
    }
}
