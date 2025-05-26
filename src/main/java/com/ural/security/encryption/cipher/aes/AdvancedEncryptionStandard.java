package com.ural.security.encryption.cipher.aes;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

import com.ural.security.encryption.SymmetricCipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.ural.security.encryption.cipher.aes.AesSpec.AES;
import static com.ural.security.encryption.spec.CipherAlgorithm.AES_MODE;

public class AdvancedEncryptionStandard implements SymmetricCipher {

    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(AES_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(AES.getTagLengthBit(), nonce));

        return cipher.doFinal(byteArrayToEncrypt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(AES_MODE.getMode());
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(AES.getTagLengthBit(), nonce));

        return cipher.doFinal(byteArrayToDecrypt);
    }

    @Override
    public String getName() {
        return AES_MODE.getName();
    }

    @Override
    public AesSpec getSpec() {
        return AES;
    }
}
