package com.ural.manager.security.encryption.spec;

public enum AesGcmSpec {
    AES(256, 128, 12, 16);

    private final int keyLengthBit;
    private final int ivLengthByte;
    private final int saltLengthByte;
    private final int tagLengthBit;


    AesGcmSpec(int keyLengthBit, int tagLengthBit, int ivLengthByte, int saltLengthByte) {
        this.keyLengthBit = keyLengthBit;
        this.ivLengthByte = ivLengthByte;
        this.saltLengthByte = saltLengthByte;
        this.tagLengthBit = tagLengthBit;
    }

    public int getKeyLengthBit() {
        return keyLengthBit;
    }

    public int getIvLengthByte() {
        return ivLengthByte;
    }

    public int getSaltLengthByte() {
        return saltLengthByte;
    }

    public int getTagLengthBit() {
        return tagLengthBit;
    }
}
