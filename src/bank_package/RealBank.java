package bank_package;

import acct.Account;
import acct.AccountFactory;
import utility.RandomGenerator;

import java.io.Serializable;
import java.util.*;


public class RealBank implements Serializable, Bank {

    private static final Random r = new Random();
    private static final RandomGenerator random = new RandomGenerator();
    private static final AccountFactory testAccountFactory = new AccountFactory();
    private int NUMBER_OF_CUSTOMERS;
    private int NUMBER_OF_ACCOUNTS;
    private String name = "Sea Island Bank - Sandiest Bank in Idaho!";
    private Hashtable<Integer, Customer> customerHashtable;
    private Hashtable<Integer, Account> accountHashtable;
    private Hashtable<Integer, UUID> emailUUIDHashTable;
    /*ToDo: Declare Hashtables static and adapt saveData methods to cope with this.*/

    public RealBank(String name, int numberAccounts, int numberCustomers) {
        this.name = name;
        this.NUMBER_OF_ACCOUNTS = numberAccounts;
        this.NUMBER_OF_CUSTOMERS = numberCustomers;
        if (customerHashtable == null) {
            customerHashtable = new Hashtable<Integer, Customer>(numberCustomers * 2);
        }
        if (accountHashtable == null) {
            accountHashtable = new Hashtable<Integer, Account>(numberAccounts * 2);
        }
        if (emailUUIDHashTable == null) {
            emailUUIDHashTable = new Hashtable<Integer, UUID>(numberAccounts * 2);
        }


    }

    /**
     * getAccountHashtable retrieves the current comprehensive account hashtable of the bank
     *
     * @return returns Hashtable object with user's accounts. keys associated with the accounts are
     *         the account numbers themselves.
     */
    public Hashtable getAccountHashTable() {
        try {
            return accountHashtable;
        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : getAccountHashTable.\nHashTable has not yet" +
                    " been created. Returning temporary.");
            return new Hashtable();
        }
    }

    /**getNewRandomBank retrieves new randomly generated bank; used for testing purposes.
     *       Properties:
     *                  Name between 0 and 10 chars long
     *                  50 to 500 accountHashtable initial size
     *                  50 to 500 customerHashtable initial size
     *                  50 to 500 randomly generated customers added,
     *                  each customer has 0 to 10 randomly generated accounts
     *
     * @return randomly created bank
     * */
    public RealBank getNewRandomBank() {

        RealBank randomBank = new RealBank(random.nameGen(0, 10), random.getInts(50, 500), random.getInts(50, 500));
        addRandomCustomers(random.getInts(50, 500));
        return randomBank;
    }

    /**addRandomCustomers adds a specified number of random customers, each with 0 to 10 accounts
     *
     ** @param numberCustomers number of customers to be created.
     *
     * */
    public void addRandomCustomers(int numberCustomers) {

        try {

            if (customerHashtable == null) {
                customerHashtable = new Hashtable<Integer, Customer>(numberCustomers * 2);
            }
            if (accountHashtable == null) {
                accountHashtable = new Hashtable<Integer, Account>(numberCustomers * 10);
            }


            for (int i = 0; i < numberCustomers; i++) {
                Customer tempCustomer = new Customer();
                customerHashtable.put(tempCustomer.getUUID().hashCode(), tempCustomer);
                emailUUIDHashTable.put(tempCustomer.getEmail().hashCode(), tempCustomer.getUUID());

                for (int k = 0; k < r.nextInt(10); k++) { //generates anywhere between 10 and 0 random accounts
                    Account tempAccount = testAccountFactory.getRandomAccount(tempCustomer);
                    if (!(tempAccount == null))
                        tempCustomer.addAccount(tempAccount);
                }
                this.addCustomer(tempCustomer);
            }
        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : addRandomCustomers");
        }
    }

    /**addCustomer adds a new customer to the bank's customer hashtable and adds all accounts owned by the customer to
     *             the bank's account hash table.
     *
     * @param customer customer object to be added.
     *
     * @return true if the account was successfully added, false otherwise*/
    public boolean addCustomer(Customer customer) {

        try {
            customerHashtable.put(customer.getUUID().hashCode(), customer);
            emailUUIDHashTable.put(customer.getEmail().hashCode(), customer.getUUID());
            this.NUMBER_OF_CUSTOMERS++;
            Enumeration<Integer> acctKeys = customer.getAccountHashtable().keys();
            while (acctKeys.hasMoreElements()) {
                Integer acctKey = acctKeys.nextElement();
                Account tempAcct = customer.getAccount(acctKey);
                if (!(tempAcct == null)) {
                    accountHashtable.put(acctKey, tempAcct);
                    this.NUMBER_OF_ACCOUNTS++;
                }
            }
            return true;
        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : addCustomer");
            return false;
        }

    }

    /**addAccount adds new account object to the bank's account hash table and adds the same account to the OWNER's
     *            own account hashtable
     *
     * @param account account object to be added
     * */
    public void addAccount(Account account) {
        try {
            accountHashtable.put(account.getACCOUNT_NUMBER(), account);
            customerHashtable.get(account.getOwner().hashCode()).addAccount(account);
            this.NUMBER_OF_ACCOUNTS++;
        } catch (NoSuchElementException e) {
            System.out.printf("No such element caught in RealBank : addAccount.\nOwner not in system.");

        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : addAccount.");
        }
    }

    /**removeCustomer removes a customer and all of its associated accounts from the bank's hash tables
     *
     * @param customer customer object to be removed
     *
     * @return true if successfully removed, false otherwise
     * */
    public boolean removeCustomer(Customer customer) {

        try {

            if (customerHashtable.contains(customer)) {

                customerHashtable.remove(customer.getUUID().hashCode());
                emailUUIDHashTable.remove(customer.getEmail().hashCode());

                this.NUMBER_OF_CUSTOMERS--;

                Enumeration<Integer> acctKeys = customer.getAccountHashtable().keys();
                while (acctKeys.hasMoreElements()) {
                    Integer acctKey = acctKeys.nextElement();
                    Account tempAcct = customer.getAccount(acctKey);
                    if (!(tempAcct == null)) {
                        accountHashtable.remove(acctKey);
                        this.NUMBER_OF_ACCOUNTS--;
                    }
                }
                return true;
            }
        } catch (NullPointerException n) {
            System.out.printf("Null pointer exception caught in RealBank : removeCustomer");
            return false;
        }
        return false;
    }

    /**startLoginProcess overrides Bank interface's startLoginProcess. Used by BankProxy object. Method used to ask the bank whether or not
     *            it has an account object associated with the given account number
     *
     * @param accountNumber account number interger to check for.
     *
     * @return true if the bank has the account, false otherwise.
     * */
    @Override
    public boolean hasAccount(Integer accountNumber) {
        try {
            return accountHashtable.containsKey(accountNumber);
        } catch (NullPointerException n) {
            System.out.printf("null pointer caught in RealBank : startLoginProcess");
            return false;
        }
    }

    /**hasCustomer overrides Bank interface's hasCustomer. used by BankProxy object. Method used to ask the bank whether
     *             or not it has a customer object associated with the given UUID object
     *
     * @param email email associated with the customer. Customer's hash key calculated from the UUID.
     *
     * @return true if found, false otherwise.
     * */

    @Override
    public boolean hasCustomer(String email) {
        try {
            return !customerHashtable.isEmpty() && hasCustomer(emailUUIDHashTable.get(email.hashCode()));
        } catch (NullPointerException n) {
            System.out.println("Null pointer exception in RealBank : hasCustomer.");
            return false;
        }
    }

    /**
     * hasCustomer overrides Bank interface's hasCustomer. used by BankProxy object. Method used to ask the bank whether
     * or not it has a customer object associated with the given UUID object
     *
     * @param customerUUID UUID object associated with the customer. Customer's hash key calculated from the UUID.
     * @return true if found, false otherwise.
     */

    public boolean hasCustomer(UUID customerUUID) {
        try {
            return !customerHashtable.isEmpty() && customerHashtable.containsKey(customerUUID.hashCode());
        } catch (NullPointerException n) {
            System.out.println("Null pointer exception in RealBank : hasCustomer.");
            return false;
        }
    }

    /**removeAccount removes account object associated with the given account number from both the account hash table
     *               and the account hashtable associated with the customer
     *
     * @param accountNumber account number of the account to be removed
     *
     * @return true if successfully removed, false otherwise
     * */
    public boolean removeAccount(Integer accountNumber) {

        try {
            if (accountHashtable.containsKey(accountNumber)) {
                Account tempAccountToBeRemoved = accountHashtable.get(accountNumber);
                customerHashtable.get(accountHashtable.get(accountNumber).getOwner().hashCode()).getAccountHashtable().remove(accountNumber);
                accountHashtable.remove(accountNumber);
                this.NUMBER_OF_ACCOUNTS--;
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : removeAccount.");
            return false;
        }
    }

    /**requestCustomer method used to retrieve customer from the realBank using a given UUID object
     *
     * @param email email associated with the customer.
     *
     * @return true if successfully removed, false otherwise.
     * */
    public Customer requestCustomer(String email) {

        try {
            if (this.customerHashtable.containsKey(emailUUIDHashTable.get(email.hashCode()).hashCode())) {
                return this.customerHashtable.get(emailUUIDHashTable.get(email.hashCode()).hashCode());
            } else {
                return null;
            }

        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : requestCustomer.\nUUID or customerHashTable not " +
                    "intitialized.");
            return null;
        }
    }

    /**requestCustomerAccounts returns Hashtable containing accounts associated with a specific customer with the
     *                         specified UUID object
     *
     * @param email email object of the customer to retrieve accounts for
     *
     * @return returns Hashtable of account objects specific to the specified customer using integer account numbers as
     *         keys
     * */
    @Override
    public Hashtable requestCustomerAccounts(String email) {
        if (this.customerHashtable.containsKey(emailUUIDHashTable.get(email.hashCode()).hashCode())) {
            return customerHashtable.get(emailUUIDHashTable.get(email.hashCode()).hashCode()).getAccountHashtable();
        } else {
            return null;
        }
    }


    /**getNumberAccounts returns current total number of accounts registered with the bank
     *
     * @return total number of accounts registered with the bank
     * */
    int getNumberAccounts() {
        return this.NUMBER_OF_ACCOUNTS;
    }

    /**getNUMBER_OF_CUSTOMERS returns current number of customers registered with the bank
     *
     * @return number of customers registered with the bank*/
    int getNUMBER_OF_CUSTOMERS() {
        return this.NUMBER_OF_CUSTOMERS;
    }

    public String toString() {
        try {
            return this.name + "-" + this.getNUMBER_OF_CUSTOMERS() + "-" + this.getNumberAccounts();
        } catch (NullPointerException e) {
            System.out.printf("null pointer caught RealBank : toString.\n instance likely has not been instantiated yet.");
            return null;
        }
    }

    /**getCustomerTable returns the customer hash table of the bank. if the current customerHashtable is null,
     *                  a new Hashtable object is returned
     *
     * @return new generic Hashtable object
     * */
    public Hashtable getCustomerTable() {
        try {
            return customerHashtable;
        } catch (NullPointerException q) {
            System.out.printf("Null pointer caught in RealBank : getCustomerTable\ncustomer hash table has not been" +
                    " initialized yet. Returning temp hashTable");
            return new Hashtable();
        }
    }

    /**updateAccountTable updates the account table by cycling through the each customer in the customer hash table and
     *                    adding each account of the customer to the bank's comprehensive account hash table IFF the
     *                    bank's comprehensive account hash table does not already contain the account
     * */
    public void updateAccountTable() {
        try {
            Enumeration<Integer> enumKeys = customerHashtable.keys();

            while (enumKeys.hasMoreElements()) {
                Integer key = enumKeys.nextElement();
                Customer temp = customerHashtable.get(key);

                Hashtable<Integer, Account> tempHash = temp.getAccountHashtable();
                Enumeration<Integer> acctKeys = tempHash.keys();


                while (acctKeys.hasMoreElements()) {
                    Integer acctKey = acctKeys.nextElement();
                    Account tempAcct = tempHash.get(acctKey);
                    if (!(tempAcct == null) && !accountHashtable.contains(tempAcct))
                        accountHashtable.put(acctKey, tempAcct);
                }
            }
        } catch (NullPointerException n) {
            System.out.printf("null pointer caught in RealBank : updateAccountTable");
        } catch (NoSuchElementException e) {
            System.out.printf("no such element caught in RealBank : updateAccountTable");
        }


    }

    public UUID getCustomerUUID(String email) {

        try {
            if (emailUUIDHashTable.containsKey(email.hashCode())) {
                return emailUUIDHashTable.get(email.hashCode());
            } else {
                return null;
            }

        } catch (NullPointerException l) {
            System.out.printf("Customer not found.");
            return null;
        }
    }
}
