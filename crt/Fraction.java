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
        while (index > -1) {
            nom += den * longs[--index];
            temp = nom;
            nom = den;
            den = temp;
        }
        this.nominator = new Numeric(nom);
        this.denominator = new Numeric(den);
    }

    public Numeric add(Numeric numeric) {
        if (!(numeric instanceof Fraction))
            return new Fraction(numeric.multiply(this.denominator).add(this.nominator), this.denominator);
        return ((Fraction) numeric).denominator.equals(this.denominator) ? new Fraction(this.nominator.add(((Fraction) numeric).nominator), this.denominator) : new Fraction(this.nominator.multiply(((Fraction) numeric).denominator).add(this.denominator.multiply(((Fraction) numeric).nominator)), this.denominator.multiply(((Fraction) numeric).denominator));
    }

    public Numeric sub(Numeric numeric) {
        if (!(numeric instanceof Fraction))
            return new Fraction(numeric.multiply(this.denominator).add(this.nominator), this.denominator);
        return ((Fraction) numeric).denominator.equals(this.denominator) ? new Fraction(this.nominator.sub(((Fraction) numeric).nominator), this.denominator) : new Fraction(this.nominator.multiply(((Fraction) numeric).denominator).add(this.denominator.multiply(((Fraction) numeric).nominator)), this.denominator.multiply(((Fraction) numeric).denominator));
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
        return new Fraction(numeric instanceof Fraction ? this.nominator.multiply(((Fraction) numeric).denominator).modulo(((Fraction) numeric).nominator.multiply(this.denominator)) : this.nominator.modulo(numeric.multiply(this.denominator)), numeric instanceof Fraction ? this.denominator.multiply(((Fraction) numeric).denominator) : this.denominator);
    }

    /**
     * Implemented!
     * @param target
     * @return
     */
    public Numeric takeModulo(Numeric target) {
        return new Fraction(target instanceof Fraction ? this.nominator.multiply(((Fraction) target).denominator).takeModulo(((Fraction) target).nominator.multiply(this.denominator)) : this.nominator.takeModulo(this.denominator.multiply(target)), target instanceof Fraction ? this.denominator.multiply(((Fraction) target).denominator) : this.denominator);
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

