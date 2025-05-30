package com.ural.security.encryption.cipher.chacha20;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import com.ural.security.encryption.SymmetricCipher;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.ural.security.encryption.cipher.chacha20.ChaCha20Spec.CHACHA20;
import static com.ural.security.encryption.spec.CipherAlgorithm.CHACHA20_MODE;

public class ChaCha20 implements SymmetricCipher {
    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(CHACHA20_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(nonce));

        return cipher.doFinal(byteArrayToEncrypt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(CHACHA20_MODE.getMode());
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nonce));

        return cipher.doFinal(byteArrayToDecrypt);
    }

    @Override
    public String getName() {
        return CHACHA20_MODE.getName();
    }

    @Override
    public ChaCha20Spec getSpec() {
        return CHACHA20;
    }
}
