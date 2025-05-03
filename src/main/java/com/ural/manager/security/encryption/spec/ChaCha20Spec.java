package com.ural.manager.security.encryption.spec;

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

    public int getKeyLengthBit() {
        return keyLengthBit;
    }

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
