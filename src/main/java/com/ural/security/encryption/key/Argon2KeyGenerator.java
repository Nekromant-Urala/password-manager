package com.ural.security.encryption.key;


import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Argon2KeyGenerator implements SecretKeyGenerator {
    private static final int memoryLimit = 131_072;
    private static final int hashLength = 32;
    private static final int parallelism = 2;

    @Override
    public SecretKey generateSecretKey(char[] keyWord, byte[] salt, int iterationCount, String algorithmName) throws Exception {
        Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_i)
                .withVersion(Argon2Parameters.ARGON2_VERSION_13)
                .withIterations(iterationCount)
                .withMemoryAsKB(memoryLimit)
                .withParallelism(parallelism)
                .withSalt(salt);

        Argon2BytesGenerator generator = new Argon2BytesGenerator();
        generator.init(builder.build());
        byte[] key = new byte[hashLength];
        generator.generateBytes(keyWord, key, 0, key.length);

        return new SecretKeySpec(key, algorithmName);
    }
}
