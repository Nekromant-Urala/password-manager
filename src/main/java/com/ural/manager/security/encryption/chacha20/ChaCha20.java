package com.ural.manager.security.encryption.chacha20;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

import com.ural.manager.security.encryption.RandomGenerator;
import com.ural.manager.security.encryption.SymmetricCipher;

import static com.ural.manager.security.encryption.spec.ChaCha20Spec.CHACHA20;
import static com.ural.manager.security.encryption.spec.CipherAlgorithm.CHACHA20_MODE;

public class ChaCha20 implements SymmetricCipher {
    private final RandomGenerator generator = arrayLength -> {
        byte[] nonce = new byte[arrayLength];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    };

    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key) throws Exception {
        byte[] nonce = generator.getRandomBytes(CHACHA20.getNonceLengthByte());

        Cipher cipher = Cipher.getInstance(CHACHA20_MODE.getMode());

        IvParameterSpec iv = new IvParameterSpec(nonce);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] encryptedText = cipher.doFinal(byteArrayToEncrypt);
        byte[] output = ByteBuffer.allocate(byteArrayToEncrypt.length + CHACHA20.getNonceLengthByte())
                .put(encryptedText)
                .put(nonce)
                .array();

        return Base64.getEncoder().encode(output);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key) throws Exception {
        byte[] encryptedData = Base64.getDecoder().decode(byteArrayToDecrypt);

        ByteBuffer bb = ByteBuffer.wrap(encryptedData);

        byte[] encryptedText = new byte[encryptedData.length - CHACHA20.getNonceLengthByte()];
        byte[] nonce = new byte[CHACHA20.getKeyLengthBit()];
        bb.get(encryptedText);
        bb.get(nonce);

        Cipher cipher = Cipher.getInstance(CHACHA20_MODE.getMode());

        IvParameterSpec iv = new IvParameterSpec(nonce);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        return cipher.doFinal(encryptedText);
    }
}
