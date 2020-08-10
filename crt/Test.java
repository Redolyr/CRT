package crt;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {

        test(new Vector(3, 5, 7), new Numeric(328), clipC(new Vector(3, 5, 7), new Numeric(328)));
        test(new Vector(3, 5, 7), new Numeric(104), clipC(new Vector(3, 5, 7), new Numeric(104)));
        test(new Vector(3, 7, 11), new Numeric(104), clipC(new Vector(3, 7, 11), new Numeric(104)));
        test(new Vector(3, 5, 11), new Numeric(104), clipC(new Vector(3, 5, 11), new Numeric(104)));
    }

    public static Vector clipC(Vector vector2, Numeric numeric4) {
        Numeric max = new Numeric(1);
        for (Numeric numeric5 : vector2.state) max = max.multiply(numeric5);
        numeric4 = numeric4.modulo(max);
        Numeric[] numerics2 = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2.state[len]);
        return new Vector(numerics2);
    }

    public static void test(Vector vector2, Numeric numeric4, Vector vector9) {
        Numeric max = new Numeric(1);
        for (Numeric numeric6 : vector2.state) max = max.multiply(numeric6);
        Vector clipInput = new Vector(clipInput(vector2.state, numeric4));
        for (Vector vector10 : clip(vector2, numeric4))
            System.out.println("a:" + vector2 + " b:" + vector10 + " c:" + vector9 + " input:" + numeric4 + "(" + numeric4.modulo(max) + ")" + " output:" + clipOutput(vector2.state, clipInput.state, vector10.state) + " max:" + max);
    }

    public static final Numeric ONE = new Numeric(1);

    /**
     *
     * @param vector2 x
     * @return bs
     */
    public static Vector[] clip(Vector vector2, Numeric input) {
        Numeric[] numerics;
        Vector[] export = new Vector[0];

        //c
        numerics = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
        Vector vector4 = new Vector(numerics);

        //ab
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(0));
        Vector vector5 = new Vector(numerics);

        //a
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(1));
        Vector vector7 = new Vector(numerics);
        for (int __len = 0; __len < vector7.state.length; ++__len)
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);

        Vector[] vector6 = clipBLine(vector2.state, clipAtB(vector2.state, vector4.state));
//        Vector[] vector6 = new Vector[] {new Vector(clipAtB(vector2.state, vector4.state))};

        Numeric max = new Numeric(1);
        for (int len = 0; len < vector2.state.length; ++len) max = max.multiply(vector2.state[len]);

        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = clipCalculate(vector2.state, vector4.state, vector5.state, vector6[_len].state, vector7.state);
            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tab:%s \tsum:%s \tinput:%s(%s) \tmax:%s \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, vector5, numerics[0], input, input.sub(max), max, numerics[1], numerics[1].equals(new Numeric(1)));
            if (!numerics[1].sub(ONE).isZero()) continue;
            export = Arrays.copyOf(export, export.length + 1);
            export[export.length - 1] = new Vector(Arrays.copyOf(vector5.state, vector5.state.length));
        }
        System.out.println();
        return export;
    }

    /**
     *
     * @param vector2 x
     * @param vector4 c
     * @return
     */
    public static Numeric[][] clipAtB(Numeric[] vector2, Numeric[] vector4) {
        Numeric[][] numerics = new Numeric[vector2.length][];
        Numeric max = ONE;
        for (int len = 0; len < numerics.length; ++len) max = max.multiply(vector2[len]);
        for (int _len = 0; _len < numerics.length; ++_len) {
            Numeric numeric1 = vector2[_len];
            for (Numeric numeric = ONE.toValue(); numeric.toValue().value < max.division(numeric1).toValue().value; numeric = numeric.add(ONE)) {
//                System.out.println("loop " + _len + " " + numeric + " " + numeric1 + " " + max.division(numeric1).multiply(numeric) + " " + max);
                if (max.division(numeric1).multiply(numeric).modulo(numeric1).equals(ONE)) {
                    if (numerics[_len] == null) numerics[_len] = new Numeric[0];
                    numerics[_len] = Arrays.copyOf(numerics[_len], numerics[_len].length + 1);
                    numerics[_len][numerics[_len].length - 1] = numeric;
                    continue;
                }
            }
        }
        for (Numeric[] numerics1 : numerics) System.out.println("output " + Arrays.toString(numerics1));
        return numerics;
    }

    /**
     *
     * @param vector2 x
     * @param vector9 c
     * @param vector10 b
     * @return input % max
     */
    public static Numeric clipOutput(Numeric[] vector2, Numeric[] vector9, Numeric[] vector10) {
        Numeric numeric5 = new Numeric(0);
        Numeric max = new Numeric(1);
        int len;
        for (len = 0; len < vector2.length; ++len) max = max.multiply(vector2[len]);
        for (len = 0; len < vector2.length; ++len)
            numeric5 = numeric5.add(max.division(vector2[len]).multiply(vector9[len]).multiply(vector10[len]));
        return numeric5.modulo(max);
    }

    /**
     *
     * @param vector2 x
     * @param numeric4 input
     * @return c
     */
    public static Numeric[] clipInput(Numeric[] vector2, Numeric numeric4) {
        Numeric[] numerics2 = new Numeric[vector2.length];
        Numeric max = new Numeric(1);
        int len;
        for (len = 0; len < vector2.length; ++len) max = max.multiply(vector2[len]);
        numeric4 = numeric4.modulo(max);
        for (len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2[len]);
        return numerics2;
    }

    /*
    public static Vector[] clipBLine(Numeric[] vector2, Numeric[] vector8) {
        Numeric[] numerics;
        Vector[] vector6 = new Vector[(int) Math.pow(2, vector2.length)];;
        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.length];
            vector6[_len] = new Vector(numerics);
            for (int len = 0; len < numerics.length; ++len) {
                numerics = vector6[_len].state;
                Numeric numeric4 = vector2[len].multiply(new Numeric(((_len >> len) & 1) != 0 ? 1 : 0));
                numerics[len] = vector8[len].modulo(vector2[len]);
                numerics[len] = (numerics[len].toValue().value > 0 ? numerics[len].sub(numeric4) : numerics[len].add(numeric4));
            }
        }
        return vector6;
    }*/
    public static Vector[] clipBLine(Numeric[] vector2, Numeric[][] vector8) {
        Vector[] vector6 = new Vector[0];
        Numeric[] numerics;
        int[] ints = new int[vector8.length];
        long max = IntStream.range(0, vector8.length).mapToLong(len -> (ints[len] = vector8[len].length)).reduce(1, (a, b) -> a * b);

        for (Numeric[] numeric : vector8) System.out.println("vec8 in " + Arrays.toString(numeric));

        Numeric MAX = ONE;
        for (int len = 0; len < vector2.length; ++len) MAX = MAX.multiply(vector2[len]);

        for (int len = 0; len < max; ++len) {
            numerics = new Numeric[vector8.length];
            vector6 = Arrays.copyOf(vector6, vector6.length + 1);
            vector6[vector6.length - 1] = new Vector(numerics);
            int[] amp = new int[vector8.length];
            for (int _len = 0; _len < vector8.length; ++_len) {
//                System.out.println("filler " + len + " " + _len + " " + ints[_len]);
                numerics[_len] = vector8[_len][len % ints[_len]];
                amp[_len] = len % ints[_len];
            }
            Numeric numeric = new Numeric(0);
            for (int i = 0; i < numerics.length; i++)
                numeric = numeric.add(numerics[i].multiply(MAX.division(vector2[i])));
            numeric = numeric.modulo(MAX);
            System.out.println("outer " + len + " " + numeric + " " + Arrays.toString(numerics) + " " + Arrays.toString(amp));
            if (numeric.toValue().value != 1) vector6 = Arrays.copyOf(vector6, vector6.length - 1);
        }
        System.out.println("vector6 " + Arrays.toString(vector6));
        return vector6;
    }

    public static Numeric[] clipCalculate(Numeric[] vector2, Numeric[] vector4, Numeric[] vector5, Numeric[] vector6, Numeric[] vector7) {
        Numeric f = new Numeric(0);
        Numeric numeric3 = new Numeric(0);
        for (int __len = 0; __len < vector2.length; ++__len) {
            vector5[__len] = vector6[__len].multiply(vector7[__len]).modulo(vector2[__len]).negate();
            f = f.add(vector5[__len]);
            numeric3 = numeric3.add(vector5[__len].multiply(vector4[__len]));
        }
        return new Numeric[] {numeric3, f};
    }
}
