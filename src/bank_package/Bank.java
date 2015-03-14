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
    PrintWriter writer = getPW("C:\\Users\\robert\\Desktop\\bankinformation.txt");
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

    public void writeInfoToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT")) {
            writer = getPW(fileName);
        }
        Enumeration<Integer> enumKeys = customerHashtable.keys();
        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Customer temp = customerHashtable.get(key);
            writer.println(temp.toString());
            Hashtable<Integer, Account> tempHash = temp.getAccountHashtable();
            Enumeration<Integer> acctKeys = tempHash.keys();
            writer.println("------------------------------------------");
            writer.println("-------------CUSTOMER'S ACCOUNTS----------");
            writer.println("------------------------------------------");
            writer.println(getAccountHeaders());

            while (acctKeys.hasMoreElements()) {
                Integer acctKey = acctKeys.nextElement();
                Account tempAcct = tempHash.get(acctKey);
                writer.println(tempAcct.toString());
            }
            writer.println("------------------------------------------");
            writer.println("-------------NEW CUSTOMER-----------------");
            writer.println("------------------------------------------");
            writer.println(getCustomerHeaders());

        }
        writeAccountInfoToFile(fileName);
        writeCustomerInfoToFile(fileName);

        writer.close();
        System.out.println("Finished writing to file.");
    }

    private String getAccountHeaders() {

        String temp = String.format("%-10s %-10s %-20s %-20s %-36s %-4s %-6s %-4s", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");

        return temp;
    }


    public String getCustomerHeaders() {
        String temp = String.format("%-36s %-20s %-20s %-3s %-4s %-4s", "CUSTOMER ID", "NAME", "PASSWORD", "AGE", "CRED", "CHEX");

        return temp;
    }

    public void writeCustomerInfoToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT"))
            writer = getPW(fileName);

        Enumeration<Integer> enumKeys = customerHashtable.keys();
        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                writer.println("------------------------------------------");
                writer.println(getCustomerHeaders());
                writer.println("------------------------------------------");
                tempCount = 0;
            }
            Integer key = enumKeys.nextElement();
            Customer temp = customerHashtable.get(key);
            writer.println(temp.toString());
            tempCount++;
        }
        //writer.close();
        System.out.println("Finished writing customers to file.");

    }

    public void writeAccountInfoToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT"))
            writer = getPW(fileName);


        Enumeration<Integer> enumKeys = this.accountHashtable.keys();
        int tempCount = 10;

        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                writer.println("------------------------------------------");
                writer.println(getAccountHeaders());
                writer.println("------------------------------------------");
                tempCount = 0;
            }

            Integer key = enumKeys.nextElement();
            Account temp = this.accountHashtable.get(key);
            writer.println(temp.toString());
            tempCount++;
        }

        //writer.close();
        System.out.println("Finished writing accounts to file.");
    }

}
