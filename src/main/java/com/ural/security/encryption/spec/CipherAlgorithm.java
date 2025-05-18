package com.ural.security.encryption.spec;

public enum CipherAlgorithm {
    AES_MODE("AES", "AES/GCM/NoPadding"),
    CHACHA20_MODE("ChaCha20", "ChaCha20-Poly1305"),
    TWOFISH_MODE("TwoFish", "TwoFish/GCM/NoPadding"),
    DES_MODE("3DES", "DESede/CFB8/NoPadding");

    private final String name;
    private final String mode;

    CipherAlgorithm(String name, String mode) {
        this.name = name;
        this.mode = mode;
    }

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }
}
