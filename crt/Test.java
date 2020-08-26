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

        System.out.println("3 < 5 max " + CRT.max(3, 5));
        System.out.println("3 < 5 min " + CRT.min(3, 5));
        System.out.println("5 < 3 not_max " + CRT.not_max(5, 3));
        System.out.println("5 < 3 not_min " + CRT.not_min(5, 3));
        System.out.println("3 < 3 max " + CRT.max(3, 3));
        System.out.println("3 < 3 min " + CRT.min(3, 3));
        System.out.println("3 < 3 not_max " + CRT.not_max(3, 3));
        System.out.println("3 < 3 not_min " + CRT.not_min(3, 3));
        System.out.println(CRT.min(3, 5) + CRT.not_min(3, 5));
        System.out.println(CRT.min(5, 3) + CRT.not_min(5, 3));

//        if (true) return;

        System.out.println("boot");

        toNextPath();

        CRT crt = new CRT();
        final Object[][][] matches = {new Object[0][2]};

        Numeric[] complete = new Numeric[] {new Numeric(10), new Numeric(11), new Numeric(12)};

        matches[0] = new Object[0][4];

        Consumer consumer = (v) -> {
            Vector vec = (Vector) v;
            Numeric max = Vector.allProduct(vec);
            int match = 0;
            int length = 0;
            Vector modulo;
            Numeric numeric;
            log("fire " + max + ", " + vec);
            for (Numeric len = max.negate(); len.compareTo(max.add(max)) < 0; len = len.add(ONE)) {
                modulo = crt.setBaseModulo(vec, len);
                numeric = crt.getOutput(modulo);
                if (len.modulo(max).compareTo(numeric) == 0) ++match;
                ++length;
                log("test \t" + len + "(" + len.modulo(max) + ") \tinput:" + modulo + " \toutput:" + numeric + " \tmax:" + max /*+ " --- " + crt*/);
            }
            log("match \tbase:" + vec + " \tmax:" + max + " \tmatch: " + match + " \tlength:" + length + " \tproduct:" + crt.getProduct());
            log();
            Vector vector = crt.getProduct();
            if (vector != null && vector.state != null) vector = new Vector(Arrays.copyOf(vector.state, vector.state.length));
            matches[0] = Arrays.copyOf(matches[0], matches[0].length + 1);
            matches[0][matches[0].length - 1] = new Object[]{vec, max, match, length, vector};
        };

        System.out.println("pre all complete");

        allComplete(complete, consumer);

        Object[][] objs = new Object[0][0];

        System.out.println("show match");

        for (Object[] m : matches[0]) {
            log("match \tbase:" + m[0] + " \tmax:" + m[1] + " \tmatch: " + m[2] + " \tlength:" + m[3] + " \tproduct:" + m[4] + " \tismatch:" + (m[2].equals(m[3])));
            if (m[2].equals(m[3])) {
                objs = Arrays.copyOf(objs, objs.length + 1);
                objs[objs.length - 1] = m;
            }
        }

        Test.log("match base count " + matches[0].length);

        System.out.println("show match on");

        for (Object[] m : objs)
            log("match on \tbase:" + m[0] + " \tmax:" + m[1] + " \tmatch: " + m[2] + " \tlength:" + m[3] + " \tproduct:" + m[4] + " \tismatch:" + (m[2].equals(m[3])));

        Test.log("match on base count " + objs.length);
    }

    public static final long FILE_SIZE = 1024L * 1024L;

    public static String PATH = ".\\all";

    public static String STARTS_WITH_REGEX_FILE_NAME = "all_test";

    public static File CURRENT_FILE;

    public static PrintStream printStream;

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static File CURRENT_PATH;

    public static void toNextPath() {
        File parent = new File(PATH).getAbsoluteFile();
        String PATH0 = simpleDateFormat.format(new Date()) + "-data";
        CURRENT_PATH = new File(PATH + "\\" + PATH0 + (parent.listFiles(n -> n.getName().startsWith(PATH0)).length)).getAbsoluteFile();
    }

    public static File getNextFile() {
        if (!CURRENT_PATH.exists()) CURRENT_PATH.mkdirs();
        int count = CURRENT_PATH.listFiles(f -> f.getName().startsWith(STARTS_WITH_REGEX_FILE_NAME) && f.getName().endsWith(".log")).length;
        return new File(CURRENT_PATH, STARTS_WITH_REGEX_FILE_NAME + count + "-" + simpleDateFormat.format(new Date()) + ".log");
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
        System.out.println(log);
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
