package crt;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) {

        Vector vector2;
        Numeric max;

        vector2 = new Vector(3, 5, 7);
        Numeric numeric4 = new Numeric(328);
        max = new Numeric(1);
        for (Numeric numeric5 : vector2.state) max = max.multiply(numeric5);
        numeric4 = numeric4.modulo(max);
        Numeric[] numerics2 = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2.state[len]);
        Vector vector9 = new Vector(numerics2);

        vector2 = new Vector(3, 5, 7);
        numeric4 = new Numeric(104);
        max = new Numeric(1);
        for (Numeric numeric6 : vector2.state) max = max.multiply(numeric6);
        Vector clipInput = new Vector(clipInput(vector2.state, numeric4));
        Vector[] clip = clip(vector2, numeric4);
        for (Vector vector10 : clip)
            System.out.println("0 a:" + vector2 + " b:" + vector10 + " c:" + vector9 + " input:" + numeric4 + " output:" + clipOutput(vector2.state, clipInput.state, vector10.state) + " max:" + max);

        vector2 = new Vector(3, 7, 11);
        numeric4 = new Numeric(104);
        max = new Numeric(1);
        for (Numeric numeric6 : vector2.state) max = max.multiply(numeric6);
        clipInput = new Vector(clipInput(vector2.state, numeric4));
        clip = clip(vector2, numeric4);
        for (Vector vector10 : clip)
            System.out.println("1 a:" + vector2 + " b:" + vector10 + " c:" + vector9 + " input:" + numeric4 + " output:" + clipOutput(vector2.state, clipInput.state, vector10.state) + " max:" + max);

        vector2 = new Vector(3, 5, 11);
        numeric4 = new Numeric(104);
        max = new Numeric(1);
        for (Numeric numeric6 : vector2.state) max = max.multiply(numeric6);
        clipInput = new Vector(clipInput(vector2.state, numeric4));
        clip = clip(vector2, numeric4);
        for (Vector vector10 : clip)
            System.out.println("2 a:" + vector2 + " b:" + vector10 + " c:" + vector9 + " input:" + numeric4 + " output:" + clipOutput(vector2.state, clipInput.state, vector10.state) + " max:" + max);
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

        Numeric max = new Numeric(1);
        for (int len = 0; len < vector2.state.length; ++len) max = max.multiply(vector2.state[len]);

        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = clipCalculate(vector2.state, vector4.state, vector5.state, vector6[_len].state, vector7.state);
            System.out.printf("inner \t%d(%s) \tx:%s \ta:%s \tb:%s \tc:%s \tab:%s \tsum:%s \tinput:%s(%s) \tmax:%s \tf:%s \tf=1:%s\n", _len, Integer.toBinaryString(_len), vector2, vector7, vector6[_len], vector4, vector5, numerics[0], input, input.sub(max), max, numerics[1], numerics[1].equals(new Numeric(1)));
            if (numerics[1].equals(new Numeric(1))) {
                System.arraycopy(numerics, 2, numerics, 0, numerics.length - 2);
                numerics = Arrays.copyOf(numerics, numerics.length - 2);
                export = Arrays.copyOf(export, export.length + 1);
                export[export.length - 1] = new Vector(numerics);
            }
        }
        return export;
    }

    /**
     *
     * @param vector2 x
     * @param vector4 c
     * @return
     */
    public static Numeric[] clipAtB(Numeric[] vector2, Numeric[] vector4) {
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
    }

    public static Numeric[] clipCalculate(Numeric[] vector2, Numeric[] vector4, Numeric[] vector5, Numeric[] vector6, Numeric[] vector7) {
        Numeric[] numerics1 = new Numeric[vector5.length + 2];
        Numeric f = new Numeric(0);
        Numeric numeric3 = new Numeric(0);
        for (int __len = 0; __len < vector2.length; ++__len) {
            vector5[__len] = vector6[__len].multiply(vector7[__len]).modulo(vector2[__len]).negate();
            f = f.add(vector5[__len]);
            numeric3 = numeric3.add(vector5[__len].multiply(vector4[__len]));
        }
        if (f.equals(new Numeric(1))) {
            numerics1 = Arrays.copyOf(vector5, vector5.length + 2);
            System.arraycopy(numerics1, 0, numerics1, 2, numerics1.length - 2);
        }
        if (f.negate().equals(new Numeric(1))) {
            numerics1 = Arrays.copyOf(vector5, vector5.length + 2);
            System.arraycopy(numerics1, 0, numerics1, 2, numerics1.length - 2);
            for (int len = 2; len < numerics1.length; ++len) numerics1[len] = numerics1[len].negate();
        }
        numerics1[0] = numeric3;
        numerics1[1] = f;
        return numerics1;
    }
}
