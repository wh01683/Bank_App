package utility;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class uScanner implements Serializable {

    private static final Scanner in = new Scanner(System.in);
    private final String prompt;
    private final int MAX;
    private final int MIN;


    public uScanner(String textPrompt, int min, int max) {
        this.prompt = textPrompt;
        this.MIN = min;
        this.MAX = max;

    }


    private int getInt() throws NoSuchElementException {

        System.out.println(this.prompt);
        while (!in.hasNextInt()) {
            System.out.println("Must enter a numeric value.");
            in.next();
        }
        return in.nextInt();
    }

    public int intGet() throws NoSuchElementException {
        int val = getInt();
        while (val < this.MIN | val > this.MAX) {
            System.out.println("Must be greater than " + this.MIN + " and lesser than " + this.MAX);
            val = getInt();
        }
        return val;
    }

    public double doubleGet() throws NoSuchElementException {
        double val = getDouble();

        double minDouble = this.MIN;
        double maxDouble = this.MAX;

        while (val < minDouble | val > maxDouble) {
            System.out.println("Must be greater than " + minDouble + " and lesser than " + maxDouble);
            val = getDouble();
        }
        return val;
    }

    public String stringGet() throws NoSuchElementException {
        String val = getString();

        while (!val.matches("[a-zA-Z]+")) {

            System.out.println("Must enter letters only.");
            val = getString();

            while (val.length() < this.MIN | val.length() > this.MAX) {
                System.out.println("Must be longer than " + this.MIN + " and shorter than " + this.MAX + " characters long.");
                val = getString();
            }
        }

        return val;
    }

    public String alphaNumericStringGet() throws NoSuchElementException {

        String val = getString();

        while (val.length() < this.MIN | val.length() > this.MAX) {
            System.out.println("Must be longer than " + this.MIN + " and shorter than " + this.MAX + " characters long.");
            val = getString();
        }
        return val;
    }

    private double getDouble() throws NoSuchElementException {
        System.out.println(this.prompt);
        while (!in.hasNextDouble()) {
            System.out.println("Must enter a numeric value.");
            in.next();
        }
        return in.nextDouble();
    }

    private String getString() throws NoSuchElementException {
        System.out.println(this.prompt);

        while (!in.hasNext()) {
            System.out.println(this.prompt);
            in.next();
        }
        return in.next();
    }
}
