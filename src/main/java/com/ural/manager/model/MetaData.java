package com.ural.manager.model;

public class MetaData {
    private String encryptAlgorithm;
    private String keyGenerator;
    private int iterations;
    private String encryptedPasswordWithMeta;
    private String nameDatabase;

    private MetaData() {
    }

    public static class Builder {
        private MetaData metaData;

        public Builder() {
            this.metaData = new MetaData();
        }

        public Builder encryptAlgorithm(String encryptAlgorithm) {
            metaData.encryptAlgorithm = encryptAlgorithm;
            return this;
        }

        public Builder keyGenerator(String keyGenerator) {
            metaData.keyGenerator = keyGenerator;
            return this;
        }

        public Builder iterations(int iterations) {
            metaData.iterations = iterations;
            return this;
        }

        public Builder encryptedPasswordWithMeta(String encryptedPasswordWithMeta) {
            metaData.encryptedPasswordWithMeta = encryptedPasswordWithMeta;
            return this;
        }

        public Builder nameDatabase(String nameDatabase) {
            metaData.nameDatabase = nameDatabase;
            return this;
        }

        public MetaData build() {
            return metaData;
        }

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

    public String getEncryptedPasswordWithMeta() {
        return encryptedPasswordWithMeta;
    }

    public String getNameDatabase() {
        return nameDatabase;
    }
}
