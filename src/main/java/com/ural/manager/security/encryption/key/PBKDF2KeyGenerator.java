package com.ural.manager.security.encryption.key;


import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.spec.KeySpec;

import static com.ural.manager.security.encryption.spec.KeyGenerator.PBKDF2;

public class PBKDF2KeyGenerator implements SecretKeyGenerator {
    private static final int keyLength = 256;

    @Override
    public SecretKey generateSecretKey(char[] keyWord, byte[] salt, int iterationCount, String algorithmName) throws Exception {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2.getMode());
        KeySpec spec = new PBEKeySpec(keyWord, salt, iterationCount, keyLength);
        return new SecretKeySpec(keyFactory.generateSecret(spec).getEncoded(), algorithmName);
    }
}
