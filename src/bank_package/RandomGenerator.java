package bank_package;

import java.util.Random;
import java.util.Vector;

public class RandomGenerator {

    private static int currentIndex;
    private final Random r = new Random();
    private final Vector<Integer> acctNumberList = new Vector<Integer>(500);
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    private final int NUM_IN_HEX = HEX_ALPHABET.length();
    private final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int LENGTH_OF_ALPHA_NUMERIC = ALPHA_NUMERIC.length();
    private final int NUM_IN_ALPHABET = ALPHABET.length();

    public boolean getRandomBoolean() {
        float temp = r.nextFloat();
        return (temp < .50);
    }

    public Integer acctGen() {
        /*Returns random account number.*/
        int temp = 100000000;
        Integer tempAccountNumber = temp += r.nextInt(99999999);

        while (this.acctNumberList.contains(tempAccountNumber))
            tempAccountNumber = temp += r.nextInt(99999999);

        this.acctNumberList.add(currentIndex, tempAccountNumber);
        return tempAccountNumber;
    }

    public String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }

    public String passwordGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i < maxLength; i++) {
            temp += ALPHA_NUMERIC.charAt(r.nextInt(LENGTH_OF_ALPHA_NUMERIC));
        }
        return temp;
    }

    public String accountTypeGen() {
        String[] accounts = {"SAVINGS", "CHECKING", "IRA", "CD"};
        return accounts[r.nextInt(3)];
    }


}
