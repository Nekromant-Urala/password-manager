package com.ural.manager.security.key.separation.abss;

public class ShareGF256 {
    private final byte d;
    private final byte k;

    public ShareGF256(byte d, byte k) {
        this.d = d;
        this.k = k;
    }

    public byte getD() {
        return d;
    }

    public byte getK() {
        return k;
    }

    @Override
    public String toString() {
        return "Share{" +
                "d=" + d +
                ", k=" + k +
                '}';
    }
}
