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


    }

    public Hashtable getAccountHashTable() {
        try {
            return accountHashtable;
        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : getAccountHashTable.\nHashTable has not yet" +
                    " been created. Returning temporary.");
            return new Hashtable();
        }
    }

    public RealBank getNewRandomBank() {

        RealBank randomBank = new RealBank(random.nameGen(0, 10), random.getInts(50, 500), random.getInts(50, 500));
        addRandomCustomers(random.getInts(50, 500));
        return randomBank;
    }

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

    public boolean addCustomer(Customer customer) {

        try {
            customerHashtable.put(customer.getUUID().hashCode(), customer);
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

    public boolean removeCustomer(Customer customer) {

        try {
            if (accountHashtable.contains(customer)) {
                accountHashtable.remove(customer.getUUID().hashCode());
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

    @Override
    public boolean hasAccount(Integer accountNumber) {
        try {
            return accountHashtable.containsKey(accountNumber);
        } catch (NullPointerException n) {
            System.out.printf("null pointer caught in RealBank : hasAccount");
            return false;
        }
    }

    @Override
    public boolean hasCustomer(UUID customerUUID) {
        try {
            return !customerHashtable.isEmpty() && customerHashtable.containsKey(customerUUID.hashCode());
        } catch (NullPointerException n) {
            System.out.println("Null pointer exception in RealBank : hasCustomer.");
            return false;
        }
    }

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

    public Customer requestCustomer(UUID customerID) {

        try {
            if (this.customerHashtable.containsKey(customerID.hashCode())) {
                return this.customerHashtable.get(customerID.hashCode());
            } else {
                return null;
            }

        } catch (NullPointerException n) {
            System.out.printf("Null pointer caught in RealBank : requestCustomer.\nUUID or customerHashTable not " +
                    "intitialized.");
            return null;
        }
    }

    @Override
    public Hashtable requestCustomerAccounts(UUID customerUUID) {
        if (this.customerHashtable.containsKey(customerUUID.hashCode())) {
            return customerHashtable.get(customerUUID.hashCode()).getAccountHashtable();
        } else {
            return null;
        }
    }

    int getNumberAccounts() {
        return this.NUMBER_OF_ACCOUNTS;
    }

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

    public Hashtable getCustomerTable() {
        try {
            return customerHashtable;
        } catch (NullPointerException q) {
            System.out.printf("Null pointer caught in RealBank : getCustomerTable\ncustomer hash table has not been" +
                    " initialized yet. Returning temp hashTable");
            return new Hashtable();
        }
    }

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
                    if (!(tempAcct == null))
                        accountHashtable.put(acctKey, tempAcct);

                }
            }
        } catch (NullPointerException n) {
            System.out.printf("null pointer caught in RealBank : updateAccountTable");
        } catch (NoSuchElementException e) {
            System.out.printf("no such element caught in RealBank : updateAccountTable");
        }


    }
}
