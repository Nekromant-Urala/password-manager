package manager.generator.passsword;

public enum Symbols {
    CHAR_LOWERCASE("abcdefghijklmnopqrstuvwxyz", 26),
    CHAR_UPPERCASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ", 26),
    DIGIT("0123456789", 10),
    SPECIAL_SYMBOL("~$^+=<>", 7),
    PUNCTUATION_SYMBOL("!@#&()–[{}]:;',?/*—", 19),
    ALL_SYMBOLS(CHAR_LOWERCASE.getSymbols()
            + CHAR_UPPERCASE.getSymbols()
            + DIGIT.getSymbols()
            + SPECIAL_SYMBOL.getSymbols()
            + PUNCTUATION_SYMBOL.getSymbols(),
            87
    );

    private final String symbols;
    private final int countChars;

    Symbols(String symbols, int countChars) {
        this.symbols = symbols;
        this.countChars = countChars;
    }

    public String getSymbols() {
        return symbols;
    }

    public int getCountChars() {
        return countChars;
    }
}
