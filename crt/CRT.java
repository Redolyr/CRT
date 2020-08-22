package crt;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CRT {

    public static final Numeric ONE = new Numeric(1);

    private Vector baseNumber;

    private Vector product;

    private Numeric max;

    public Vector getModulo(Numeric input) {
        return clipC(this.baseNumber, input, this.max);
    }

    public Vector setBaseModulo(Vector baseNumber, Numeric input) {
        this.baseNumber = baseNumber;
        this.max = Vector.allProduct(this.baseNumber);
        this.product = clip(baseNumber, input, this.max);
        return this.getModulo(input);
    }

    public Numeric getOutput(Vector baseModulo) {
        return clipOutput(this.baseNumber.state, baseModulo.state, this.product.state, this.max);
    }

    public Vector getBaseNumber() {
        return this.baseNumber;
    }

    public Numeric getMax() {
        return this.max;
    }

    public Vector getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return ("{'CRT':{"
                + "'baseNumber':" + this.baseNumber
                + ", 'product':" + this.product
                + ", 'max':'" + this.max + "'"
                + "}}").replace("'", "\"");
    }

    private static Vector clip(Vector vector2, Numeric input, Numeric max) {
        Numeric[] numerics;

        //c
        numerics = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics.length; ++len) numerics[len] = input.modulo(vector2.state[len]);
        Vector vector4 = new Vector(numerics);

        //export
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(0));
        Vector export = new Vector(numerics);

        //a
        numerics = new Numeric[vector2.state.length];
        Arrays.fill(numerics, new Numeric(1));
        Vector vector7 = new Vector(numerics);
        for (int __len = 0; __len < vector7.state.length; ++__len)
            for (int len = 0; len < vector7.state.length; ++len)
                vector7.state[__len] = vector7.state[__len].multiply(len == __len ? ONE : vector2.state[len]);

        Vector[] vector6 = clipBLine(vector2.state, clipAtB(vector2.state, max), max);

        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.state.length];
            Arrays.fill(numerics, new Numeric(0));

            Numeric f = new Numeric(0);
            for (int __len = 0; __len < vector2.state.length; ++__len) {
                numerics[__len] = vector6[_len].state[__len].multiply(vector7.state[__len]).multiply(vector4.state[__len]).modulo(vector2.state[__len]);
                f = f.add(vector6[_len].state[__len].multiply(vector7.state[__len]));
            }
            if (!f.modulo(max).equals(ONE)) continue;
            export = vector6[_len];
        }
//        for (Vector vector : vector6) Test.log("export vec6 " + vector);
//        Test.log("export " + export);
        return export;
    }

    private static Vector[] clipBLine(Numeric[] vector2, Numeric[][] vector8, Numeric MAX) {
        Vector[] vector6 = new Vector[0];
        Numeric[] numerics;
        int[] ints = new int[vector8.length];
        long max = 1;
        for (int i = 0; i < vector8.length; i++) {
//            System.out.println("let " + Arrays.toString(vector8));
            max = max * (ints[i] = vector8[i].length);
        }

        for (int len = 0; len < max; ++len) {
            numerics = new Numeric[vector8.length];
            vector6 = Arrays.copyOf(vector6, vector6.length + 1);
            vector6[vector6.length - 1] = new Vector(numerics);
            Numeric numeric = new Numeric(0);
            for (int _len = 0; _len < vector8.length; ++_len) {
                numerics[_len] = vector8[_len][len % ints[_len]];
                numeric = numeric.add(numerics[_len].multiply(MAX.division(vector2[_len])));
            }
            numeric = numeric.modulo(MAX);
            if (numeric.toValue().value != 1) vector6 = Arrays.copyOf(vector6, vector6.length - 1);
//            Test.log("vec6 " + len + " " + numeric);
        }
        return vector6;
    }

    private static Numeric[][] clipAtB(Numeric[] vector2, Numeric max) {
        Numeric[][] numerics = new Numeric[vector2.length][];
        for (int _len = 0; _len < numerics.length; ++_len) {
            Numeric numeric1 = vector2[_len];
            for (Numeric numeric = ONE; numeric.compareTo(max.division(numeric1)) < 0; numeric = numeric.add(ONE)) {
                if (max.division(numeric1).multiply(numeric).modulo(numeric1).equals(ONE)) {
                    if (numerics[_len] == null) numerics[_len] = new Numeric[0];
                    numerics[_len] = Arrays.copyOf(numerics[_len], numerics[_len].length + 1);
                    numerics[_len][numerics[_len].length - 1] = numeric;
                    continue;
                }
            }
        }
        Numeric[] numerics1;
        for (int len = 0; len < numerics.length; ++len) {
            if (numerics[len] == null) {
                numerics1 = new Numeric[vector2.length];
                Arrays.fill(numerics1, new Numeric(0));
                numerics[len] = numerics1;
            }
        }
//        System.out.println("class " + Arrays.toString(vector2) + ", " + max);
        return numerics;
    }

    private static Numeric clipOutput(Numeric[] vector2, Numeric[] vector9, Numeric[] vector10, Numeric max) {
        Numeric numeric5 = new Numeric(0);
        for (int len = 0; len < vector2.length; ++len)
            numeric5 = numeric5.add(max.division(vector2[len]).multiply(vector9[len]).multiply(vector10[len]));
        return numeric5.modulo(max);
    }

    private static Vector clipC(Vector vector2, Numeric numeric4, Numeric max) {
        Numeric[] numerics2 = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2.state[len]);
        return new Vector(numerics2);
    }
}
