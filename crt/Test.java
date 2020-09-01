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

        try {
            System.out.println("boot");

            toNextPath();

            CRT crt = new CRT();
            final Object[][][] matches = {new Object[0][2]};

            Numeric[] complete = new Numeric[]{new Numeric(4), new Numeric(5), new Numeric(6)};

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
                    if (len.modulo(new Numeric(100)).equals(new Numeric(0)))
                        log("test \t" + len + "(" + len.modulo(max) + ") \tinput:" + modulo + " \toutput:" + numeric + " \tmax:" + max /*+ " --- " + crt*/);
                }
                log("match \tbase:" + vec + " \tmax:" + max + " \tmatch: " + match + " \tlength:" + length + " \tproduct:" + crt.getProduct());
                log();
                Vector vector = crt.getProduct();
                if (vector != null && vector.state != null)
                    vector = new Vector(Arrays.copyOf(vector.state, vector.state.length));
                matches[0] = Arrays.copyOf(matches[0], matches[0].length + 1);
                matches[0][matches[0].length - 1] = new Object[]{vec, max, match, length, vector};
            };

            System.out.println("pre all complete");

            try {
                allComplete(complete, consumer);
            } catch (OutOfMemoryError error) {

                log("\n\n\nOut of Memory Error");

                if (printStream != null) {
                    printStream.close();
                    printStream = null;
                }
            }

            Object[][] objs = new Object[0][0];

            System.out.println("show match");

            for (Object[] m : matches[0]) {
                log("match \tbase:" + m[0] + " \tmax:" + m[1] + " \tmatch: " + m[2] + " \tlength:" + m[3] + " \tproduct:" + m[4] + " \tismatch:" + (!m[2].equals(new Numeric(0)) && m[2].equals(m[3]) && !((Numeric) m[1]).isZero()));
                if (!m[2].equals(new Numeric(0)) && m[2].equals(m[3]) && !((Numeric) m[1]).isZero()) {
                    objs = Arrays.copyOf(objs, objs.length + 1);
                    objs[objs.length - 1] = m;
                }
            }

            Test.log("match base count " + matches[0].length);

            System.out.println("show match on");

            for (Object[] m : objs)
                log("match on \tbase:" + m[0] + " \tmax:" + m[1] + " \tmatch: " + m[2] + " \tlength:" + m[3] + " \tproduct:" + m[4] + " \tismatch:" + (!m[2].equals(new Numeric(0)) && m[2].equals(m[3]) && !((Numeric) m[1]).isZero()));

            Test.log("match on base count " + objs.length);
        } catch (OutOfMemoryError error) {

            log("\n\n\nOut of Memory Error");
            log("Out of Memory Error");


            if (printStream != null) {
                printStream.close();
                printStream = null;
            }
        }
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

    public static final Numeric ONE = new Numeric(1);
}
