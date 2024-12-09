package manager.encryption;

public enum SpecificationAES {
    KEY_LENGTH(256),
    ITERATION_COUNT(1024);

    private final int number;

    SpecificationAES(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
