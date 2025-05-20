package com.ural.manager.model;

import java.util.ArrayList;
import java.util.List;

public class PasswordConfiguration {
    private static final int DEFAULT_LENGTH = 20;

    private int length;
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
            config.lowerCase = checkMark;
            return this;
        }

        public Builder addUpperCase(boolean checkMark) {
            config.upperCase = checkMark;
            return this;
        }

        public Builder addDigits(boolean checkMark) {
            config.digits = checkMark;
            return this;
        }

        public Builder addMinus(boolean checkMark) {
            config.minus = checkMark;
            return this;
        }

        public Builder addUnderscore(boolean checkMark) {
            config.underscore = checkMark;
            return this;
        }

        public Builder addSpace(boolean checkMark) {
            config.space = checkMark;
            return this;
        }

        public Builder addSpecialSymbols(boolean checkMark) {
            config.specialSymbols = checkMark;
            return this;
        }

        public Builder addStaples(boolean checkMark) {
            config.staples = checkMark;
            return this;
        }

        public PasswordConfiguration build() {
            if (config.length <= 0) {
                addLength(DEFAULT_LENGTH);
            }
            return config;
        }
    }

    public int getEnabledOptions() {
        int count = 0;

        if (isLowerCase()) count++;
        if (isUpperCase()) count++;
        if (isDigits()) count++;
        if (isMinus()) count++;
        if (isUnderscore()) count++;
        if (isSpace()) count++;
        if (isSpecialSymbols()) count++;
        if (isStaples()) count++;

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
