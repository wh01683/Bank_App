package bank_package;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.UUID;

class Bank {

    File test = new File("tests.xml");
    private String name = "Sea Island Bank - No Shoes, No Problem!";
    private int numberCustomers;
    private int numberAccounts;
    private Hashtable<Customer, UUID> testList;
    private XMLEncoder x = new XMLEncoder(getStreams("C:\\Users\\robert\\Desktop\\tests.xml"));

    public Bank(String name, int numberAccounts, int numberCustomers) {
        this.name = name;
        this.numberAccounts = numberAccounts;
        this.numberCustomers = numberCustomers;
    }

    public int getNumberAccounts() {
        return this.numberAccounts;
    }

    public int getNumberCustomers() {
        return this.numberCustomers;
    }


    private FileOutputStream getStreams(String fileName) {

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
}
