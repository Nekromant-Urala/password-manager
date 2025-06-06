package com.ural.security.encryption.cipher.twofish;

import com.ural.security.encryption.SymmetricCipher;
import com.ural.security.encryption.spec.AlgorithmSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import static com.ural.security.encryption.cipher.twofish.TwofishSpec.TWOFISH;
import static com.ural.security.encryption.spec.CipherAlgorithm.TWOFISH_MODE;

public class Twofish implements SymmetricCipher {

    @Override
    public byte[] encrypt(byte[] byteArrayToEncrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Security.addProvider(new BouncyCastleProvider());

        Cipher cipher = Cipher.getInstance(TWOFISH_MODE.getMode());
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TWOFISH.getTagLengthBit(), nonce));

        return cipher.doFinal(byteArrayToEncrypt);
    }

    @Override
    public byte[] decrypt(byte[] byteArrayToDecrypt, SecretKey key, byte[] nonce) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(TWOFISH_MODE.getMode());
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TWOFISH.getTagLengthBit(), nonce));

        return cipher.doFinal(byteArrayToDecrypt);
    }

    @Override
    public String getName() {
        return TWOFISH_MODE.getName();
    }

    @Override
    public AlgorithmSpec getSpec() {
        return TWOFISH;
    }
}
