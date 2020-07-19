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

        Fraction fraction2 = new Fraction(1);
        Fraction[] fractions = new Fraction[5];
        for (int len = 0; len < 5; ++len) {
            fraction2 = (Fraction) ((Numeric) fraction2).multiply((Numeric) (fractions[len] = new Fraction(4 * len + 3, 4 * len + 5)));
            System.out.println(fractions[len] + ", " + len + " get");
        }
        System.out.println(Arrays.toString(fractions));
        System.out.println(fraction2);
        System.out.println(new Vector(fractions));
        System.out.println(Vector.allProduct(new Vector(fractions)));

        Numeric ONE = new Numeric(1);
        Numeric numeric1 = new Numeric(1);
        Numeric numeric2 = new Numeric(1);
        Vector vector2 = new Vector( 3, 5, 7);
        Numeric use;
        long elapsedTime0 = 0;
        long elapsedTime1 = 0;
        long elapsedTiem = 0;
        System.out.println();
        for (int __len = 0; __len < 3; ++__len) {
            use = vector2.state[__len];
            numeric1 = ONE;
            numeric2 = ONE;
            elapsedTiem = System.nanoTime();
            numeric1 = Vector.allProduct(vector2).division(use).modulo(use);
            elapsedTime0 = System.nanoTime();
            for (int len = 0; len < 3; ++len) {
//                System.out.println(len + ", " + __len + ", " + vector2.state[len] + ", " + use + ", " + (len == __len ? ONE : vector2.state[len].modulo(use)));
                numeric2 = numeric2.multiply(len == __len ? ONE : vector2.state[len].modulo(use));
            }
//            System.out.println(use + ", " + __len + ", " + numeric2);
            numeric2 = numeric2.modulo(use);
            elapsedTime1 = System.nanoTime();
            System.out.printf("%s (%d): %s (%d), %s (%d)\n", use, __len, numeric1, elapsedTime0 - elapsedTiem, numeric2, elapsedTime1 - elapsedTime0);
        }
        vector2 = new Vector( 3, 7, 19);
        for (int __len = 0; __len < 3; ++__len) {
            use = vector2.state[__len];
            numeric1 = ONE;
            numeric2 = ONE;
            elapsedTiem = System.nanoTime();
            numeric1 = Vector.allProduct(vector2).division(use).modulo(use);
            elapsedTime0 = System.nanoTime();
            for (int len = 0; len < 3; ++len) {
//                System.out.println(len + ", " + __len + ", " + vector2.state[len] + ", " + use + ", " + (len == __len ? ONE : vector2.state[len].modulo(use)));
                numeric2 = numeric2.multiply(len == __len ? ONE : vector2.state[len].modulo(use));
            }
//            System.out.println(use + ", " + __len + ", " + numeric2);
            numeric2 = numeric2.modulo(use);
            elapsedTime1 = System.nanoTime();
            System.out.printf("%s (%d): %s (%d), %s (%d)\n", use, __len, numeric1, elapsedTime0 - elapsedTiem, numeric2, elapsedTime1 - elapsedTime0);
        }

        vector2 = new Vector( 3, 5, 7);
        Vector vector3 = new Vector(1, 1, 1);
        for (int __len = 0; __len < 3; ++__len) {
            use = vector2.state[__len];
            numeric2 = ONE;
            for (int len = 0; len < 3; ++len) numeric2 = numeric2.multiply(len == __len ? ONE : vector2.state[len].modulo(use));
            numeric2 = numeric2.modulo(use);
            if (numeric2.multiply(use.sub(ONE)).modulo(use).equals(ONE)) vector3.state[__len] = new Numeric(-1);
            else vector3.state[__len] = ONE;
            System.out.printf("%s (%d): %s (%d), %s (%d), %s\n", use, __len, numeric1, elapsedTime0 - elapsedTiem, numeric2, elapsedTime1 - elapsedTime0, vector3.state[__len]);
        }

        vector2 = new Vector( 3, 5, 11);
        vector3 = new Vector(1, 1, 1);
        for (int __len = 0; __len < 3; ++__len) {
            use = vector2.state[__len];
            numeric2 = ONE;
            for (int len = 0; len < 3; ++len) numeric2 = numeric2.multiply(len == __len ? ONE : vector2.state[len].modulo(use));
            vector3.state[__len] = numeric2.modulo(use);
            System.out.printf("%s (%d): %s (%d), %s (%d), %s\n", use, __len, numeric1, elapsedTime0 - elapsedTiem, numeric2, elapsedTime1 - elapsedTime0, vector3.state[__len]);
        }
    }
}
