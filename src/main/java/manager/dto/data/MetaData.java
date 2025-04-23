package manager.dto.data;

public class MetaData {
    private String encryption;
    private String keyDerivation;
    private long iterations;
    private String salt;
    private String iv;

    public String getEncryption() {
        return encryption;
    }

    public void setEncryption(String encryption) {
        this.encryption = encryption;
    }

    public String getKeyDerivation() {
        return keyDerivation;
    }

    public void setKeyDerivation(String keyDerivation) {
        this.keyDerivation = keyDerivation;
    }

    public long getIterations() {
        return iterations;
    }

    public void setIterations(long iterations) {
        this.iterations = iterations;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
