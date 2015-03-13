package bank_package;

import acct.Account;
import acct.AccountFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


public class Bank {
    private final boolean RANDOM_BANK;
    private final int numberCustomers;
    private final int numberAccounts;
    private final int[] keyArray;
    File test = new File("tests.xml");
    PrintWriter writer = getPW("C:\\Users\\robert\\Desktop\\newionuflkijan.txt");
    private Random r = new Random();
    private String name = "Sea Island Bank - Sandiest Bank in Idaho!";
    private Hashtable<Integer, Customer> customerHashtable;
    private Hashtable<Integer, Account> accountHashtable;
    private AccountFactory testAccountFactory = new AccountFactory();

    public Bank(String name, int numberAccounts, int numberCustomers, boolean random) {
        this.name = name;
        this.numberAccounts = numberAccounts;
        this.numberCustomers = numberCustomers;
        this.RANDOM_BANK = random;
        this.keyArray = new int[numberCustomers];

        if (this.RANDOM_BANK) {
            this.customerHashtable = new Hashtable<Integer, Customer>(this.numberCustomers * 2);
            for (int i = 0; i < this.numberCustomers; ++i) {
                Customer temp = new Customer(this.RANDOM_BANK);
                customerHashtable.put(temp.getUUID().hashCode(), temp);
                keyArray[i] = temp.getUUID().hashCode();
            }


        }

    }

    public void addCustomer(int numberCustomers) {
        this.customerHashtable = new Hashtable<Integer, Customer>(numberCustomers * 2);
        this.accountHashtable = new Hashtable<Integer, Account>(numberCustomers * 10);
        for (int i = 0; i < numberCustomers; i++) {
            Customer tempCustomer = new Customer(true);
            this.customerHashtable.put(tempCustomer.getUUID().hashCode(), tempCustomer);

            for (int k = 0; k < r.nextInt(10); k++) { //generates anywhere between 10 and 0 random accounts
                Account tempAccount = testAccountFactory.getRandomAccount(tempCustomer);
                tempCustomer.addAccount(tempAccount);
            }

            updateAccountTable();

        }
    }

    public int getNumberAccounts() {
        return this.numberAccounts;
    }

    public int getNumberCustomers() {
        return this.numberCustomers;
    }

    private PrintWriter getPW(String fileName) {
        PrintWriter pw;
        try {
            pw = new PrintWriter(fileName);
            return pw;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public void printCustomerInfo() {

        Enumeration<Integer> enumKeys = customerHashtable.keys();

        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Customer temp = customerHashtable.get(key);
            System.out.println(temp.toString());
            temp.printAccountInformation();
        }

    }

    public void printAccountInfo() {

        Enumeration<Integer> enumKeys = this.accountHashtable.keys();

        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Account temp = accountHashtable.get(key);
            System.out.println(temp.toString());
        }

    }
    @Override
    public String toString() {
        return this.name + "-" + this.getNumberCustomers() + "-" + this.getNumberAccounts();
    }

    Hashtable getCustomerTable() {
        return this.customerHashtable;
    }

    private void updateAccountTable() {

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
