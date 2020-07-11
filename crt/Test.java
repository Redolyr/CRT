package crt;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        Vector vector = new Vector(3, 2, 8);
        Vector vector1 = new Vector(2, 4, 9);
        //3 * 2 + 2 * 4 + 8 * 9 = 6 + 8 + 72 = 14 + 72 = 86
        Numeric numeric = vector.innerProduct(vector1);
        System.out.println(numeric);

        Numeric fraction = new Fraction(800, 1000);
        System.out.println(fraction);

        Numeric fraction0 = new Fraction(0.8);
        System.out.println(fraction0);

        /**
         * 'x + 1/0.8 = x * 0.8'<br>
         *     x * (1 - 0.8) = -1/0.8<br>
         *         x = -10/8 * 1/(1-0.8) = -50/8<br>
         *             x = -6.25
         */
        Numeric fraction1 = new Fraction(-6.25 + 1 / 0.8);

        System.out.println(fraction.equals(fraction0) + ", " + fraction.doubleValue() + " ," + fraction0.doubleValue());
        System.out.println(fraction1.division(new Numeric(-6.25)).doubleValue());

        System.out.println(Arrays.toString(Fraction.continuedFraction(6.25)));
        System.out.println(Arrays.toString(Fraction.continuedFraction(3.75)));
        System.out.println("Multiply Division " + fraction.multiply(fraction0) + ", " + fraction.division(fraction0));
        System.out.println("Multiply Division " + fraction.multiply(fraction0).doubleValue() + ", " + fraction.division(fraction0).doubleValue());

        System.out.println(fraction.sub(fraction0).doubleValue());
        System.out.println(fraction.sub(fraction1.division(new Numeric(-6.25))).doubleValue());

        System.out.println(new Numeric(5).modulo(new Numeric(3)));
        System.out.println(new Numeric(3).takeModulo(new Numeric(5)));
        System.out.println(new Fraction(50).modulo(new Fraction(1, 2)));
        System.out.println(new Fraction(1, 2).takeModulo(new Fraction(50)));

        Fraction fraction2 = new Fraction(0);
        Fraction[] fractions = new Fraction[5];
        fractions[0] = new Fraction(1, 1);
        for (int len = 1; len < 5; ++len) {
            fraction2 = (Fraction) fraction2.multiply(fractions[len] = new Fraction(3, 4 * len + 5));
            System.out.println(fractions[len] + ", " + len + " get");
        }
        System.out.println(Arrays.toString(fractions));
        System.out.println(fraction2);
        System.out.println(Vector.allProduct(new Vector(fractions)));


    }
}
