package password.generator;

public enum Symbols {
    CHAR_LOWERCASE("abcdefghijklmnopqrstuvwxyz"),
    CHAR_UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    DIGIT("0123456789"),
    SPECIAL_SYMBOL("~$^+=<>"),
    PUNCTUATION_SYMBOL("!@#&()–[{}]:;',?/*—"),
    ALL_SYMBOLS(CHAR_LOWERCASE.getSymbols()
            + CHAR_UPPERCASE.getSymbols()
            + DIGIT.getSymbols()
            + SPECIAL_SYMBOL.getSymbols()
            + PUNCTUATION_SYMBOL.getSymbols()
    );

    private final String symbols;

    Symbols(String symbols) {
        this.symbols = symbols;
    }

    public String getSymbols() {
        return symbols;
    }
}
