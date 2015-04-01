package utility;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class uScanner implements Serializable {

    private static final Scanner in = new Scanner(System.in);
    private final String prompt;
    private final int MAX;
    private final int MIN;


    /**
     * uScanner constructs a new uScanner with a given text prompt to display to the user, a specified min, and a
     * specified max
     *
     * @param textPrompt message to be displayed whenever input is requested
     * @param min        min length/value of desired input
     * @param max        max length/value of the desired input
     */
    public uScanner(String textPrompt, int min, int max) {
        this.prompt = textPrompt;
        this.MIN = min;
        this.MAX = max;

    }

    /**
     * getInt prompts user for input until it matches an integer
     *
     * @return returns an integer on scan
     * */
    private int getInt() throws NoSuchElementException {

        System.out.println(this.prompt);
        while (!in.hasNextInt()) {
            System.out.println("Must enter a numeric value.");
            in.next();
        }
        return in.nextInt();
    }

    /**
     * intGet method called by the user. this method first calls getInt which makes sure the input is an integer, then
     *        the input value is processed to make sure it follows the min and max parameters specified by the constructor
     *
     * @return returns an integer within the specified min and max value
     * */
    public int intGet() throws NoSuchElementException {
        int val = getInt();
        while (val < this.MIN | val > this.MAX) {
            System.out.println("Must be greater than " + this.MIN + " and lesser than " + this.MAX);
            val = getInt();
        }
        return val;
    }

    /**
     * doubleGet method called to retrieve a double value from the user within a certain value boundary, a boundary
     *           specified in the constructor. this method first calls getDouble to verify that the input is indeed a
     *           double, then the value is processed further to make sure the value falls within the boundaries specified
     *           by the constructor
     *
     * @return returns a double within the boundaries set in the constructor
     * */
    public double doubleGet() throws NoSuchElementException {
        double val = getDouble();

        double minDouble = this.MIN;
        double maxDouble = this.MAX;

        while (Math.abs(val) < Math.abs(minDouble) | val > maxDouble) {
            System.out.println("Must be greater than " + minDouble + " and lesser than " + maxDouble);
            val = getDouble();
        }
        return val;
    }

    /**
     * stringGet method called to retrieve a String from the user within a length specified by the constructor. The method
     *           first calls getString to verify that the input is a string before passing the value on for further analysis.
     *           if the string contains any values other than lower case a-z or uppercase A-Z, the string is rejected.
     *
     * @return returns a String of letters with a length within boundaries specified by the constructor
     * */
    public String stringGet() throws NoSuchElementException {

        try {
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

        } catch (NullPointerException p) {
            System.out.println("No string entered.");
            return stringGet();
        }
    }

    /**
     * alphaNumericStringGet is obtains a String from the user between a certain minimum and maximum length as defined
     *                       by the constructor. This method will return any Strings with the specified length, there
     *                       are no character specifications
     *
     * @return returns a string of any character within a specified min and max length defined by the constructor
     * */
    public String alphaNumericStringGet() throws NoSuchElementException {


        String val = getString();

        while (val.length() < this.MIN | val.length() > this.MAX) {
            System.out.println("Must be longer than " + this.MIN + " and shorter than " + this.MAX + " characters long.");
            val = getString();
        }
        return val;
    }

    /**
     * method used to retrieve ONLY doubles from the user. This method will loop until a double is scanned
     *
     * @return a double value
     * */
    private double getDouble() throws NoSuchElementException {
        System.out.println(this.prompt);
        while (!in.hasNextDouble()) {
            System.out.println("Must enter a numeric value.");
            in.next();
        }
        return in.nextDouble();
    }

    /**
     * getString used to retrieve ONLY strings from the user. This method will loop as long as the input is null.
     *
     * @return returns a String
     *
     * */
    private String getString() {
        System.out.println(this.prompt);
        try {
            while (!in.hasNext()) {
                System.out.println(this.prompt);
                in.next();
            }
            return in.next();
        } catch (NoSuchElementException l) {
            System.exit(0);
        }
        return null;
    }
}
