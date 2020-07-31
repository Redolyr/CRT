package crt;

import java.util.Arrays;

public class Fraction extends Numeric {

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
        while (index > 0) {
            nom += den * longs[--index];
            temp = nom;
            nom = den;
            den = temp;
        }
        System.out.println(longs.length + ", " + index);
        this.nominator = new Numeric(longs.length % 2 == 1 ? den : nom);
        this.denominator = new Numeric(longs.length % 2 == 1 ? nom : den);
    }

    protected Numeric toValue() {
        return this.nominator.division(this.denominator).toValue();
    }

    public Numeric add(Numeric numeric) {
        if (!(numeric instanceof Fraction)) return new Fraction(numeric.multiply(this.denominator).add(this.nominator), this.denominator);
        Fraction fraction = (Fraction) numeric;
        return (fraction.denominator.equals(this.denominator) ? new Fraction(this.nominator.add(fraction.nominator), this.denominator) : new Fraction(this.nominator.multiply(fraction.denominator).add(this.denominator.multiply(fraction.nominator)), this.denominator.multiply(fraction.denominator)));
    }

    public Numeric sub(Numeric numeric) {
        if (!(numeric instanceof Fraction)) return new Fraction(numeric.multiply(this.denominator).add(this.nominator), this.denominator);
        Fraction fraction = (Fraction) numeric;
        return (fraction.denominator.equals(this.denominator) ? new Fraction(this.nominator.sub(fraction.nominator), this.denominator) : new Fraction(this.nominator.multiply(fraction.denominator).sub(this.denominator.multiply(fraction.nominator)), this.denominator.multiply(fraction.denominator)));
    }

    public Numeric multiply(Numeric numeric) {
        if (!(numeric instanceof Fraction)) return new Fraction(this.nominator.multiply(numeric), this.denominator);
        return new Fraction(this.nominator.multiply(((Fraction) numeric).nominator), this.denominator.multiply(((Fraction) numeric).denominator));
    }

    public Numeric division(Numeric numeric) {
        return this.multiply(numeric instanceof Fraction ? new Fraction(((Fraction) numeric).denominator, ((Fraction) numeric).nominator) : new Fraction(new Numeric(1), numeric));
    }

    @Deprecated
    public Numeric modulo(Numeric numeric) {
        if (!(numeric instanceof Fraction)) return new Fraction(this.nominator.modulo(numeric.multiply(this.denominator)), this.denominator);
        Fraction fraction = (Fraction) numeric;
        return new Fraction(this.nominator.multiply(fraction.denominator).modulo(fraction.nominator.multiply(this.denominator)), this.denominator.multiply(fraction.denominator));
    }

    /**
     * Implemented!
     * @param target
     * @return
     */
    public Numeric takeModulo(Numeric target) {
        if (!(target instanceof Fraction)) return new Fraction(this.nominator.takeModulo(this.denominator.multiply(target)), this.denominator);
        Fraction fraction = (Fraction) target;
        return new Fraction(fraction.nominator.multiply(this.denominator).takeModulo(this.nominator.multiply(fraction.denominator)), this.denominator.multiply(fraction.denominator));
    }

    public Numeric negate() {
        return new Fraction(this.nominator.negate(), this.denominator);
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

    public boolean isZero() {
        return this.nominator.isZero() || this.denominator.isZero();
    }

    public boolean isNaN() {
        return this.nominator.isZero() && this.denominator.isZero();
    }

    public boolean isInfinite() {
        return this.denominator.isZero();
    }

    public String toString() {
        return String.format("%s / %s", this.nominator, this.denominator);
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
}

