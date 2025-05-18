package com.ural.security.encryption.cipher.twofish;

import com.ural.security.encryption.spec.AlgorithmSpec;

public enum TwoFishSpec implements AlgorithmSpec {
    TWOFISH(256, 128, 12, 16);

    private final int keyLengthBit;
    private final int tagLengthBit;
    private final int saltLengthByte;
    private final int ivLengthByte;

    TwoFishSpec(int keyLengthBit, int tagLengthBit, int ivLengthByte, int saltLengthByte) {
        this.tagLengthBit = tagLengthBit;
        this.keyLengthBit = keyLengthBit;
        this.ivLengthByte = ivLengthByte;
        this.saltLengthByte = saltLengthByte;
    }

    public int getTagLengthBit() {
        return tagLengthBit;
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
}
