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
    }
}
