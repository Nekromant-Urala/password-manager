package com.ural.manager.model;

public class SettingsData {
    private static final int DEFAULT_ITERATION_PBKDF2 = 600_000;

    private String masterPassword;
    private String nameFile;
    private String pathFile;
    private String description;
    private String encryptAlgorithm;
    private String keyGenerator;
    private int iterations;

    private SettingsData() {

    }

    public static class Builder {
        private SettingsData settings;

        public Builder() {
            this.settings = new SettingsData();
        }

        public Builder masterPassword(String masterPassword) {
            settings.masterPassword = masterPassword;
            return this;
        }

        public Builder nameFile(String nameFile) {
            settings.nameFile = nameFile;
            return this;
        }

        public Builder pathFile(String pathFile) {
            settings.pathFile = pathFile;
            return this;
        }

        public Builder description(String description) {
            settings.description = description;
            return this;
        }

        public Builder encryptAlgorithm(String encryptAlgorithm) {
            settings.encryptAlgorithm = encryptAlgorithm;
            return this;
        }

        public Builder keyGenerator(String keyGenerator) {
            settings.keyGenerator = keyGenerator;
            return this;
        }

        public Builder iterations(int iterations) {
            settings.iterations = iterations;
            return this;
        }

        public SettingsData build() {
            if (settings.iterations <= 0) {
                iterations(DEFAULT_ITERATION_PBKDF2);
            }
            return settings;
        }
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public String getNameFile() {
        return nameFile;
    }

    public String getPathFile() {
        return pathFile;
    }

    public String getDescription() {
        return description;
    }

    public String getEncryptAlgorithm() {
        return encryptAlgorithm;
    }

    public String getKeyGenerator() {
        return keyGenerator;
    }

    public int getIterations() {
        return iterations;
    }

    @Override
    public String toString() {
        return "SettingsData{" +
                "masterPassword='" + masterPassword + '\'' +
                ", nameFile='" + nameFile + '\'' +
                ", pathFile='" + pathFile + '\'' +
                ", description='" + description + '\'' +
                ", encryptAlgorithm='" + encryptAlgorithm + '\'' +
                ", keyGenerator='" + keyGenerator + '\'' +
                ", iterations=" + iterations +
                '}';
    }
}
