package crt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
        Vector clipInput = new Vector(clipInput(vector2.state, numeric4.modulo(max), max));
//        Vector[] vectors = clip(vector2, numeric4, max);
//        System.out.println("return " + Arrays.toString(vectors));
//        for (Vector vector10 : vectors)
//            System.out.println("synchronized a:" + vector2 + " b:" + vector10 + " c:" + vector9 + " input:" + numeric4 + "(" + numeric4.modulo(max) + ")" + " output:" + clipOutput(vector2.state, clipInput.state, vector10.state, max) + " max:" + max);
        Vector vector6 = clip(vector2, numeric4, max);
        System.out.println("synchronized a:" + vector2 + " b:" + vector6 + " c:" + vector9 + " input:" + numeric4 + "(" + numeric4.modulo(max) + ")" + " output:" + clipOutput(vector2.state, clipInput.state, vector6.state, max) + " max:" + max);
    }

    public static final Numeric ONE = new Numeric(1);

    /**
     *
     * @param vector2 x
     * @return bs
     */
    public static Vector clip(Vector vector2, Numeric input, Numeric max) {
        Numeric[] numerics;
//        Vector[] export = new Vector[0];


        Vector export;
        //c
        numerics = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
        Vector vector4 = new Vector(numerics);

        //export
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(0));
        export = new Vector(numerics);

        //a
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(1));
        Vector vector7 = new Vector(numerics);
        for (int __len = 0; __len < vector7.state.length; ++__len)
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);

        Vector[] vector6 = clipBLine(vector2.state, clipAtB(vector2.state, vector4.state, max), max);
//        vector6 = selectVector6(vector2.state, vector6, -1);
//        Vector[] vector6 = new Vector[] {new Vector(clipAtB(vector2.state, vector4.state))};

        for (int _len = 0; _len < vector6.length; ++_len) {

            //ab
            numerics = new Numeric[vector2.state.length];
            Arrays.fill(numerics, new Numeric(0));

            Numeric f = new Numeric(0);
            for (int __len = 0; __len < vector2.state.length; ++__len) {
                numerics[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]).multiply(vector4.state[__len]).modulo(vector2.state[__len]);
                f = f.add(vector6[_len].state[__len].multiply(vector7.state[__len]));
//                System.out.println("consumer " + __len + " " + vector6[_len] + " " + vector7 + " " +  vector6[_len].state[__len].multiply(vector7.state[__len]));
            }
            f = f.modulo(max);

//            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tab:%s \tsum:%s \tinput:%s(%s) \tmax:%s \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, new Vector(numerics), numerics[0], input, input.sub(max), max, f, f.equals(new Numeric(1)));
            if (!f.equals(ONE)) continue;
            export = vector6[_len];

            /*if (export.length == 0) {
                export = Arrays.copyOf(export, export.length + 1);
                export[export.length - 1] = vector6[_len];
            }
            System.out.println("glutch " + Arrays.toString(export));

            for (Vector vector : export) {
                if (Arrays.equals(vector.state, vector6[_len].state)) continue;
                export = Arrays.copyOf(export, export.length + 1);
                export[export.length - 1] = vector6[_len];
//                System.out.println("glitch " + Arrays.toString(export));
            }*/
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
    public static Numeric[][] clipAtB(Numeric[] vector2, Numeric[] vector4, Numeric max) {
        Numeric[][] numerics = new Numeric[vector2.length][];
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
    public static Numeric clipOutput(Numeric[] vector2, Numeric[] vector9, Numeric[] vector10, Numeric max) {
        Numeric numeric5 = new Numeric(0);
        for (int len = 0; len < vector2.length; ++len)
            numeric5 = numeric5.add(max.division(vector2[len]).multiply(vector9[len]).multiply(vector10[len]));
        return numeric5.modulo(max);
    }

    /**
     *
     * @param vector2 x
     * @param numeric4 input
     * @return c
     */
    public static Numeric[] clipInput(Numeric[] vector2, Numeric numeric4, Numeric max) {
        Numeric[] numerics2 = new Numeric[vector2.length];
        for (int len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2[len]);
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
    public static Vector[] clipBLine(Numeric[] vector2, Numeric[][] vector8, Numeric MAX) {
        Vector[] vector6 = new Vector[0];
        Numeric[] numerics;
        int[] ints = new int[vector8.length];
        long max = IntStream.range(0, vector8.length).mapToLong(len -> (ints[len] = vector8[len].length)).reduce(1, (a, b) -> a * b);

        for (Numeric[] numeric : vector8) System.out.println("vec8 in " + Arrays.toString(numeric));

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
//            System.out.println("outer " + len + " " + numeric + " " + Arrays.toString(numerics) + " " + Arrays.toString(amp));
            if (numeric.toValue().value != 1) vector6 = Arrays.copyOf(vector6, vector6.length - 1);
        }
        System.out.println("vector6 " + Arrays.toString(vector6));
        return vector6;
    }

    /**
     *
     * @param vector6
     * @param select if is this -1 result minimum. else out this number element.
     * @return if vec6.length <= select to selectVector6(vector6, -1)
     */
    public static Vector[] selectVector6(Numeric[] vector2, Vector[] vector6, int select) {
        if (vector6 == null) return null;
        else if (select > -1 && vector6.length > select) return new Vector[] {vector6[select]};
        else if (vector6.length <= select) return selectVector6(vector2, vector6, -1);
        else if (vector6.length == 1) return new Vector[] {vector6[0]};
        else if (vector6.length == 0) return new Vector[0];
        Vector[] vectors = new Vector[vector6[0].state.length];
        Numeric[] numerics = new Numeric[vectors.length];
        Numeric max = ONE;
        for (Numeric vector : vector2) max = max.multiply(vector);
        for (int len = 0; len < vectors.length; ++len) {
            Arrays.fill(numerics, ONE);
            for (int _len = 0; _len < vector2.length; ++_len) numerics[len] = max.division(vector2[_len]);
            vectors[len] = new Vector(Arrays.copyOf(numerics, numerics.length));
        }
        Numeric f = new Numeric(0);
        for (Vector vector : vector6) {
            Numeric[] numerics1 = vector.state;
//            System.out.println("forever " + vector);
            for (int len = 0; len < numerics1.length; ++len) {
                for (int __len = 0; __len < vector.state.length; ++__len)
                    f = f.add(vector.state[__len].multiply(max).division(vector2[__len]));
                f = f.modulo(max);
//                System.out.println("break " + vector + " " + vectors[len]);
                if (!f.equals(ONE)) break;
                vectors[len] = numerics1[len].sub(vectors[len].state[len]).toValue().value < 0 ? vector : vectors[len];
//                System.out.println("broken " + vector + " " + vectors[len]);
            }
        }
        System.out.println("cuz " + Arrays.toString(vector2) + " " + Arrays.toString(vectors));
        return vectors;
    }
}
