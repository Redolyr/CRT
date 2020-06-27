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
        for (int pos = 0; pos < length; ++pos) {
            if (!product[pos]) {
                doubles[pos] *= vector.state[pos];
                product[pos] = isError(doubles[pos]);
            }
        }
        for (int len = 0; len < vectors.length; ++len) {
            for (int pos = 0; pos < length; ++pos) {
                if (!product[pos]) {
                    doubles[pos] *= vectors[len].state[pos];
                    product[pos] = isError(doubles[pos]);
                }
            }
        }
        return new Vector(doubles);
    }

    public static long roundDown(double value) {
        return (long) (value - 0.5);
    }

    public static class Vec {

        public long times;

        public double value;

        public Vec(long times, double value) {
            this.times = times;
            this.value = value;
        }

    }

    public static long[] continuedFraction(double value) {
        long[] longs = new long[0];

        double value0 = value;

        while (true) {
            longs = Arrays.copyOf(longs, longs.length + 1);
            double v0 = value0 % 1;
            long v1 = (long) (value0 - v0);
            longs[longs.length - 1] = v1;
            if (v0 == 0) break;
            value0 = 1 / v0;
        }
        return longs;
    }

    public static class Fraction extends Number {

        public Numeric nominator;

        public Numeric denominator;

        public Fraction(double nominator, double denominator) {
            this.nominator = new Numeric(nominator);
            this.denominator = new Numeric(denominator);
        }

        public Fraction(Numeric nominator, Numeric denominator) {
            this.nominator = nominator;
            this.denominator = denominator;
        }

        public Fraction(double value) {
            long[] longs = continuedFraction(value);
            long nom = 1;
            long den = 1;
            long temp = 0;

            int index = longs.length - 1;
            den = longs[index];
            while (index > -1) {
                nom += den * longs[--index];
                temp = nom;
                nom = den;
                den = temp;
            }
            this.nominator = new Numeric(nom);
            this.denominator = new Numeric(den);
        }

        public int intValue() {
            return (int) this.longValue();
        }

        public long longValue() {
            double nominator = this.nominator.doubleValue();
            double denominator = this.denominator.doubleValue();
            return nominator < denominator ? (nominator * 2 > denominator || nominator > denominator / 2) ? 1 : 0 : (long) ((nominator - (nominator % denominator)) / denominator);
        }

        public float floatValue() {
            return (float) this.doubleValue();
        }

        public double doubleValue() {
            return this.nominator.doubleValue() / this.denominator.doubleValue();
        }
    }

    public static class Numeric extends Number {

        public double value;

        public Numeric(double value) {
            this.value = value;
        }

        @Deprecated
        public int intValue() {
            return (int) this.value;
        }

        public long longValue() {
            return (long) this.value;
        }

        @Deprecated
        public float floatValue() {
            return (float) this.value;
        }

        public double doubleValue() {
            return this.value;
        }
    }
}
