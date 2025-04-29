package com.ural.manager.security.key.encryption.aes;

public enum Specification {
    KEY_LENGTH(256),
    ITERATION_COUNT(10000),
    PBKDF2WithHmacSHA256("PBKDF2WithHmacSHA256"),
    AES("AES"),
    AES_MODE("AES/CBC/PKCS5Padding");

    private int number;
    private String spec;

    Specification(int number) {
        this.number = number;
    }

    Specification(String spec) {
        this.spec = spec;
    }

    public String getSpec() {
        return spec;
    }

    public int getNumber() {
        return number;
    }
}
