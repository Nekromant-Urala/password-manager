package com.ural.manager.security.encryption.key;

import javax.crypto.SecretKey;

public class Argon2KeyGenerator implements SecretKeyGenerator{
    @Override
    public SecretKey generateSecretKey(byte[] keyWord, byte[] salt) {
        return null;
    }
}
