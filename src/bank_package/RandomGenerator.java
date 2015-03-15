package bank_package;

import java.util.Random;
import java.util.Vector;

public class RandomGenerator {

    private static int currentIndex;
    private final Random r = new Random();
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    private final int NUM_IN_HEX = HEX_ALPHABET.length();
    private final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int LENGTH_OF_ALPHA_NUMERIC = ALPHA_NUMERIC.length();
    private final int NUM_IN_ALPHABET = ALPHABET.length();
    private final Vector<Integer> acctNumberList = new Vector<Integer>(50);

    public boolean getRandomBoolean() {
        double temp = r.nextDouble();
        return (temp < .50);
    }

    public Integer acctGen() {
        /*Returns random account number.*/
        int temp = 100000000;
        Integer tempAccountNumber = temp += r.nextInt(99999999);

        while (this.acctNumberList.contains(tempAccountNumber))
            tempAccountNumber = temp += r.nextInt(99999999);

        this.acctNumberList.add(tempAccountNumber);
        currentIndex++;
        return tempAccountNumber;
    }

    public String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }

    public double getDubs(double min, double max) {
        return ((r.nextDouble() * max) + min);
    }

    public int getInts(int min, int max) {
        return ((r.nextInt(max)) + min);
    }

    public String passwordGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i < maxLength; i++) {
            temp += ALPHA_NUMERIC.charAt(r.nextInt(LENGTH_OF_ALPHA_NUMERIC));
        }
        return temp;
    }

    public String accountTypeGen() {
        String[] accounts = {"SAVINGS", "CHECKING", "IRA", "CD", "MMA", "RETURN"};
        return accounts[r.nextInt(6)];
    }

    public String informationGen() {
        String[] accounts = {"CHEX" , "CREDIT", "ACCOUNTS", "ALL", "RETURN"};
        return accounts[r.nextInt(5)];
    }

    public String transactionGen() {
        String[] accounts = {"DEPOSIT", "WITHDRAW", "TRANSFER", "ACCOUNTS", "RETURN"};
        return accounts[r.nextInt(5)];
    }

    public String processGen(){
        String[] accounts = {"INFORMATION", "TRANSACTION", "ADDACCOUNT", "EXIT"};
        return accounts[r.nextInt(4)];
    }



}
