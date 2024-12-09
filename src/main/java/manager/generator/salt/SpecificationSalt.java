package manager.generator.salt;

public enum SpecificationSalt {
    SALT_LENGTH(20),
    DIGIT_COUNT(4),
    CHAR_UPPERCASE_COUNT(4),
    CHAR_LOWERCASE_COUNT(4),
    SPECIAL_SYMBOL_COUNT(4),
    PUNCTUATION_SYMBOL_COUNT(4);

    private final int count;

    SpecificationSalt(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
