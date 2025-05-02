package com.ural.manager.security.encryption.aes;

import com.ural.manager.security.encryption.RandomGenerator;
import com.ural.manager.security.encryption.SymmetricCipher;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

import static com.ural.manager.security.encryption.spec.AesGcmSpec.AES;
import static com.ural.manager.security.encryption.spec.CipherAlgorithm.AES_MODE;

public class AdvancedEncryptionStandard implements SymmetricCipher {
    private final RandomGenerator generator = arrayLength -> {
        byte[] nonce = new byte[arrayLength];
        new SecureRandom().nextBytes(nonce);
        return nonce;
    };

    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key) throws Exception {
        byte[] salt = generator.getRandomBytes(AES.getSaltLengthByte());
        byte[] iv = generator.getRandomBytes(AES.getIvLengthByte());

        Cipher cipher = Cipher.getInstance(AES_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(AES.getTagLengthBit(), iv));

        byte[] encryptedText = cipher.doFinal(byteArrayToEncrypt);
        byte[] encryptedTextWithSalt = ByteBuffer.allocate(iv.length + salt.length + encryptedText.length)
                .put(iv)
                .put(salt)
                .put(encryptedText)
                .array();

        return Base64.getEncoder().encode(encryptedTextWithSalt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key) throws Exception {
        byte[] encryptedData = Base64.getDecoder().decode(byteArrayToDecrypt);

        ByteBuffer bb = ByteBuffer.wrap(encryptedData);
        byte[] iv = new byte[AES.getIvLengthByte()];
        byte[] salt = new byte[AES.getSaltLengthByte()];
        byte[] encryptedText = new byte[bb.remaining()];

        bb.get(iv);
        bb.get(salt);
        bb.get(encryptedText);

        Cipher cipher = Cipher.getInstance(AES_MODE.getMode());
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(AES.getTagLengthBit(), iv));

        return cipher.doFinal(byteArrayToDecrypt);
    }
}
