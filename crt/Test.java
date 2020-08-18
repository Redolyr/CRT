package crt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {

        /*
        test(new Vector(3, 5, 7), new Numeric(328));
        test(new Vector(3, 5, 7), new Numeric(104));
        test(new Vector(3, 7, 11), new Numeric(104));
        test(new Vector(3, 5, 11), new Numeric(104));

        CRT crt = new CRT();
        final Vector[] modulo = new Vector[1];
        final Numeric[] numeric = new Numeric[1];

        modulo[0] = crt.setBaseModulo(new Vector(3, 5, 7), new Numeric(328));
        numeric[0] = crt.getOutput(modulo[0]);
        System.out.println("test input:" + modulo[0] + " output:" + numeric[0]);

        modulo[0] = crt.setBaseModulo(new Vector(3, 5, 7), new Numeric(104));
        numeric[0] = crt.getOutput(modulo[0]);
        System.out.println("test input:" + modulo[0] + " output:" + numeric[0]);

        modulo[0] = crt.setBaseModulo(new Vector(3, 7, 11), new Numeric(104));
        numeric[0] = crt.getOutput(modulo[0]);
        System.out.println("test input:" + modulo[0] + " output:" + numeric[0]);

        modulo[0] = crt.setBaseModulo(new Vector(3, 5, 11), new Numeric(104));
        numeric[0] = crt.getOutput(modulo[0]);
        System.out.println("test input:" + modulo[0] + " output:" + numeric[0]);

        System.out.println();

        Vector[] vectors = new Vector[] {
                new Vector(3, 5, 7),
                new Vector(3, 7, 11),
                new Vector(3, 5, 11),
                new Vector(-3, -5, -7),
                new Vector(-3, -7, -11),
                new Vector(-3, -5, -11),
        };
        final Object[][][] matches = {new Object[vectors.length][2]};
        Vector vector;
        final Numeric[] max = new Numeric[1];
        final int[] match = new int[1];
        final int[] length = new int[1];

        for (int _len = 0; _len < vectors.length; ++_len) {
            vector = vectors[_len];
            max[0] = Vector.allProduct(vector);
            match[0] = 0;
            length[0] = 0;
            for (Numeric len = max[0].negate(); len.compareTo(max[0].add(max[0])) < 0; len = len.add(ONE)) {
                modulo[0] = crt.setBaseModulo(vector, len);
                numeric[0] = crt.getOutput(modulo[0]);
                if (len.modulo(max[0]).compareTo(numeric[0]) == 0) ++match[0];
                ++length[0];
                System.out.println("test " + len + "(" + len.modulo(max[0]) + ") input:" + modulo[0] + " output:" + numeric[0] + " max:" + max[0]);
            }
            System.out.println("match base:" + vector + " max:" + max[0] + " match: " + match[0] + " length:" + length[0]);
            System.out.println();
            matches[0][_len] = new Object[] {vector, max[0], match[0], length[0]};
        }

        for (Object[] m : matches[0])
            System.out.println("match base:" + m[0] + " max:" + m[1] + " match: " + m[2] + " length:" + m[3]);

        System.out.println();
        */

        System.out.println("boot");

        CRT crt = new CRT();
        final Object[][][] matches = {new Object[0][2]};
        Vector vector;
        final Numeric[] max = new Numeric[1];
        final int[] match = new int[1];
        final int[] length = new int[1];
        final Vector[] modulo = new Vector[1];
        final Numeric[] numeric = new Numeric[1];

        Numeric[] complete = new Numeric[] {new Numeric(10), new Numeric(11), new Numeric(12)};

        matches[0] = new Object[0][4];

        Consumer consumer = (v) -> {
            Vector vec = (Vector) v;
            max[0] = Vector.allProduct(vec);
            match[0] = 0;
            length[0] = 0;
            log("fire " + max[0] + ", " + vec);
            for (Numeric len = max[0].negate(); len.compareTo(max[0].add(max[0])) < 0; len = len.add(ONE)) {
                modulo[0] = crt.setBaseModulo(vec, len);
                numeric[0] = crt.getOutput(modulo[0]);
                if (len.modulo(max[0]).compareTo(numeric[0]) == 0) ++match[0];
                ++length[0];
                log("test " + len + "(" + len.modulo(max[0]) + ") input:" + modulo[0] + " output:" + numeric[0] + " max:" + max[0]);
            }
            log("match base:" + vec + " max:" + max[0] + " match: " + match[0] + " length:" + length[0]);
            log();
            matches[0] = Arrays.copyOf(matches[0], matches[0].length + 1);
            matches[0][matches[0].length - 1] = new Object[]{vec, max[0], match[0], length[0]};
        };

        System.out.println("pre all complete");

        allComplete(complete, consumer);

        Object[][] objs = new Object[0][0];

        System.out.println("show match");

        for (Object[] m : matches[0]) {
            log("match base:" + m[0] + " max:" + m[1] + " match: " + m[2] + " length:" + m[3]);
            if (m[2].equals(m[3])) {
                objs = Arrays.copyOf(objs, objs.length + 1);
                objs[objs.length - 1] = m;
            }
        }

        System.out.println("show match on");

        for (Object[] m : objs)
            log("match on base:" + m[0] + " max:" + m[1] + " match: " + m[2] + " length:" + m[3]);
    }

    public static final long FILE_SIZE = 1024L * 1024L;

    public static String PATH = ".\\all";

    public static String STARTS_WITH_REGEX_FILE_NAME = "all_test";

    public static File CURRENT_FILE;

    public static PrintStream printStream;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD");

    public static File getNextFile() {
        File path = new File(PATH);
        if (!path.exists()) path.mkdirs();
        int count = path.listFiles(f -> f.getName().startsWith(STARTS_WITH_REGEX_FILE_NAME) && f.getName().endsWith(".log")).length;
        return new File(PATH, STARTS_WITH_REGEX_FILE_NAME + count + "-" + simpleDateFormat.format(new Date()) + ".log");
    }

    public static void log(String log) {
        try {
            log0(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log() {
        try {
            log0("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log0(String log) throws IOException {
        if (printStream == null) {
            CURRENT_FILE = getNextFile();
            printStream = new PrintStream(CURRENT_FILE);
        } else if (FILE_SIZE < CURRENT_FILE.length()) {
            System.out.println((CURRENT_FILE != null ? CURRENT_FILE.getName() : "unnamed") + " " + (CURRENT_FILE != null ? CURRENT_FILE.length() : -1) + " " + (((CURRENT_FILE != null ? CURRENT_FILE.length() : -1)) > FILE_SIZE));
            printStream.close();
            CURRENT_FILE = getNextFile();
            printStream = new PrintStream(CURRENT_FILE);
        }
        printStream.println(log);
    }

    public static void allComplete(Numeric[] maximumNumbers, Consumer consumer) {
        Numeric max = Vector.allProduct(new Vector(maximumNumbers));
        Numeric[] numerics;
        System.out.println("all " + Arrays.toString(maximumNumbers));
        for (Numeric numeric = new Numeric(0); numeric.compareTo(max) < 0; numeric = numeric.add(ONE)) {
            numerics = new Numeric[maximumNumbers.length];
            for (int len = 0; len < numerics.length; ++len) numerics[len] = numeric.modulo(maximumNumbers[len]);
            log("complete " + new Vector(numerics));
            consumer.accept(new Vector(numerics));
        }
    }

    public static void test(Vector vector2, Numeric numeric4) {
        Numeric max = Vector.allProduct(vector2);
        Vector clipInput = new Vector(clipInput(vector2.state, numeric4.modulo(max), max));
        Vector vector6 = clip(vector2, numeric4, max);
        Numeric output = clipOutput(vector2.state, clipInput.state, vector6.state, max);
        log("synchronized a:" + vector2 + " b:" + vector6 + " c:" + clipInput + " input:" + numeric4 + "(" + numeric4.modulo(max) + ")" + " output:" + output + " max:" + max);
    }

    public static final Numeric ONE = new Numeric(1);

    /**
     *
     * @param vector2 x
     * @return bs
     */
    public static Vector clip(Vector vector2, Numeric input, Numeric max) {
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
        System.out.println();
        return export;
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

    /**
     *
     * @param vector2 x
     * @return
     */
    public static Numeric[][] clipAtB(Numeric[] vector2, Numeric max) {
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
        for (Numeric[] numerics1 : numerics) log("output " + Arrays.toString(numerics1));
        return numerics;
    }

    public static Vector[] clipBLine(Numeric[] vector2, Numeric[][] vector8, Numeric MAX) {
        Vector[] vector6 = new Vector[0];
        Numeric[] numerics;
        int[] ints = new int[vector8.length];
        long max = IntStream.range(0, vector8.length).mapToLong(len -> (ints[len] = vector8[len].length)).reduce(1, (a, b) -> a * b);

        for (Numeric[] numeric : vector8) log("vec8 in " + Arrays.toString(numeric));

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
        }
        log("vector6 " + Arrays.toString(vector6));
        return vector6;
    }
}
