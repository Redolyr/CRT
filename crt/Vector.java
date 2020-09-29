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
        if (vectors.length == 0) throw new IllegalStateException("this array length is 0");
        if (vectors.length == 1) return innerProduct(this, vectors[0]);
        return innerProduct0(this, vectors);
    }

    public Numeric add(int index, Numeric numeric) {
        return this.state[index].add(numeric);
    }

    public Numeric sub(int index, Numeric numeric) {
        return this.state[index].sub(numeric);
    }

    public Numeric multiply(int index, Numeric numeric) {
        return this.state[index].multiply(numeric);
    }

    public Numeric division(int index, Numeric numeric) {
        return this.state[index].division(numeric);
    }

    public Numeric modulo(int index, Numeric numeric) {
        return this.state[index].modulo(numeric);
    }

    public Numeric takeModulo(int index, Numeric target) {
        return this.state[index].takeModulo(target);
    }

    public Numeric negate(int index) {
        return this.state[index].negate();
    }

    public Numeric abs(int index) {
        return this.state[index].abs();
    }

    public boolean isNaN(int index) {
        return this.state[index].isNaN();
    }

    public boolean isZero(int index) {
        return this.state[index].isZero();
    }

    public boolean isInfinite(int index) {
        return this.state[index].isInfinite();
    }

    public int compareTo(int index, Numeric o) {
        return this.state[index].compareTo(o);
    }

    public Vector add(int index, Vector vector, int index0) {
        Vector vector1 = new Vector(this.state);
        vector1.state[index] = this.state[index].add(vector.state[index0]);
        return vector1;
    }

    public Vector sub(int index, Vector vector, int index0) {
        Vector vector1 = new Vector(this.state);
        vector1.state[index] = this.state[index].sub(vector.state[index0]);
        return vector1;
    }

    public Vector multiply(int index, Vector vector, int index0) {
        Vector vector1 = new Vector(this.state);
        vector1.state[index] = this.state[index].multiply(vector.state[index0]);
        return vector1;
    }

    public Vector division(int index, Vector vector, int index0) {
        Vector vector1 = new Vector(this.state);
        vector1.state[index] = this.state[index].division(vector.state[index0]);
        return vector1;
    }

    public Vector modulo(int index, Vector vector, int index0) {
        Vector vector1 = new Vector(this.state);
        vector1.state[index] = this.state[index].modulo(vector.state[index0]);
        return vector1;
    }

    public Vector takeModulo(int index, Vector vector, int index0) {
        Vector vector1 = new Vector(this.state);
        vector1.state[index] = this.state[index].takeModulo(vector.state[index0]);
        return vector1;
    }

    public int compareTo(int index, Vector vector, int index0) {
        return this.state[index].compareTo(vector.state[index0]);
    }

    public Numeric sum() {
        return sum(this);
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
        if (length0 != length1) throw new IllegalArgumentException("vector wrong length.");
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
                throw new IllegalArgumentException("vector wrong length of element.");
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

    public static Numeric sum(Vector vector) {
        Numeric sum = new Numeric(0);
        for (Numeric numeric : vector.state) sum = sum.add(numeric);
        return sum;
    }

    public static Numeric allProduct(Vector vector) {
        for (Numeric numeric : vector.state) if (isError(numeric)) return new Numeric(0);
        Numeric numeric = new Numeric(1);
        for (Numeric numeric0 : vector.state) numeric = numeric.multiply(numeric0);
        return numeric;
    }
}
