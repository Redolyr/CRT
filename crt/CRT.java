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

        Vector[] vector6 = clipBLine(vector2.state, sieving(clipAtB(vector2.state, max)), max);

        /*
            for (int _len = 0; _len < vec6.length; ++_len) {
                numbers = new double[numbers.length];
                Arrays.fill(numbers, 0);

                double f = 0;
                for (int __len = 0; __len < Math.min(vec2.length, vec6[_len].length); ++__len) {
                    numbers[__len] = (vec6[_len][__len] * vec7[__len] * vec4[__len]) % vec2[__len];
                    f += vec6[_len][__len] * vec7[__len];
                }
                if (f != 1) continue;
                export = vec6[_len];
            }
         */
        for (int _len = 0; _len < vector6.length; ++_len) {
            numerics = new Numeric[vector2.state.length];
            Arrays.fill(numerics, new Numeric(0));

            Numeric f = new Numeric(0);
            for (int __len = 0; __len < min(vector2.state.length, vector6[_len].state.length) + not_min(vector2.state.length, vector6[_len].state.length); ++__len) {
//                System.out.println(vector6[_len].state.length + " " + vector7.state.length + " " + vector4.state.length + " " + vector2.state.length + " " + __len);
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

    /*
    double[][] vec6 clipBLine(double[] vec2, double[][] vec6, double max)
       double[][] vec6 = new double[0][];
       int[] ints = new int[vec8.length];
       long max = 1;

       for (int i = 0; i < vec8.length; ++i) max *= (ints[i] = (vec8[i] == null ? 0 : vec8[i].length);

       for (int len = 0; len < max; ++len) {
           vec6 = Arrays.copyOf(vec6, vec6.length + 1);
           vec6[vec6.length - 1] = new double[vec8.length];

           for (int _len = ; _len < vec8.length; ++_len) {
               numbers[_len] = vec8[_len][len % ints[_len]];
               number += numbers[_len] * MAX/  vec2[_len];
               number = number % MAX;
               if (number != 1) vec6 = Arrays.copyOf(vec6, vec6.length - 1);
           }
       }
       return vec6;
     */
    private static Vector[] clipBLine(Numeric[] vector2, Numeric[][] vector8, Numeric MAX) {
        Vector[] vector6 = new Vector[0];
        Numeric[] numerics;
        int[] ints = new int[vector8.length];
        long max = 1;
        for (int i = 0; i < vector8.length; i++) {
//            System.out.println("let " + vector8.length + ": " + Arrays.toString(vector8));
//            System.out.println(ints.length);
            max = max * (ints[i] = (vector8[i] == null ? 0 : vector8[i].length));
        }

        for (int len = 0; len < max; ++len) {
            numerics = new Numeric[vector8.length];
            vector6 = Arrays.copyOf(vector6, vector6.length + 1);
            vector6[vector6.length - 1] = new Vector(numerics);
            Numeric numeric = new Numeric(0);
            for (int _len = 0; _len < vector8.length; ++_len) {
//                if (vector8[_len].length != vector2.length) continue;
                numerics[_len] = vector8[_len][len % ints[_len]];
                numeric = numeric.add(numerics[_len].multiply(MAX.division(vector2[_len])));
            }
            numeric = numeric.modulo(MAX);
            if (numeric.toValue().value != 1) vector6 = Arrays.copyOf(vector6, vector6.length - 1);
//            Test.log("vec6 " + len + " " + numeric);
        }

        Test.log("observe " + Arrays.toString(vector6));
//        for (Vector vector : vector6) System.out.println("vehicle " + vector);
        for (Numeric[] numerics1 : vector8) Test.log("behavior " + Arrays.toString(numerics1));
        return vector6;
    }

    /*
    double[][] clipAtB(double[] vec2, double max)
        double[][] numbers = new double[vec2.length][];
        for (int _len = 0; _len < numbers.length; ++_len) {
            double number1  = vec2[_len];
            for (int number = 1; number < max / number1; ++number) {
                numbers[_len] = Arrays.copyOf(numbers[_len], numbers[_len].length + 1);
                numbers[_len][numbers[_len].length - 1] = ((max / number1 * number) % number1) == 1 ? number : 0;
            }
        }
     */
    private static Numeric[][] clipAtB(Numeric[] vector2, Numeric max) {
        Numeric[][] numerics = new Numeric[vector2.length][];
        Test.log("clip vector2:" + Arrays.toString(vector2) + " vector2-length:" + vector2.length + " max:" + max);
        for (int _len = 0; _len < numerics.length; ++_len) {
            Numeric numeric1 = vector2[_len];
            for (Numeric numeric = ONE; numeric.compareTo(max.division(numeric1)) < 0; numeric = numeric.add(ONE)) {
                if (numerics[_len] == null) numerics[_len] = new Numeric[0];
                numerics[_len] = Arrays.copyOf(numerics[_len], numerics[_len].length + 1);
                numerics[_len][numerics[_len].length - 1] = max.division(numeric1).multiply(numeric).modulo(numeric1).equals(ONE) ? numeric : new Numeric(0);
            }
//            Test.log("most index:" + _len + " max:" + max + " div:" + numeric1 + " mod:" + numeric1);
            Test.log("post " + _len + " " + Arrays.toString(numerics[_len]));
        }
        return numerics;
    }

    /*
    double clipOutput(double[] vec2, double[] vec9, double[] vec10, double max)
        double number5 = 0;
        for (int len = 0; len < Math.min(vec2.length, vec10.length); ++len) number5 += max / vec2[len] * vec9[len] * vec10[len];
        return number5 % max;
     */
    private static Numeric clipOutput(Numeric[] vector2, Numeric[] vector9, Numeric[] vector10, Numeric max) {
        Numeric numeric5 = new Numeric(0);
        for (int len = 0; len < min(vector2.length, vector10.length) + not_min(vector2.length, vector10.length); ++len)
            numeric5 = numeric5.add(max.division(vector2[len]).multiply(vector9[len]).multiply(vector10[len]));
        return numeric5.modulo(max);
    }

    /*
    double[] clipC(double[] vec2, double number4, double max)
        double[] number2 = new double[vec2.length];
        for (int len = 0; len < number2.length; ++len) number2[len] = number4 % vec2[len];
        return number2;
     */
    private static Vector clipC(Vector vector2, Numeric numeric4, Numeric max) {
        Numeric[] numerics2 = new Numeric[vector2.state.length];
        for (int len = 0; len < numerics2.length; ++len) numerics2[len] = numeric4.modulo(vector2.state[len]);
        return new Vector(numerics2);
    }

    public static Numeric[][] sieving(Numeric[][] numerics) {
        if (numerics.length == 0) return new Numeric[0][0];
        int[] indices = new int[0];
        for (int len = 0; len < numerics.length; ++len) {
            if (numerics[len] == null) continue;
            for (Numeric numeric : numerics[len]) {
                if (numeric == null) continue;
                if (!numeric.isZero()) {
                    indices = Arrays.copyOf(indices, indices.length + 1);
                    indices[indices.length - 1] = len;
                    break;
                }
            }
        }
        if (indices.length == 0) return new Numeric[][] {numerics[0]};
        Numeric[][] numerics1 = new Numeric[indices.length][];
        for (int len = 0; len < indices.length; ++len) numerics1[len] = numerics[indices[len]];
        return numerics1;
    }

    public static long bit(long src0, long src1) {
        long bit = Long.MAX_VALUE - src0 + src1;
        return (long) (bit / Math.sqrt(bit * bit));
    }

    public static int max(int src0, int src1) {
        return (int) ((bit(src0, src1) + 1) / 2 * src1);
    }

    public static int min(int src0, int src1) {
        return (int) ((bit(src0, src1) + 1) / 2 * src0);
    }

    public static int not_max(int src0, int src1) {
        return (int) ((-bit(src0, src1) + 1) / 2 * src1);
    }

    public static int not_min(int src0, int src1) {
        return (int) ((-bit(src0, src1) + 1) / 2 * src0);
    }
}
