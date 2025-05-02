package com.ural.manager.security.separation.abss;

import java.math.BigInteger;

public class ShareGFP {

    private BigInteger p;
    private BigInteger d;
    private BigInteger k;

    public ShareGFP(BigInteger p, BigInteger d, BigInteger k) {
        this.p = p;
        this.d = d;
        this.k = k;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getD() {
        return d;
    }

    public BigInteger getK() {
        return k;
    }

    @Override
    public String toString() {
        return "Part{" +
                "p=" + p +
                ", d=" + d +
                ", k=" + k +
                "}";
    }
}