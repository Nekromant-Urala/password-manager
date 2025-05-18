package com.ural.security.generator.passsword;

public enum Symbols {
    LOWERCASE("abcdefghijklmnopqrstuvwxyz"),
    UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    DIGIT("0123456789"),
    MINUS("-"),
    UNDERSCORE("_"),
    SPACE(" "),
    STAPLE("[]{}()<>"),
    SPECIAL("~$^+=!@#&:;',?/*");

    private final String symbols;

    Symbols(String symbols) {
        this.symbols = symbols;
    }

    public String getSymbols() {
        return symbols;
    }
}
