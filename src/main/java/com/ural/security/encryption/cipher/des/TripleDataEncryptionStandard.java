package com.ural.security.encryption.cipher.des;

import com.ural.security.encryption.SymmetricCipher;
import com.ural.security.encryption.spec.AlgorithmSpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.ural.security.encryption.cipher.des.DesSpec.DES;
import static com.ural.security.encryption.spec.CipherAlgorithm.DES_MODE;

public class TripleDataEncryptionStandard implements SymmetricCipher {

    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(DES_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(nonce));

        return cipher.doFinal(byteArrayToEncrypt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(DES_MODE.getMode());
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(nonce));

        return cipher.doFinal(byteArrayToDecrypt);
    }

    @Override
    public String getName() {
        return DES_MODE.getName();
    }

    @Override
    public AlgorithmSpec getSpec() {
        return DES;
    }
}
