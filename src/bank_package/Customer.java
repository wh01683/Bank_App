package bank_package;

import acct.Account;
import utility.uScanner;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.UUID;


public class Customer implements Serializable {

    private final UUID CUSTOMER_ID;
    private final String NAME;
    private final String PASSWORD;
    private final CreditReport CREDIT_REPORT;
    private final ChexSystems CHEX_SCORE = new ChexSystems();
    private final String EMAIL_ADDRESS;
    private final Hashtable<Integer, Account> accountHashtable = new Hashtable<Integer, Account>(5);
    private int age;

    /**
     * Customer creates a custom customer object
     *
     * @param tempName        age of the new customer passed by the invoker
     * @param newEmail        email of the new customer
     * @param tempAge         age of the new customer
     * @param password        password of the new customer
     * @param newCreditReport credit report object of the new customer
     */
    public Customer(String tempName, String newEmail, int tempAge, String password, CreditReport newCreditReport) {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();

        this.age = tempAge;
        this.NAME = tempName;
        this.CREDIT_REPORT = newCreditReport;
        this.PASSWORD = password;
        this.EMAIL_ADDRESS = newEmail;
    }

    /**getAccountHashtable returns a hash table containing all the bank accounts owned by the customer
     * @return returns a Hashmap with the customer's accounts as the values and the integer account numbers as the keys*/
    public Hashtable<Integer, Account> getAccountHashtable() {
        return this.accountHashtable;
    }

    /**getPASSWORD returns the password the customer has on file. Customized Customers will create one when they register,
     *             randomized Customers will generate their own
     *
     * @return string form of the customer's password*/
    public String getPASSWORD() {
        return this.PASSWORD;
    }

    /**getUUID returns the customer's UUID object randomly assigned on creation
     * @return the customer's UUID object*/
    public UUID getUUID() {
        return this.CUSTOMER_ID;
    }

    /**getName returns the customer's name
     * @return customer's name*/
    public String getName() {
        return this.NAME;
    }

    /**getChexSystemsScore returns the customer's ChexSystems score. This score is what banks use to determine whether or not
     *                     a customer is eligible to open a checking account or not. The value ranges from 100 on the low side
     *                     to 899, just like a credit score. the Customer's chex score is randomly generated for both
     *                     randomized and customized Customers.
     *
     *  @return returns the customer's ChexScore*/
    public int getChexSystemsScore() {
        return this.CHEX_SCORE.getScore();
    }

    /**getCreditScore gets the user's Credit Score int value
     *
     * @return the customer's numeric credit score.*/
    public int getCreditScore() {
        return this.CREDIT_REPORT.getCreditScore();
    }

    /**addAccount adds an account object to the customer's account hash table
     * @param newAccount new account object added by the customer*/
    public void addAccount(Account newAccount) {
        if (!(newAccount == null))
            this.accountHashtable.put(newAccount.getACCOUNT_NUMBER(), newAccount);

    }

    /**toString returns a string representation of the customer object. the customer's UUID, name, password, age, credit
     *          score, and chex score are displayed with columns in between them.
     * @return string representation of the customer.*/
    @Override
    public String toString() {
        return String.format("||%-36s||%-20s||%-20s||%-3d||%-4d||%-4d||", this.CUSTOMER_ID, this.NAME,
                this.PASSWORD, this.age, this.getCreditScore(), this.getChexSystemsScore());
    }

    /**getAccount returns the customer's account object associated with a specified account number.
     * @param accountNumber integer value of the account to be retrieved.
     * @return returns the account object associated with the given account number*/
    public Account getAccount(Integer accountNumber) {
        try {
            final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", 0, 200000000);

            if (this.accountHashtable.containsKey(accountNumber))
                return this.accountHashtable.get(accountNumber);
            else {
                System.out.println("Could not locate your account. Please re-enter your account number.");
                return getAccount(ACCOUNT_NUMBER_SCANNER.intGet());
            }
        } catch (NullPointerException q) {
            System.out.printf("null pointer caught in Customer : getAccount");
            return null;
        }
    }

    /**
     * getEmail gets the address associated with the customer
     *
     * @return email address of the customer
     */

    public String getEmail() {
        return this.EMAIL_ADDRESS;
    }
}
