package bank_package;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.UUID;

class Bank {

    private final boolean RANDOM_BANK;
    File test = new File("tests.xml");
    PrintWriter writer = getPW("C:\\Users\\robert\\Desktop\\tests.xml");
    private String name = "Sea Island Bank - Sandiest Bank in Idaho!";
    private int numberCustomers;
    private int numberAccounts;
    private Hashtable<Integer, Customer> customerHashtable;
    private XMLEncoder x = new XMLEncoder(getFOS("C:\\Users\\robert\\Desktop\\tests.xml"));

    public Bank(String name, int numberAccounts, int numberCustomers, boolean random) {
        this.name = name;
        this.numberAccounts = numberAccounts;
        this.numberCustomers = numberCustomers;
        this.RANDOM_BANK = random;

        if (this.RANDOM_BANK) {
            this.customerHashtable = new Hashtable<Integer, Customer>(this.numberCustomers * 2);
            Customer temp = new Customer(this.RANDOM_BANK);
            for (int i = 0; i < this.numberCustomers; ++i) {
                customerHashtable.put(temp.getUUID().hashCode(), temp);
            }

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

    private FileOutputStream getFOS(String fileName) {

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName);
            return fos;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public void printCustomerInfoToFile() {
        x.writeObject(this.customerHashtable);
    }

}
