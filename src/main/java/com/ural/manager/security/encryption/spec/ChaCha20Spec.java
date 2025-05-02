package com.ural.manager.security.encryption.spec;

public enum ChaCha20Spec {
    CHACHA20(256, 12, 16);

    private final int keyLengthBit;
    private final int nonceLengthByte;
    private final int macLengthByte;

    ChaCha20Spec(int keyLengthBit, int nonceLengthByte, int macLengthByte) {
        this.keyLengthBit = keyLengthBit;
        this.nonceLengthByte = nonceLengthByte;
        this.macLengthByte = macLengthByte;
    }

    public int getKeyLengthBit() {
        return keyLengthBit;
    }

    public int getNonceLengthByte() {
        return nonceLengthByte;
    }

    public int getMacLengthByte() {
        return macLengthByte;
    }
}
