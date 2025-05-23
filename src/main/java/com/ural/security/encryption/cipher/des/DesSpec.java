package com.ural.security.encryption.cipher.des;

import com.ural.security.encryption.spec.AlgorithmSpec;

public enum DesSpec implements AlgorithmSpec {
    DES(192, 8, 16);

    private final int keyLengthBit;
    private final int ivLengthByte;
    private final int saltLengthByte;

    DesSpec(int keyLengthBit, int ivLengthByte, int saltLengthByte) {
        this.keyLengthBit = keyLengthBit;
        this.ivLengthByte = ivLengthByte;
        this.saltLengthByte = saltLengthByte;
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
