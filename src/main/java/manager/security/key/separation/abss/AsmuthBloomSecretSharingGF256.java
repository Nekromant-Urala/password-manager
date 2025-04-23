package manager.security.key.separation.abss;


import manager.security.key.separation.GF256.GF256;

import java.security.SecureRandom;
import java.util.Arrays;

public class AsmuthBloomSecretSharingGF256 {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final GF256 GF_256 = new GF256();
    private final int n;
    private final int t;

    public AsmuthBloomSecretSharingGF256(int n, int t) {
        this.n = n;
        this.t = t;
    }

    public ShareGF256[][] split(byte[] secret) {
        ShareGF256[][] shares = new ShareGF256[secret.length][n];

        for (int i = 0; i < secret.length; i++) {
            ShareGF256[] byteShares = splitByte(secret[i]);
            shares[i] = byteShares;
        }

        return shares;
    }

    private ShareGF256[] splitByte(byte secretByte) {
        byte[] coefficients = new byte[t];
        coefficients[0] = secretByte;

        for (int i = 1; i < t; i++) {
            coefficients[i] = generateRandomByte();
        }

        byte[] d = generateDistinctElements(n);

        ShareGF256[] shares = new ShareGF256[n];
        for (int i = 0; i < n; i++) {
            byte x = d[i];
            byte y = coefficients[0];
            for (int j = 1; j < t; j++) {
                y = GF_256.add(y, GF_256.mul(coefficients[j], power(x, j)));
            }
            shares[i] = new ShareGF256(x, y);
        }
        return shares;
    }

    public byte[] join(ShareGF256[]... allShares) {
        if (allShares == null || allShares.length == 0) {
            throw new IllegalArgumentException("Нет долей для восстановления секрета.");
        }

        byte[] recoveredSecret = new byte[allShares.length];

        for (int i = 0; i < allShares.length; i++) {
            recoveredSecret[i] = recoverByte(Arrays.copyOf(allShares[i], t)); // Берем первые t долей
        }

        return recoveredSecret;
    }

    private byte recoverByte(ShareGF256[] shares) {
        if (shares.length < t) {
            throw new IllegalArgumentException("Недостаточно долей для восстановления секрета.");
        }

        byte[] xValues = new byte[shares.length];
        byte[] yValues = new byte[shares.length];

        for (int i = 0; i < shares.length; i++) {
            xValues[i] = shares[i].getD();
            yValues[i] = shares[i].getK();
        }

        return lagrangeInterpolation(xValues, yValues);
    }

    private byte power(byte base, int exponent) {
        byte result = 1;
        for (int i = 0; i < exponent; i++) {
            result = GF_256.mul(result, base);
        }
        return result;
    }

    private byte[] generateDistinctElements(int n) {
        byte[] elements = new byte[n];
        for (int i = 0; i < n; i++) {
            byte element;
            do {
                element = generateRandomByte();
            } while (element == 0 || contains(elements, element, i));
            elements[i] = element;
        }
        return elements;
    }

    private boolean contains(byte[] array, byte element, int index) {
        for (int i = 0; i < index; i++) {
            if (array[i] == element) {
                return true;
            }
        }
        return false;
    }

    private byte lagrangeInterpolation(byte[] xValues, byte[] yValues) {
        final byte x = 0;
        byte result = 0;
        for (int i = 0; i < xValues.length; i++) {
            byte term = yValues[i];
            for (int j = 0; j < xValues.length; j++) {
                if (i != j) {
                    byte numerator = GF_256.sub(x, xValues[j]);
                    byte denominator = GF_256.sub(xValues[i], xValues[j]);
                    term = GF_256.mul(term, GF_256.div(numerator, denominator));
                }
            }
            result = GF_256.add(result, term);
        }
        return result;
    }

    private byte generateRandomByte() {
        byte[] bytes = new byte[1];
        SECURE_RANDOM.nextBytes(bytes);
        return bytes[0];
    }
}
