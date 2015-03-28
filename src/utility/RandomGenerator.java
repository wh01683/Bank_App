package utility;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

public class RandomGenerator implements Serializable {

    private final Random r = new Random();
    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int LENGTH_OF_ALPHA_NUMERIC = ALPHA_NUMERIC.length();
    private final int NUM_IN_ALPHABET = ALPHABET.length();
    private final Vector<Integer> acctNumberList = new Vector<Integer>(50);

// --Commented out by Inspection START (3/27/15 8:02 PM):
//    /**
//     * getRandomBoolean returns a randomly selected boolean (true or false) by generating a double value between 0 and 1,
//     * and returning true if the value is less than .5 and false if the value is greater than .5, mimicking
//     * the 50/50 percent chance of selecting true or false.
//     *
//     * @return randomly selected boolean true or false
//     */
//
//    public boolean getRandomBoolean() {
//        double temp = r.nextDouble();
//        return (temp < .50);
//    }
// --Commented out by Inspection STOP (3/27/15 8:02 PM)
/**
 * acctGen generates a random integer between 100,000,000 and 199,999,999 to be used as an account number and as the key
 *         associated with the account. A vector (acctNumberList) keeps track of all account numbers already generated.
 *         Each time a new account number is generated, the method checks the vector for that account number. If
 *         the vector contains the account number, a new one is generated until one is found that is not taken.
 *
 * @return returns the generated account number
 * */
public Integer acctGen() {
        /*Returns random account number.*/
        int temp = 100000000;
        Integer tempAccountNumber = temp += r.nextInt(99999999);

        while (this.acctNumberList.contains(tempAccountNumber))
            tempAccountNumber = temp += r.nextInt(99999999);

        this.acctNumberList.add(tempAccountNumber);

        return tempAccountNumber;
    }

    /**
     * nameGen generates pseudo-name for randomly created customers.
     *
     * @param minLength minimum length of the name to be created
     *
     * @param maxLength max length of the name to be created
     *
     * @return returns the generated name
     * */

    public String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }

    /**
     * getDubs returns a randomly created double with a specified minimum and maximum value.
     *
     * @param min the minimum desired value of the double
     *
     * @param max the maximum desired value of the double
     *
     * @return random double between the specified boundaries
     * */
    public double getDubs(double min, double max) {
        return ((r.nextDouble() * max) + min);
    }

    /**
     * getInts returns a randomly generated integer between the specified boundaries
     *
     * @param min the minimum desired value of the integer
     *
     * @param max the maximum desired value of the integer
     *
     * @return returns the integer generated within the desired boundaries
     * */
    public int getInts(int min, int max) {
        return ((r.nextInt(max)) + min);
    }

    /**
     * passwordGen generates a password for randomly created customers. Contains numbers and letters and is between a
     *             specified min and max length
     *
     * @param minLength the minimum desired length for the password
     *
     * @param maxLength the maximum desired length for the password
     *
     * @return returns the String password with a length within the desired boundaries
     * */
    public String passwordGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i < maxLength; i++) {
            temp += ALPHA_NUMERIC.charAt(r.nextInt(LENGTH_OF_ALPHA_NUMERIC));
        }
        return temp;
    }

    /**
     * accountTypeGen used for testing account creation, this method returns a bank account type chosen at random from
     *                a pre-defined array by returning the String at a randomly chosen index. This method may also
     *                generate "RETURN" to prevent the customer from getting stuck in infinite loops during trials
     *
     * @return returns SAVINGS, CHECKING, IRA, CD, MMA, or RETURN
     * */
    public String accountTypeGen() {
        String[] accounts = {"SAVINGS", "CHECKING", "IRA", "CD", "MMA", "RETURN"};
        return accounts[r.nextInt(6)];
    }
// --Commented out by Inspection START (3/27/15 8:02 PM):
//    /**
//     * informationGen used for testing menu navigation and login functionality, this method returns a menu choice
//     *                chosen at random from a pre-defined array by returning the String at a randomly chosen index. This
//     *                method may also generate "RETURN" to prevent the customer from getting stuck in infinite loops
//     *                during trials
//     *
//     * @return returns CHEX, CREDIT, ACCOUNTS, ALL, or RETURN
//     * */
//    public String informationGen() {
//        String[] accounts = {"CHEX" , "CREDIT", "ACCOUNTS", "ALL", "RETURN"};
//        return accounts[r.nextInt(5)];
//    }
// --Commented out by Inspection STOP (3/27/15 8:02 PM)
// --Commented out by Inspection START (3/27/15 8:02 PM):
//    /**
//     * transactionGen used for testing menu navigation and login functionality, this method returns a transaction type
//     *                chosen at random from a pre-defined array by returning the String at a randomly chosen index. This
//     *                method may also generate "RETURN" to prevent the customer from getting stuck in infinite loops
//     *                during trials
//     *
//     * @return returns CHEX, CREDIT, ACCOUNTS, ALL, or RETURN
//     * */
//    public String transactionGen() {
//        String[] accounts = {"DEPOSIT", "WITHDRAW", "TRANSFER", "ACCOUNTS", "RETURN"};
//        return accounts[r.nextInt(5)];
//    }
// --Commented out by Inspection STOP (3/27/15 8:02 PM)
// --Commented out by Inspection START (3/27/15 8:02 PM):
//    /**
//     * informationGen used for testing menu navigation and login functionality, this method returns a process selection
//     *                chosen at random from a pre-defined array by returning the String at a randomly chosen index. This
//     *                method may also generate "EXIT" to prevent the customer from getting stuck in infinite loops
//     *                during trials
//     *
//     * @return returns INFORMATION, TRANSACTION, ADDACCOUNT, or EXIT
//     * */
//    public String processGen() throws IndexOutOfBoundsException{
//        String[] accounts = {"INFORMATION", "TRANSACTION", "ADDACCOUNT", "EXIT"};
//        return accounts[r.nextInt(4)];
//    }
// --Commented out by Inspection STOP (3/27/15 8:02 PM)
    /**
     * informationGen used for testing menu navigation and login functionality, this method returns a bank account type
     *                chosen at random from a pre-defined array by returning the String at a randomly chosen index. This
     *                method may also generate "RETURN" to prevent the customer from getting stuck in infinite loops
     *                during trials
     *
     * @return returns CHEX, CREDIT, ACCOUNTS, ALL, or RETURN
     * */
    public String emailGen() {
        String[] emailDomains = {"yahoo.com", "gmail.com", "georgiasouthern.edu", "hotmail.com", "live.com",
                "genericbusiness.com", "someotheruniversity.edu", "notmyrealemail.com"};
        return (passwordGen(5, 10) + "@" + emailDomains[r.nextInt(8)]);
    }



}
