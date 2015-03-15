package bank_package;

import acct.Account;
import acct.AccountFactory;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;


public class Bank implements Serializable {

    private final Random r = new Random();
    private final RandomGenerator random = new RandomGenerator();
    private final AccountFactory testAccountFactory = new AccountFactory();
    private int NUMBER_OF_CUSTOMERS;
    private int NUMBER_OF_ACCOUNTS;
    private String name = "Sea Island Bank - Sandiest Bank in Idaho!";
    private Hashtable<Integer, Customer> customerHashtable;
    private Hashtable<Integer, Account> accountHashtable;

    public Bank(String name, int numberAccounts, int numberCustomers) {
        this.name = name;
        this.NUMBER_OF_ACCOUNTS = numberAccounts;
        this.NUMBER_OF_CUSTOMERS = numberCustomers;

    }

    public Hashtable getAccountHashTable() {
        return this.accountHashtable;
    }

    public Bank getNewRandomBank() {

        Bank randomBank = new Bank(random.nameGen(0, 10), random.getInts(50, 500), random.getInts(50, 500));
        addRandomCustomers(random.getInts(50, 500));
        return randomBank;
    }

    public void addRandomCustomers(int numberCustomers) {
        this.customerHashtable = new Hashtable<Integer, Customer>(numberCustomers * 2);
        this.accountHashtable = new Hashtable<Integer, Account>(numberCustomers * 10);
        for (int i = 0; i < numberCustomers; i++) {
            Customer tempCustomer = new Customer();
            this.customerHashtable.put(tempCustomer.getUUID().hashCode(), tempCustomer);

            for (int k = 0; k < r.nextInt(10); k++) { //generates anywhere between 10 and 0 random accounts
                Account tempAccount = testAccountFactory.getRandomAccount(tempCustomer);
                if(!(tempAccount == null))
                    tempCustomer.addAccount(tempAccount);
            }
            updateAccountTable();
        }
    }

    public void addCustomer(Customer customer){

        this.customerHashtable.put(customer.getUUID().hashCode(), customer);
        Enumeration<Integer> acctKeys = customer.getAccountHashtable().keys();
        while (acctKeys.hasMoreElements()) {
            Integer acctKey = acctKeys.nextElement();
            Account tempAcct = customer.getAccount(acctKey);
            if (!(tempAcct == null))
                this.accountHashtable.put(acctKey, tempAcct);

        }

    }

    public Customer requestCustomer(UUID customerID){

        if(this.customerHashtable.containsKey(customerID.hashCode())){
            return customerHashtable.get(customerID.hashCode());
        }
        else{
            return null;
        }

    }

    int getNumberAccounts() {
        return this.NUMBER_OF_ACCOUNTS;
    }

    int getNUMBER_OF_CUSTOMERS() {
        return this.NUMBER_OF_CUSTOMERS;
    }

    @Override
    public String toString() {
        return this.name + "-" + this.getNUMBER_OF_CUSTOMERS() + "-" + this.getNumberAccounts();
    }

    public Hashtable getCustomerTable() {
        return this.customerHashtable;
    }

    public void updateAccountTable() {

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
                    this.accountHashtable.put(acctKey, tempAcct);

            }
        }
    }
}
