package bank_package;

import acct.Account;

import java.util.Hashtable;

/**Bank Interface defines the methods available to the Bank Proxy and ultimately the customer. the goal is to restrict
* their access and not give customers the ability to edit balances(aside from making a deposit or withdrawal), add/remove
* other customers, etc.*/

interface Bank {

    /**
     * addAccount adds an account object to the bank's account tables and to the appropriate customer's account tables
     *
     * @param newAccount account object to be added
     */
    void addAccount(Account newAccount);
/**
 * requestCustomer requests a single customer object from the real bank using a UUID object
 *
 * @param email email object used to locate the correct customer requested
 * @return returns the customer object requested
 * */
Customer requestCustomer(String email);
/**
 * requestCustomerAccounts returns the account hashtable of the customer associated with the given UUID
 *
 * @param email email used to locate the correct customer and retrieve the correct accounts
 * @return Hashtable object containing Accounts and their associated account numbers as keys.
 * */
Hashtable requestCustomerAccounts(String email);

    /**
     * removeAccount removes a specific account from the bank's account hashtable and from the customer's personal account
     * hash table
     *
     * @param accountNumber account number of the account to be deleted
     * @return true if account successfully removed, false otherwise
     */
    boolean removeAccount(Integer accountNumber);

/**
 * startLoginProcess asks the real bank if it contains the account object associated with the given account number
 *
 * @param accountNumber account number of the requested account
 * @return true if account exists, false otherwise
 * */
boolean hasAccount(Integer accountNumber);
/**
 * hasCustomer asks the real bank if it contains the customer associated with the given UUID
 *
 * @param email email of the requested customer
 * @return true if customer exists, false otherwise
 * */
boolean hasCustomer(String email);
/**
 * addCustomer adds specific customer to the bank's customer hash table and adds its accounts (if any) to the bank's
 *             account hash table
 * @param customer customer object to be added
 * @return returns true if added successfully, false otherwise
 * */
boolean addCustomer(Customer customer);

}
