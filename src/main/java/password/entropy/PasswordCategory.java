package password.entropy;

public enum PasswordCategory {
    POOR("плохой пароль"),
    WEAK("слабый пароль"),
    REASONABLE("неплохой пароль"),
    VERY_GOOD("очень хороший пароль");

    private final String category;

    PasswordCategory(String category) {
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}
