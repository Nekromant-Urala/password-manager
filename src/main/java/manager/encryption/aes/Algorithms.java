package manager.encryption.aes;

public enum Algorithms {
    PBKDF2WithHmacSHA256("PBKDF2WithHmacSHA256"),
    AES("AES"),
    AES_MODE("AES/CBC/PKCS5Padding");

    private final String nameAlgorithm;

    Algorithms(String nameAlgorithm) {
        this.nameAlgorithm = nameAlgorithm;
    }

    public String getNameAlgorithm() {
        return nameAlgorithm;
    }
}
