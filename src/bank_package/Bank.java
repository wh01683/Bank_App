package bank_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;


public class Bank {
    private final boolean RANDOM_BANK;
    File test = new File("tests.xml");
    PrintWriter writer = getPW("C:\\Users\\robert\\Desktop\\newionuflkijan.txt");
    private String name = "Sea Island Bank - Sandiest Bank in Idaho!";
    private int numberCustomers;
    private int numberAccounts;
    private Hashtable<Integer, Customer> customerHashtable;
    private int[] keyArray;

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
        for (int i = 0; i < numberCustomers; i++) {
            Customer temp = new Customer(true);
            this.customerHashtable.put(temp.getUUID().hashCode(), temp);
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

    public void printCustomerInfoToFile() {

        Enumeration<Integer> enumKeys = customerHashtable.keys();
        System.out.println("CUSTOMER ID --------------------------PASSWORD ---NAME ------------------------------" +
                "------------AGE-CRED");
        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Customer temp = customerHashtable.get(key);
            temp.printAllCustomerInformation();
        }

    }

    protected Hashtable getCustomerTable() {
        return this.customerHashtable;
    }

}
