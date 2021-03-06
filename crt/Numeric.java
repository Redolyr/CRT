package crt;

public class Numeric extends Number implements Comparable<Numeric> {

    public double value;

    protected Numeric() {
    }

    public Numeric(double value) {
        this.value = value;
    }

    protected Numeric toValue() {
        return this;
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

    public Numeric add(Numeric numeric) {
        return new Numeric(this.value + numeric.value);
    }

    public Numeric sub(Numeric numeric) {
        return new Numeric(this.value - numeric.value);
    }

    public Numeric multiply(Numeric numeric) {
        return new Numeric(this.value * numeric.toValue().value);
    }

    public Numeric division(Numeric numeric) {
        return new Numeric(this.value / numeric.value);
    }

    /**
     * Memo: (x+y)/z = b + (x - bz + y)/z  (x is int, y is float, 0 < y < 1, z is any number).<br>
     * 'b + (x - bz + y) % z' = 'x - bz + y'.<br>
     * if z = 1, return y.<br>
     *
     * 'A % B', value name numeric is B, here on A.<br>
     * @param numeric
     * @return
     */
    public Numeric modulo(Numeric numeric) {
        double d = this.value % numeric.value;
        d = d - numeric.value * ((Math.sqrt(d * d) / d - 1) / 2);
        if (Double.isNaN(d) || Double.isInfinite(d) || d == 0) return new Numeric(0);
        return new Numeric(d);
    }

    /**
     * 'A % B', target is A, on B in here.<br>
     *     If not implement in deprecate.
     * @param target
     * @return
     */
    @Deprecated
    public Numeric takeModulo(Numeric target) {
        return target.modulo(this);
    }

    public Numeric negate() {
        return new Numeric(-this.value);
    }

    public Numeric abs() {
        return this.value > 0 ? this : this.negate();
    }

    public boolean isNaN() {
        return Double.isNaN(this.value);
    }

    public boolean isZero() {
        return this.value == 0;
    }

    public boolean isInfinite() {
        return Double.isInfinite(this.value);
    }

    public int compareTo(Numeric o) {
        Numeric numeric = this.sub(o);
        return numeric.toValue().value > 0 ? 1 : numeric.isZero() ? 0 : -1;
    }

    public boolean equals(Object obj) {
        if (obj.getClass().equals(boolean.class)) return false;
        if (obj.getClass().isPrimitive()) return this.value == (double) obj;
        if (obj instanceof Numeric) return ((Numeric) obj).value == this.value;
        return false;
    }

    public String toString() {
        return Double.toString(this.value);
    }

    public static Numeric[] makeNumerics(double... doubles) {
        Numeric[] numerics = new Numeric[doubles.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = new Numeric(doubles[len]);
        return numerics;
    }
}
