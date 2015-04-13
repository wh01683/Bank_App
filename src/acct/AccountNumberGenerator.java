package acct;

import java.util.Random;
import java.util.Vector;

/**
 * Created by robert on 4/12/2015.
 */
public class AccountNumberGenerator {

    private static Vector<Integer> acctNumberList;
    Random r = new Random();

    public AccountNumberGenerator(Vector<Integer> newAcctNumberList) {
        if (newAcctNumberList == null) {
            acctNumberList = new Vector<Integer>(50);
            acctNumberList.add(100000000);
        } else {
            acctNumberList = newAcctNumberList;
        }
    }

    public static Vector<Integer> getAcctNumberList() {
        return acctNumberList;
    }

    public static void setAcctNumberList(Vector<Integer> acctNumberList) {
        AccountNumberGenerator.acctNumberList = acctNumberList;
    }

    /**
     * acctGen generates a random integer between 100,000,000 and 199,999,999 to be used as an account number and as the key
     * associated with the account. A vector (acctNumberList) keeps track of all account numbers already generated.
     * Each time a new account number is generated, the method checks the vector for that account number. If
     * the vector contains the account number, a new one is generated until one is found that is not taken.
     *
     * @return returns the generated account number
     */


    public Integer acctGen() {
        /*Returns random account number.*/
        int temp = 100000000;
        Integer tempAccountNumber = (temp += r.nextInt(99999999));

        while (acctNumberList.contains(tempAccountNumber)) {
            tempAccountNumber = (temp += r.nextInt(99999999));
        }

        acctNumberList.add(tempAccountNumber);

        return tempAccountNumber;
    }
}
