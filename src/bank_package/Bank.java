package bank_package;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.BiConsumer;

class Bank {
    private final boolean RANDOM_BANK;
    File test = new File("tests.xml");
    PrintWriter writer = getPW("C:\\Users\\robert\\Desktop\\newionuflkijan.txt");
    private String name = "Sea Island Bank - Sandiest Bank in Idaho!";
    private int numberCustomers;
    private int numberAccounts;
    private Hashtable<Integer, Customer> customerHashtable;
    //private XMLEncoder x = new XMLEncoder(getFOS("C:\\Users\\robert\\Desktop\\tests.xml"));
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

        Enumeration<Integer> enumKeys = customerHashtable.keys();

        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Customer temp = customerHashtable.get(key);
            System.out.println(temp.getName());


            //writer.printf((customerHashtable.get(key).getName()));
        }

    }

    protected Hashtable getCustomerTable() {
        return this.customerHashtable;
    }

}
