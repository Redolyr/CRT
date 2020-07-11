package crt;

import java.util.Arrays;

public class Vector {

    public Numeric[] state;

    public Vector(double... state) {
        this.state = Numeric.makeNumerics(state);
    }

    public Vector(Numeric... state) {
        this.state = state;
    }


    public Numeric innerProduct(Vector... vectors) {
        if (vectors.length == 0) throw new IllegalStateException("That Vector length is ZERO");
        if (vectors.length == 1) return innerProduct(this, vectors[0]);
        return innerProduct0(this, vectors);
    }

    public String toString() {
        String string = "";
        for (Numeric numeric : this.state) string += numeric.toString() + ", ";
        return "{" + string.substring(0, string.length() - 2) + "}";
    }

    public static boolean isError(Numeric numeric) {
        return numeric.isZero() || numeric.isNaN() || numeric.isInfinite();
    }

    public static Numeric innerProduct(Vector vector0, Vector vector1) {
        if (vector0 == null || vector1 == null) return new Numeric(0);
        int length0 = vector0.state.length;
        int length1 = vector1.state.length;
        if (length0 != length1) throw new IllegalArgumentException("It mismatch length of vector.");
        Numeric[] doubles = new Numeric[length0];
        for (int len = 0; len < doubles.length; ++len) {
            if (isError(vector0.state[len]) || isError(vector1.state[len])) continue;
            doubles[len] = vector0.state[len].multiply(vector1.state[len]);
        }
        return sum(new Vector(doubles));
    }

    @Deprecated
    public static Numeric innerProduct0(Vector vector, Vector...vectors) {
        if (vectors.length == 0 || vector == null) return sum(vector);
        int length = vector.state.length;
        boolean[] product = new boolean[length];
        for (int len = 0; len < vectors.length; ++len) {
            if (vectors[len].state.length != length)
                throw new IllegalArgumentException("It mismatch that's length of vectors element.");
            for (int pos = 0; pos < product.length; ++pos) {
                if (product[pos]) continue;
                product[pos] = vectors[len].state[pos].isZero();
            }
        }
        Numeric[] doubles = new Numeric[product.length];
        int count = 0;
        for (int len = 0; len < product.length; ++len) if (product[len]) ++count;
        if (count == product.length - 1) {
            Arrays.fill(doubles, 0);
            return sum(new Vector(doubles));
        }
        Arrays.fill(doubles, 1);
        for (int pos = 0; pos < length; ++pos) {
            if (!product[pos]) {
                doubles[pos] = doubles[pos].multiply(vector.state[pos]);
                product[pos] = isError(doubles[pos]);
            }
        }
        for (int len = 0; len < vectors.length; ++len) {
            for (int pos = 0; pos < length; ++pos) {
                if (!product[pos]) {
                    doubles[pos] = doubles[pos].multiply(vector.state[pos]);
                    product[pos] = isError(doubles[pos]);
                }
            }
        }
        return sum(new Vector(doubles));
    }

    public static long roundDown(double value) {
        return (long) (value - 0.5);
    }

    public static Numeric sum(Vector vector) {
        Numeric sum = new Numeric(0);
        for (Numeric numeric : vector.state) sum = sum.add(numeric);
        return sum;
    }

    public static Numeric allProduct(Vector vector) {
        for (Numeric numeric : vector.state) if (numeric.equals(new Numeric(0))) return new Numeric(0);
        Numeric numeric = new Numeric(1);
        for (Numeric numeric0 : vector.state) numeric = numeric.multiply(numeric0);
        return numeric;
    }

    public static Numeric allProductWithMptPositions(Vector vector, int... positions) {
        int min = 0;
        int max = 0;
        for (int pos : positions) {
            if (min > pos) min = pos;
            if (max < pos) max = pos;
        }
        for (int len = 0; len < vector.state.length; ++len) {
            Numeric numeric = vector.state[len];
            if (len < min && len > max && numeric.equals(new Numeric(0))) return new Numeric(0);
            else if (len >= min && len <= max)
                for (int pos : positions) if (pos != len && numeric.equals(new Numeric(0))) return new Numeric(0);
        }
        Numeric numeric = new Numeric(1);
        for (Numeric numeric0 : vector.state) numeric = numeric.multiply(numeric0);
        return numeric;
    }
}
