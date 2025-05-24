package com.ural.security.encryption.spec;

public enum KeyGenerator {
    ARGON2("Argon2", "Argon2i"),
    PBKDF2("PBKDF2", "PBKDF2WithHmacSHA256"),
    DES("3DES", "DESede");

    private final String name;
    private final String mode;

    KeyGenerator(String name, String mode){
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
