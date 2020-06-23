package crt;

import java.util.Arrays;

public class Vector {

    public double[] state;

    public Vector(double... state) {
        this.state = state;
    }

    public Vector innerProduct(Vector... vectors) {
        if (vectors.length == 0) throw new IllegalStateException("That Vector length is ZERO");
        if (vectors.length == 1) return innerProduct(this, vectors[0]);
        return innerProduct(this, vectors);
    }

    public static boolean isError(double d) {
        return d == 0 || Double.isNaN(d) || Double.isInfinite(d);
    }

    public static Vector innerProduct(Vector vector0, Vector vector1) {
        if (vector0 == null || vector1 == null) return new Vector(0);
        int length0 = vector0.state.length;
        int length1 = vector1.state.length;
        if (length0 != length1) throw new IllegalArgumentException("It mismatch length of vector.");
        double[] doubles = new double[length0];
        for (int len = 0; len < doubles.length; ++len) {
            if (isError(vector0.state[len]) || isError(vector1.state[len])) continue;
            doubles[len] = vector0.state[len] * vector0.state[len];
        }
        return new Vector(doubles);
    }

    @Deprecated
    public static Vector innerProduct(Vector vector, Vector...vectors) {
        if (vectors.length == 0 || vector == null) return vector;
        int length = vector.state.length;
        boolean[] product = new boolean[length];
        for (int len = 0; len < vectors.length; ++len) {
            if (vectors[len].state.length != length)
                throw new IllegalArgumentException("It mismatch that's length of vectors element.");
            for (int pos = 0; pos < product.length; ++pos) {
                if (product[pos]) continue;
                product[pos] = vectors[len].state[pos] == 0;
            }
        }
        double[] doubles = new double[product.length];
        int count = 0;
        for (int len = 0; len < product.length; ++len) if (product[len]) ++count;
        if (count == product.length - 1) {
            Arrays.fill(doubles, 0);
            return new Vector(doubles);
        }
        Arrays.fill(doubles, 1);
        for (int len = 0; len < vectors.length; ++len) {
            for (int pos = 0; pos < length; ++len) {
                if (!product[pos]) {
                    doubles[pos] *= vectors[len].state[pos];
                    product[pos] = isError(doubles[pos]);
                }
            }
        }
        return new Vector(doubles);
    }
}
