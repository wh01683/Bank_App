package bank_package;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class uScanner {

    private static final Scanner in = new Scanner(System.in);
    private final String prompt;
    private int maxInt;
    private int minInt;
    private double maxDouble;
    private double minDouble;
    private int minLength;
    private int maxLength;

    public uScanner(String textPrompt, int min, int max) {
        this.prompt = textPrompt;
        this.minInt = min;
        this.maxInt = max;
        this.minLength = min;
        this.maxLength = max;
    }

    public uScanner(String textPrompt, double min, double max) {
        this.prompt = textPrompt;
        this.minDouble = min;
        this.maxDouble = max;
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
        while (val < this.minInt | val > this.maxInt) {
            System.out.println("Must be greater than " + this.minInt + " and lesser than " + this.maxInt);
            val = getInt();
        }
        return val;
    }

    public double doubleGet() throws NoSuchElementException {
        double val = getDouble();

        while (val < this.minDouble | val > this.maxDouble) {
            System.out.println("Must be greater than " + this.minDouble + " and lesser than " + this.maxDouble);
            val = getDouble();
        }
        return val;
    }

    public String stringGet() throws NoSuchElementException {
        String val = getString();

        while (!val.matches("[a-zA-Z]+")) {

            System.out.println("Must enter letters only.");
            val = getString();

            while (val.length() < this.minLength | val.length() > this.maxLength) {
                System.out.println("Must be longer than " + this.minLength + " and shorter than " + this.maxLength + " characters long.");
                val = getString();
            }
        }

        return val;
    }

    public String alphaNumericStringGet() throws NoSuchElementException {

        String val = getString();

        while (val.length() < this.minLength | val.length() > this.maxLength) {
            System.out.println("Must be longer than " + this.minLength + " and shorter than " + this.maxLength + " characters long.");
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
