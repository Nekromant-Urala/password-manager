package manager.encryption.sss;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import manager.encryption.sss.GF256.GF256;

public class ShamirSecretSharingGF256 {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();
    private static final GF256 GF_256 = GF256.getInstance();
    private final int n;
    private final int k;

    public ShamirSecretSharingGF256(int k, int n) {
        this.n = n;
        this.k = k;
    }

    public Map<Integer, byte[]> split(byte[] secret) {
        // generate part values
        final byte[][] values = new byte[n][secret.length];
        for (int i = 0; i < secret.length; i++) {
            // for each byte, generate a random polynomial, p
            final byte[] p = generate(k - 1, secret[i]);
            for (int x = 1; x <= n; x++) {
                // each part's byte is p(partId)
                values[x - 1][i] = eval(p, (byte) x);
            }
        }

        // return as a set of objects
        final Map<Integer, byte[]> parts = new HashMap<>(n);
        for (int i = 0; i < values.length; i++) {
            parts.put(i + 1, values[i]);
        }
        return Collections.unmodifiableMap(parts);
    }

    public byte[] join(Map<Integer, byte[]> parts) {
        final int[] lengths = parts.values().stream().mapToInt(v -> v.length).distinct().toArray();
        final byte[] secret = new byte[lengths[0]];
        for (int i = 0; i < secret.length; i++) {
            final byte[][] points = new byte[parts.size()][2];
            int j = 0;
            for (Map.Entry<Integer, byte[]> part : parts.entrySet()) {
                points[j][0] = part.getKey().byteValue();
                points[j][1] = part.getValue()[i];
                j++;
            }
            secret[i] = interpolate(points);
        }
        return secret;
    }

    private static byte eval(byte[] p, byte x) {
        // Horner's method
        byte result = 0;
        for (int i = p.length - 1; i >= 0; i--) {
            result = GF_256.add(GF_256.mul(result, x), p[i]);
        }
        return result;
    }

    private static byte[] generate(int degree, byte x) {
        final byte[] p = new byte[degree + 1];

        // generate random polynomials until we find one of the given degree
        do {
            ShamirSecretSharingGF256.SECURE_RANDOM.nextBytes(p);
        } while (GF_256.degree(p) != degree);

        // set y intercept
        p[0] = x;

        return p;
    }

    private static byte interpolate(byte[][] points) {
        // calculate f(0) of the given points using Lagrangian interpolation
        final byte x = 0;
        byte y = 0;
        for (int i = 0; i < points.length; i++) {
            final byte aX = points[i][0];
            final byte aY = points[i][1];
            byte li = 1;
            for (int j = 0; j < points.length; j++) {
                final byte bX = points[j][0];
                if (i != j) {
                    li = GF_256.mul(li, GF_256.div(GF_256.sub(x, bX), GF_256.sub(aX, bX)));
                }
            }
            y = GF_256.add(y, GF_256.mul(li, aY));
        }
        return y;
    }
}
