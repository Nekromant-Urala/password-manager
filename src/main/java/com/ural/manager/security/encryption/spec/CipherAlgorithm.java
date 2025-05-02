package com.ural.manager.security.encryption.spec;

public enum CipherAlgorithm {
    AES_MODE("AES", "AES/GCM/NoPadding"),
    PBKDF2_MODE("PBKDF2", "PBKDF2WithHmacSHA256"),
    CHACHA20_MODE("ChaCha20", "ChaCha20-Poly1305");

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
