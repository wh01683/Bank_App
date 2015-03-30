package acct;

import bank_package.Customer;

import java.util.UUID;

/**

 * Created by robert on 3/10/2015.
 */


public interface Account {

    /**
     * Returns the balance of the account.
     *
     * @return returns the balance of the account
     */
    double getBalance();
/**
 * Returns the String representation of the Account Type (i.e. Savings, Checking, etc.)
 *
 * @return returns the type of the account as a String.
 * */
String getType();
/**
 * Obtains the minimum balance required to open the account
 *
 * @return returns the minimum balance required to open the account.
 * */
double getMinRequiredBalance();
/**
 * Deposits a given amount into the account and returns the value deposited.
 *
 * @param amount amount of money to deposit
 *
 * @return returns the amount deposited into the account for confirmation.
 * */
double deposit(double amount);

    /**
     * adds a given amount to the account's balance.
     *
     * @param amount amount to be withdrawn
     * @return returns the amount withdrawn from the account for confirmation
     */

    double withdraw(double amount);

    /**
     * returns the Account's integer account number. This value is also the key associated with the account's position in the hashtable
     *
     * @return returns the account nuber associated with the account.
     * */
    Integer getACCOUNT_NUMBER();

    /**
     * This method is used when a withdraw is attempted and will verify that the customer is allowed to make a withdrawal
     * and that the account is in such a state.
     * @param withdrawal amount to be withdrawn
     * @return returns true if withdraw allowed, returns false otherwise.
     * */
    boolean checkWithdrawLimits(double withdrawal);

    /**
     * Applies for a new account using the customer's information and the customer's proposed opening balance to decide
     * whether or not they qualify for the account.
     *
     * @param customer customer examined for qualification. The account factory obtains the information it needs from
     *                 the customer object passed through the params.
     * @param openingBalance proposed opening balance to open the account with.
     * @return will return the newly created account if the customer qualified, else null is returned.
     *
     **/
    Account applyForNewAccount(Customer customer, double openingBalance);

    /**
     * returns the UUID object of the owner of the account
     *
     * @return returns the UUID object of the owner of the account.
     * */
    UUID getOwner();
}
