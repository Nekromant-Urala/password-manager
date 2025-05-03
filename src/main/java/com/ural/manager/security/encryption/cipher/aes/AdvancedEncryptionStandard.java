package com.ural.manager.security.encryption.cipher.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import com.ural.manager.security.encryption.SymmetricCipher;
import com.ural.manager.security.encryption.spec.AesGcmSpec;

import static com.ural.manager.security.encryption.spec.AesGcmSpec.AES;
import static com.ural.manager.security.encryption.spec.CipherAlgorithm.AES_MODE;

public class AdvancedEncryptionStandard implements SymmetricCipher {

    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(AES.getTagLengthBit(), nonce));

        return cipher.doFinal(byteArrayToEncrypt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws Exception {

        Cipher cipher = Cipher.getInstance(AES_MODE.getMode());
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(AES.getTagLengthBit(), nonce));

        return cipher.doFinal(byteArrayToDecrypt);
    }

    @Override
    public String getName() {
        return AES_MODE.getName();
    }

    @Override
    public AesGcmSpec getSpec() {
        return AES;
    }
}
