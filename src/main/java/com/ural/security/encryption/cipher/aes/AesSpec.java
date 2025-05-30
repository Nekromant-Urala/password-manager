package com.ural.security.encryption.cipher.aes;

import com.ural.security.encryption.spec.AlgorithmSpec;

public enum AesSpec implements AlgorithmSpec {
    AES(256, 128, 12, 16);

    private final int keyLengthBit;
    private final int ivLengthByte;
    private final int saltLengthByte;
    private final int tagLengthBit;


    AesSpec(int keyLengthBit, int tagLengthBit, int ivLengthByte, int saltLengthByte) {
        this.keyLengthBit = keyLengthBit;
        this.ivLengthByte = ivLengthByte;
        this.saltLengthByte = saltLengthByte;
        this.tagLengthBit = tagLengthBit;
    }

    @Override
    public int getKeyLengthBit() {
        return keyLengthBit;
    }

    @Override
    public int getNonceLengthByte() {
        return ivLengthByte;
    }

    @Override
    public int getSaltLengthByte() {
        return saltLengthByte;
    }

    public int getTagLengthBit() {
        return tagLengthBit;
    }
}
