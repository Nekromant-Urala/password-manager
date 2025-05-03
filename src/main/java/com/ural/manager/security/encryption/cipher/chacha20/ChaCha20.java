package com.ural.manager.security.encryption.cipher.chacha20;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import com.ural.manager.security.encryption.SymmetricCipher;
import com.ural.manager.security.encryption.spec.ChaCha20Spec;

import static com.ural.manager.security.encryption.spec.ChaCha20Spec.CHACHA20;
import static com.ural.manager.security.encryption.spec.CipherAlgorithm.CHACHA20_MODE;

public class ChaCha20 implements SymmetricCipher {
    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws Exception {

        Cipher cipher = Cipher.getInstance(CHACHA20_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(nonce));

        return cipher.doFinal(byteArrayToEncrypt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws Exception {

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
