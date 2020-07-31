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

        vector2 = new Vector( 3, 7, 13);
        vector3 = new Vector(1, 1, 1);
        for (int __len = 0; __len < 3; ++__len) {
            use = vector2.state[__len];
            numeric2 = ONE;
            for (int len = 0; len < 3; ++len) numeric2 = numeric2.multiply(len == __len ? ONE : vector2.state[len].modulo(use));
            vector3.state[__len] = numeric2.modulo(use);
            System.out.printf("%s (%d): %s (%d), %s (%d), %s\n", use, __len, numeric1, elapsedTime0 - elapsedTiem, numeric2, elapsedTime1 - elapsedTime0, vector3.state[__len]);
        }

        Numeric input = vector2.state[0].multiply(vector2.state[1].multiply(vector2.state[2])).sub(new Numeric(1));
        Vector vector4 = new Vector(input.modulo(vector2.state[0]), input.modulo(vector2.state[1]), input.modulo(vector2.state[2]));
        Vector vector5 = new Vector(0, 0, 0);
        Numeric numeric3;
        Numeric[] numerics = new Numeric[vector2.state.length];
        Vector[] vector6 = new Vector[(int) Math.pow(2, numerics.length)];
        Numeric max = new Numeric(1);
        Numeric f = new Numeric(0);
        for (int len = 0; len < numerics.length; ++len) max = max.multiply(vector4.state[len]);
        for (int _len = 0; _len < vector6.length; ++_len) {
            for (int len = 0; len < numerics.length; ++len)
                numerics[len] = vector3.state[len].sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
            vector6[_len] = new Vector(numerics);

            numeric3 = new Numeric(0);
            for (int __len = 0; __len < 3; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector4.state[__len]);
                f = f.add(vector5.state[__len]);
                for (int len = 0; len < 3; ++len)
                    vector5.state[__len] = vector5.state[__len].multiply(len == __len ? ONE : vector2.state[len]);
                numeric3 = numeric3.add(vector5.state[__len]);
            }
            System.out.println(_len + " x:" + vector2 + " b:" + vector6[_len] + " c:" + vector4 + " abc:" + vector5 + " sum:" + numeric3 + " input:" + input + " max:" + max + " f:" + f);
        }

        vector2 = new Vector( 3, 5, 7);
        vector3 = new Vector(1, 1, 1);
        for (int __len = 0; __len < 3; ++__len) {
            use = vector2.state[__len];
            numeric2 = ONE;
            for (int len = 0; len < 3; ++len) numeric2 = numeric2.multiply(len == __len ? ONE : vector2.state[len].modulo(use));
            vector3.state[__len] = numeric2.modulo(use);
            System.out.printf("%s (%d): %s (%d), %s (%d), %s\n", use, __len, numeric1, elapsedTime0 - elapsedTiem, numeric2, elapsedTime1 - elapsedTime0, vector3.state[__len]);
        }

        input = vector2.state[0].multiply(vector2.state[1].multiply(vector2.state[2])).sub(new Numeric(1));
        vector4 = new Vector(input.modulo(vector2.state[0]), input.modulo(vector2.state[1]), input.modulo(vector2.state[2]));
        vector5 = new Vector(0, 0, 0);
        numerics = new Numeric[vector2.state.length];
        vector6 = new Vector[(int) Math.pow(2, numerics.length)];
        max = new Numeric(1);
        f = new Numeric(0);
        for (int _len = 0; _len < vector6.length; ++_len) {
            for (int len = 0; len < numerics.length; ++len)
                numerics[len] = vector3.state[len].sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
            vector6[_len] = new Vector(numerics);

            numeric3 = new Numeric(0);
            for (int __len = 0; __len < 3; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector4.state[__len]);
                f = f.add(vector5.state[__len]);
                for (int len = 0; len < 3; ++len)
                    vector5.state[__len] = vector5.state[__len].multiply(len == __len ? ONE : vector2.state[len]);
                numeric3 = numeric3.add(vector5.state[__len]);
            }
            System.out.println(_len + " x:" + vector2 + " b:" + vector6[_len] + " c:" + vector4 + " abc:" + vector5 + " sum:" + numeric3 + " input:" + input + " max:" + max + " f:" + f);
        }

        input = vector2.state[0].multiply(vector2.state[1].multiply(vector2.state[2])).sub(new Numeric(1));
        vector4 = new Vector(input.modulo(vector2.state[0]), input.modulo(vector2.state[1]), input.modulo(vector2.state[2]));
        vector5 = new Vector(0, 0, 0);
        numerics = new Numeric[vector2.state.length];
        vector6 = new Vector[(int) Math.pow(2, numerics.length)];
        max = new Numeric(1);
        f = new Numeric(0);
        /*for (int _len = 0; _len < vector6.length; ++_len) {
            for (int len = 0; len < numerics.length; ++len) {
                numerics[len] = vector3.state[len].sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
                System.out.println("reg " + _len + " " + len + " " + (new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)) + " " + vector2.state[len] + " " + vector3.state[len] + " " + numerics[len]);
            }
            vector6[_len] = new Vector(numerics);
            System.out.println(_len + " reg b" + vector6[_len]);
        }*/

        for (int len = 0; len < vector6.length; ++len) System.out.println(len + " b: " + vector6[len]);

        Vector vector7 = new Vector(1, 1, 1);
        for (int __len = 0; __len < vector7.state.length; ++__len) {
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);
            System.out.print((__len == 0 ? "a:" : "") + vector2.state[__len] + (__len + 1 < vector7.state.length ? ", " : "\n"));
        }

        for (int len = 0; len < vector6.length; ++len) System.out.println(len + " b: " + vector6[len]);

        for (int _len = 0; _len < vector6.length; ++_len) {
            for (int len = 0; len < numerics.length; ++len) {
                numerics[len] = vector3.state[len].sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
                System.out.println("reg " + _len + " " + len + " " + (new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)) + " " + vector2.state[len] + " " + vector3.state[len] + " " + numerics[len]);
            }
            vector6[_len] = new Vector(numerics);
            System.out.println(_len + " reg b" + vector6[_len]);


            f = new Numeric(0);
            numeric3 = new Numeric(0);
            for (int __len = 0; __len < 3; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]);
                f = f.add(vector5.state[__len]);
                numeric3 = numeric3.add(vector5.state[__len].multiply(vector4.state[__len]));
                System.out.println("inner the " + __len + " " + _len + " " + f + " " + numeric3 + " " + vector5.state[__len] + ", " + vector6[_len].state[__len] + ", " + vector4.state[__len] + ", " + vector7.state[__len]);
            }
            System.out.println("inner " + _len + " x:" + vector2 + " a:" + vector7 + " b:" + vector6[_len] + " c:" + vector4 + " abc:" + vector5 + " sum:" + numeric3 + " input:" + input + " max:" + max + " f:" + f);
        }

        System.out.println();

        Numeric[] numerics1;

        vector2 = new Vector( 3, 5, 7);
        numerics = new Numeric[vector2.state.length];
        numerics1 = new Numeric[vector2.state.length];
        max = new Numeric(1);
        vector6 = new Vector[(int) Math.pow(2, numerics.length)];;

        input = new Numeric(1);
        for (int len = 0; len < vector2.state.length; ++len) input = input.multiply(vector2.state[len]);
        input = input.sub(new Numeric(1));

        Arrays.fill(numerics1, new Numeric(0));
        vector5 = new Vector(numerics1);
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
//        vector4 = new Vector(input.modulo(vector2.state[0]), input.modulo(vector2.state[1]), input.modulo(vector2.state[2]));
        vector4 = new Vector(numerics1);

        vector7 = new Vector(1, 1, 1);
        for (int __len = 0; __len < vector7.state.length; ++__len) {
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);
//            System.out.print((__len == 0 ? "a:" : "") + vector2.state[__len] + (__len + 1 < vector7.state.length ? ", " : "\n"));
        }

//        for (int len = 0; len < vector6.length; ++len) System.out.println(len + " b: " + vector6[len]);

        for (int _len = 0; _len < vector6.length; ++_len) {
            for (int len = 0; len < numerics.length; ++len) {
                numerics[len] = vector3.state[len].sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
//                System.out.println("reg " + _len + " " + len + " " + (new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)) + " " + vector2.state[len] + " " + vector3.state[len] + " " + numerics[len]);
            }
            vector6[_len] = new Vector(numerics);
//            System.out.println(_len + " reg b" + vector6[_len]);

            f = new Numeric(0);
            numeric3 = new Numeric(0);
            for (int __len = 0; __len < 3; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]);
                f = f.add(vector5.state[__len]);
                numeric3 = numeric3.add(vector5.state[__len].multiply(vector4.state[__len]));
//                System.out.println("inner the " + __len + " " + _len + " " + f + " " + numeric3 + " " + vector5.state[__len] + ", " + vector6[_len].state[__len] + ", " + vector4.state[__len] + ", " + vector7.state[__len]);
            }
            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tabc:%s \tsum:%s \tinput:%s \tmax:%s \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, vector5, numeric3, input, max, f, f.equals(new Numeric(1)));
        }

        System.out.println();

        vector2 = new Vector( 3, 5, 7);
        max = new Numeric(1);

        input = new Numeric(1);
        for (int len = 0; len < vector2.state.length; ++len) input = input.multiply(vector2.state[len]);
        input = input.sub(new Numeric(1));

        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(0));
        vector5 = new Vector(numerics);

        numerics = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
        vector4 = new Vector(numerics);

        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(1));
        vector7 = new Vector(numerics);
        for (int __len = 0; __len < vector7.state.length; ++__len)
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);

        vector6 = new Vector[(int) Math.pow(2, vector2.state.length)];;
        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.state.length];
            vector6[_len] = new Vector(numerics);
            for (int len = 0; len < numerics.length; ++len)
                vector6[_len].state[len] = vector3.state[len].sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
        }

        for (int _len = 0; _len < vector6.length; ++_len) {
            f = new Numeric(0);
            numeric3 = new Numeric(0);
            for (int __len = 0; __len < vector2.state.length; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]);
                f = f.add(vector5.state[__len]);
                numeric3 = numeric3.add(vector5.state[__len].multiply(vector4.state[__len]));
            }
            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tabc:%s \tsum:%s \tinput:%s \tmax:%s \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, vector5, numeric3, input, max, f, f.equals(new Numeric(1)));
        }

        System.out.println();
        clip(new Vector(3, 5, 7, 11));

        System.out.println();
        System.out.println();

        vector2 = new Vector( 3, 5, 7);

        input = new Numeric(1);
        for (int len = 0; len < vector2.state.length; ++len) input = input.multiply(vector2.state[len]);
        input = input.sub(new Numeric(1));

        //c
        numerics = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
        vector4 = new Vector(numerics);

        //abc
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(0));
        vector5 = new Vector(numerics);

        //a
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(1));
        vector7 = new Vector(numerics);
        for (int __len = 0; __len < vector7.state.length; ++__len)
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);

        //b
        /*vector6 = new Vector[(int) Math.pow(2, vector2.state.length)];;
        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.state.length];
            vector6[_len] = new Vector(numerics);
            for (int len = 0; len < numerics.length; ++len) vector6[_len].state[len] = vector7.state[len].modulo(vector2.state[len]).sub(vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0)));
        }*/

        /**
         * d(0) = 0
         * K(i)=Product[a(j), {j, 1, i - 1}]
         * J(i)=b(i)*K(i)+d(i)
         * d(i+1)=J(i) mod (K(i)*a(i))  (J(i) mod a(i) == c(i))
         * i>=2
         */
        numerics = new Numeric[vector2.state.length + 1];
        Arrays.fill(numerics, new Numeric(0));
        Vector vector8 = new Vector(numerics);
        System.out.println("pitch " + vector2.state.length);
        vector8.state[0] = vector4.state[0];
        for (int len = 0; len < vector2.state.length; ++len) {
            Numeric numeric4 = new Numeric(1);
            for (int _len = 0; _len < len; ++_len) numeric4 = numeric4.multiply(vector2.state[_len]);
            for (int _len = 0; _len < vector2.state[len].value; ++_len) {
                Numeric numeric5 = numeric4.multiply(new Numeric(_len)).add(vector8.state[len]);
                System.out.println("pitching " + _len + " " + numeric5 + ", " + numeric5.modulo(vector2.state[len]) + " " + vector4.state[len]);
                if (!numeric5.modulo(vector2.state[len]).equals(vector4.state[len])) continue;
                vector8.state[len + 1] = numeric5.modulo(numeric4.multiply(vector2.state[len])).modulo(vector2.state[len]);
            }
            System.out.println("patch c " + len + " " + numeric4 + " " + vector2.state[len]);
        }

        System.arraycopy(numerics, 1, numerics, 0, numerics.length - 1);
        numerics = Arrays.copyOf(numerics, numerics.length - 1);
        vector8 = new Vector(numerics);
        System.out.println(vector8);

        vector6 = new Vector[(int) Math.pow(2, vector2.state.length)];;
        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.state.length];
            vector6[_len] = new Vector(numerics);
            for (int len = 0; len < numerics.length; ++len) {
                Numeric numeric4 = vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0));
                vector6[_len].state[len] = vector8.state[len].modulo(vector2.state[len]);
                vector6[_len].state[len] = (vector6[_len].state[len].toValue().value > 0 ? vector6[_len].state[len].sub(numeric4) : vector6[_len].state[len].add(numeric4));
            }
        }
        System.out.println("how " + Arrays.toString(vector6));

        for (int len = 0; len < vector2.state.length; ++len) max = max.multiply(vector2.state[len]);

        for (int _len = 0; _len < vector6.length; ++_len) {
            f = new Numeric(0);
            numeric3 = new Numeric(0);
            for (int __len = 0; __len < vector2.state.length; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]).modulo(vector2.state[__len]);
                f = f.add(vector5.state[__len]);
                numeric3 = numeric3.add(vector5.state[__len].multiply(vector4.state[__len]));
            }
            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tab:%s \tsum:%s \tinput:%s(%s) \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, vector5, numeric3, input, input.sub(max), f, f.equals(new Numeric(1)));
        }


        vector2 = new Vector(3, 5, 7);
        Vector[] vectors = clip(vector2);
        Numeric numeric4 = new Numeric(328);
        max = new Numeric(1);
        for (Numeric numeric5 : vector2.state) max = max.multiply(numeric5);
        numeric4 = numeric4.modulo(max);
        Numeric[] numerics2 = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2.state[len]);
        Vector vector9 = new Vector(numerics2);

        Numeric numeric5 = new Numeric(0);
        for (Vector vector10 : vectors) {
            for (int len = 0; len < vector2.state.length; ++len)
                numeric5 = numeric5.add(vector2.state[len].multiply(vector9.state[len]).multiply(vector10.state[len]));
            System.out.println("calculate a:" + vector2 + " b:" + vector10 + " c:" + vector9 + " input:" + numeric4 + " output:" + numeric5 + " max:" + max);
        }
    }

    public static final Numeric ONE = new Numeric(1);
    public static Vector[] clip(Vector vector2) {
//        Vector vector2;
        Vector vector4;
        Vector[] vector6;
        Vector vector5;
        Vector vector7;
        Numeric f;
        Numeric input;
        Numeric numeric3;
        Numeric[] numerics;
        Vector vector8;
        Vector[] export = new Vector[0];
        Numeric max = new Numeric(1);

//        vector2 = new Vector( 3, 5, 7);

        input = new Numeric(1);
        for (int len = 0; len < vector2.state.length; ++len) input = input.multiply(vector2.state[len]);
        input = input.sub(new Numeric(1));

        //c
        numerics = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
        vector4 = new Vector(numerics);

        //ab
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(0));
        vector5 = new Vector(numerics);

        //a
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(1));
        vector7 = new Vector(numerics);
        for (int __len = 0; __len < vector7.state.length; ++__len)
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);
/*
        //b
        numerics = new Numeric[vector2.state.length + 1];
        Arrays.fill(numerics, new Numeric(0));
        vector8 = new Vector(numerics);
        System.out.println("pitch " + vector2.state.length);
        vector8.state[0] = vector4.state[0];
        for (int len = 0; len < vector2.state.length; ++len) {
            Numeric numeric4 = new Numeric(1);
            for (int _len = 0; _len < len; ++_len) numeric4 = numeric4.multiply(vector2.state[_len]);
            for (int _len = 0; _len < vector2.state[len].value; ++_len) {
                Numeric numeric5 = numeric4.multiply(new Numeric(_len)).add(vector8.state[len]);
                System.out.println("pitching " + _len + " " + numeric5 + ", " + numeric5.modulo(vector2.state[len]) + " " + vector4.state[len]);
                if (!numeric5.modulo(vector2.state[len]).equals(vector4.state[len])) continue;
                vector8.state[len + 1] = numeric5.modulo(numeric4.multiply(vector2.state[len])).modulo(vector2.state[len]);
            }
            System.out.println("patch c " + len + " " + numeric4 + " " + vector2.state[len]);
        }

        System.arraycopy(numerics, 1, numerics, 0, numerics.length - 1);
        numerics = Arrays.copyOf(numerics, numerics.length - 1);
        vector8 = new Vector(numerics);
        System.out.println(vector8);
        */
        vector8 = clipAtB(vector2.state, vector4.state);

        vector6 = new Vector[(int) Math.pow(2, vector2.state.length)];;
        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.state.length];
            vector6[_len] = new Vector(numerics);
            for (int len = 0; len < numerics.length; ++len) {
                numerics = vector6[_len].state;
                Numeric numeric4 = vector2.state[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0));
                numerics[len] = vector8.state[len].modulo(vector2.state[len]);
                numerics[len] = (numerics[len].toValue().value > 0 ? numerics[len].sub(numeric4) : numerics[len].add(numeric4));
            }
        }
        System.out.println("how " + Arrays.toString(vector6));

        for (int len = 0; len < vector2.state.length; ++len) max = max.multiply(vector2.state[len]);

        for (int _len = 0; _len < vector6.length; ++_len) {
            f = new Numeric(0);
            numeric3 = new Numeric(0);
            for (int __len = 0; __len < vector2.state.length; ++__len) {
                vector5.state[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]).modulo(vector2.state[__len]).negate();
                f = f.add(vector5.state[__len]);
                numeric3 = numeric3.add(vector5.state[__len].multiply(vector4.state[__len]));
                if (f.equals(new Numeric(1))) {
                    export = Arrays.copyOf(export, export.length + 1);
                    export[export.length - 1] = new Vector(Arrays.copyOf(vector5.state, vector5.state.length));
                }
            }
            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tab:%s \tsum:%s \tinput:%s(%s) \tmax:%s \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, vector5, numeric3, input, input.sub(max), max, f, f.equals(new Numeric(1)));
        }
        return export;
    }

    public static Vector clipAtB(Numeric[] vector2, Numeric[] vector4) {
        Numeric[] numerics = new Numeric[vector2.length + 1];
        numerics[0] = vector4[0];
        for (int len = 0; len < vector2.length; ++len) {
            if (numerics[len] == null) numerics[len] = new Numeric(0);
            Numeric numeric4 = new Numeric(1);
            for (int _len = 0; _len < len; ++_len) numeric4 = numeric4.multiply(vector2[_len]);
            for (int _len = 0; _len < vector2[len].value; ++_len) {
                Numeric numeric5 = numeric4.multiply(new Numeric(_len)).add(numerics[len]);
                if (!numeric5.modulo(vector2[len]).equals(vector4[len])) continue;
                numerics[len + 1] = numeric5.modulo(numeric4.multiply(vector2[len])).modulo(vector2[len]);
            }
        }

        System.arraycopy(numerics, 1, numerics, 0, numerics.length - 1);
        numerics = Arrays.copyOf(numerics, numerics.length - 1);
        return new Vector(numerics);
    }
}
