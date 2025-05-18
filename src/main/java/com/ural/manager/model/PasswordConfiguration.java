package com.ural.manager.model;

public class PasswordConfiguration {
    private int length = 20;
    private int count;
    private boolean lowerCase;
    private boolean upperCase;
    private boolean digits;
    private boolean minus;
    private boolean underscore;
    private boolean space;
    private boolean specialSymbols;
    private boolean staples;

    public static class Builder {
        private PasswordConfiguration config;

        public Builder() {
            this.config = new PasswordConfiguration();
        }

        public Builder addLength(int length) {
            config.length = length;
            return this;
        }

        public Builder addLowerCase(boolean checkMark) {
            config.count++;
            config.lowerCase = checkMark;
            return this;
        }

        public Builder addUpperCase(boolean checkMark) {
            config.count++;
            config.upperCase = checkMark;
            return this;
        }

        public Builder addDigits(boolean checkMark) {
            config.count++;
            config.digits = checkMark;
            return this;
        }

        public Builder addMinus(boolean checkMark) {
            config.count++;
            config.minus = checkMark;
            return this;
        }

        public Builder addUnderscore(boolean checkMark) {
            config.count++;
            config.underscore = checkMark;
            return this;
        }

        public Builder addSpace(boolean checkMark) {
            config.count++;
            config.space = checkMark;
            return this;
        }

        public Builder addSpecialSymbols(boolean checkMark) {
            config.count++;
            config.specialSymbols = checkMark;
            return this;
        }

        public Builder addStaples(boolean checkMark) {
            config.count++;
            config.staples = checkMark;
            return this;
        }

        public PasswordConfiguration build() {
            return config;
        }
    }

    public int getCount() {
        return count;
    }

    public int getLength() {
        return length;
    }

    public boolean isLowerCase() {
        return lowerCase;
    }

    public boolean isUpperCase() {
        return upperCase;
    }

    public boolean isDigits() {
        return digits;
    }

    public boolean isMinus() {
        return minus;
    }

    public boolean isUnderscore() {
        return underscore;
    }

    public boolean isSpace() {
        return space;
    }

    public boolean isSpecialSymbols() {
        return specialSymbols;
    }

    public boolean isStaples() {
        return staples;
    }
}
