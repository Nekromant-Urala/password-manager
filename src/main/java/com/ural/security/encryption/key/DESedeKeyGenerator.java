package com.ural.security.encryption.key;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.ural.security.encryption.spec.KeyGenerator.DES;

public class DESedeKeyGenerator implements SecretKeyGenerator {

    @Override
    public SecretKey generateSecretKey(char[] keyWord, byte[] salt, int iterationCount, String algorithmName) throws Exception {
        byte[] key = Arrays.copyOf(convertArray(keyWord), 24);
        DESedeKeySpec keySpec = new DESedeKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES.getMode());
        return keyFactory.generateSecret(keySpec);
    }

    private byte[] convertArray(char[] chars) {
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode(CharBuffer.wrap(chars));
        byte[] bytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(bytes);

        Arrays.fill(byteBuffer.array(), (byte) 0);
        return bytes;
    }
}
