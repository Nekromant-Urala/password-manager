package com.ural.security.encryption.cipher.chacha20;

import com.ural.security.encryption.spec.AlgorithmSpec;

public enum ChaCha20Spec implements AlgorithmSpec {
    CHACHA20(256, 12, 16, 12);

    private final int keyLengthBit;
    private final int nonceLengthByte;
    private final int macLengthByte;
    private final int saltLengthByte;

    ChaCha20Spec(int keyLengthBit, int nonceLengthByte, int macLengthByte, int saltLengthByte) {
        this.keyLengthBit = keyLengthBit;
        this.nonceLengthByte = nonceLengthByte;
        this.macLengthByte = macLengthByte;
        this.saltLengthByte = saltLengthByte;
    }

    @Override
    public int getKeyLengthBit() {
        return keyLengthBit;
    }

    @Override
    public int getNonceLengthByte() {
        return nonceLengthByte;
    }

    @Override
    public int getSaltLengthByte() {
        return saltLengthByte;
    }

    public int getMacLengthByte() {
        return macLengthByte;
    }
}
