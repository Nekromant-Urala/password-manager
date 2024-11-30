package password.entropy;

public enum Symbols {
    CHAR_LOWERCASE(26),
    CHAR_UPPERCASE(26),
    DIGIT(10),
    SPECIAL_SYMBOL(7),
    PUNCTUATION_SYMBOL(19);

    private final int numberOfCharacters;

    Symbols(int numberOfCharacters) {
        this.numberOfCharacters = numberOfCharacters;
    }

    public int gatNumberOfCharacters() {
        return numberOfCharacters;
    }
}
