package manager.encryption.abss;


import java.math.BigInteger;
import java.security.SecureRandom;

public class AsmuthBloomSecretSharingGFP {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private final int n;
    private final int t;

    public AsmuthBloomSecretSharingGFP(int n, int t) {
        this.n = n;
        this.t = t;
    }

    public ShareGFP[] splitSecret(byte[] secret) {
        BigInteger s = new BigInteger(secret);
        BigInteger p = BigInteger.probablePrime(s.bitLength() + 10, SECURE_RANDOM);
        BigInteger[] d = generatePrimes(p, n);
        BigInteger sDash = getsDash(d, p, s);

        ShareGFP[] shares = new ShareGFP[n];
        for (int i = 0; i < n; i++) {
            BigInteger k = sDash.mod(d[i]);
            shares[i] = new ShareGFP(p, d[i], k);
        }
        return shares;
    }

    private BigInteger getsDash(BigInteger[] d, BigInteger p, BigInteger s) {
        BigInteger leftPart = BigInteger.ONE;

        for (int i = 0; i < t; i++) {
            leftPart = leftPart.multiply(d[i]);
        }

        BigInteger rightPart = p;
        for (int i = n - t + 1; i < n; i++) {
            rightPart = rightPart.multiply(d[i]);
        }

        BigInteger temp = rightPart.divide(p);
        BigInteger r;
        BigInteger sDash;
        int numBits = p.bitLength();

        do {
            r = new BigInteger(numBits, SECURE_RANDOM);
            sDash = s.add(r.multiply(p));
            numBits++;
        } while (sDash.compareTo(temp) <= 0);
        return sDash;
    }

    private static BigInteger[] generatePrimes(BigInteger p, int n) {
        BigInteger[] primes = new BigInteger[n];
        int bitLength = p.bitLength() + 10;
        for (int i = 0; i < primes.length; i++) {
            primes[i] = BigInteger.probablePrime(bitLength, SECURE_RANDOM);
            bitLength++;
        }
        return primes;
    }

    public byte[] recoverSecret(ShareGFP... shares) {
        BigInteger p = shares[0].getP();
        BigInteger[] remainders = new BigInteger[shares.length];
        BigInteger[] modules = new BigInteger[shares.length];

        for (int i = 0; i < shares.length; i++) {
            remainders[i] = shares[i].getK();
            modules[i] = shares[i].getD();
        }

        BigInteger sDash = crt(remainders, modules);
        BigInteger s = sDash.mod(p);
        return s.toByteArray();
    }

    private static BigInteger crt(BigInteger[] remainders, BigInteger[] modules) {
        BigInteger module = BigInteger.ONE;
        for (BigInteger i : modules) {
            module = module.multiply(i);
        }

        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < remainders.length; i++) {
            BigInteger temp = module.divide(modules[i]);
            BigInteger tempInverse = temp.modInverse(modules[i]);
            result = result.add(remainders[i].multiply(temp).multiply(tempInverse)).mod(module);
        }
        return result;
    }
}
