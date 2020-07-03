package crt;

public class Test {

    public static void main(String[] args) {
        Vector vector = new Vector(3, 2, 8);
        Vector vector1 = new Vector(2, 4, 9);
        //3 * 2 + 2 * 4 + 8 * 9 = 6 + 8 + 72 = 14 + 72 = 86
        Numeric numeric = vector.innerProduct(vector1);
        System.out.println(numeric);
    }
}
