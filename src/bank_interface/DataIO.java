package bank_interface;


import bank_package.Bank;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

/*DATA Input/Output class for storing all the write -to-file methods and headers, etc.*/
public class DataIO {
    private final Bank THIS_BANK;
    private final Hashtable customerHashTable;
    private final Hashtable accountHashTable;
    private PrintWriter writer = getPW(System.getProperty("user.dir") + "\\bankInformation.txt");
    private ObjectOutputStream bankDataWriter = getOS(getFS(System.getProperty("user.dir") + "\\bankInformation.txt"));

    public DataIO(Bank bank) {
        this.THIS_BANK = bank;
        this.customerHashTable = this.THIS_BANK.getCustomerTable();
        this.accountHashTable = this.THIS_BANK.getAccountHashTable();
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

    public void writeCustomerAndAccountInformationToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT")) {
            writer = getPW(fileName);
        }
        Enumeration<Integer> enumKeys = this.customerHashTable.keys();
        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            writer.println(customerHashTable.get(key).toString());
            Enumeration<Integer> acctKeys = this.accountHashTable.keys();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            writer.println(getCustomerHeaders());
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            writer.println(getAccountHeaders());

            while (acctKeys.hasMoreElements()) {
                Integer acctKey = acctKeys.nextElement();
                writer.println(this.accountHashTable.get(acctKey).toString());
            }
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            writer.println(getCustomerHeaders());
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");


        }

        writer.close();
        System.out.println("Finished writing to file.");
    }

    public void writeCustomerInformationToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT"))
            writer = getPW(fileName);

        Enumeration<Integer> enumKeys = customerHashTable.keys();

        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                writer.println(getCustomerHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }
            writer.println(customerHashTable.get(enumKeys.nextElement()).toString());
            tempCount++;
        }
        writer.close();
        System.out.println("Finished writing customers to file.");

    }

    public void writeAccountInformationToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT"))
            writer = getPW(fileName);


        Enumeration<Integer> enumKeys = this.accountHashTable.keys();
        int tempCount = 10;

        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                writer.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }

            writer.println(this.accountHashTable.get(enumKeys.nextElement()).toString());
            tempCount++;
        }

        writer.close();
        System.out.println("Finished writing accounts to file.");
    }

    public void saveAllBankDataToFile(String fileName) throws IOException {
        if (!(fileName.equalsIgnoreCase("DEFAULT")))
            this.bankDataWriter = getOS(getFS(fileName));

        Enumeration<Integer> enumKeys = customerHashTable.keys();
        try {
            while (enumKeys.hasMoreElements()) {
                Integer key = enumKeys.nextElement();
                this.bankDataWriter.writeObject(customerHashTable.get(key));

                Enumeration<Integer> acctKeys = accountHashTable.keys();
                writer.println(getAccountHeaders());

                while (acctKeys.hasMoreElements()) {
                    writer.println(accountHashTable.get(acctKeys.nextElement()).toString());
                }

            }
/*NOT FINISHED
        * ToDo: learn how to write and read objects from file without losing data*/
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ObjectOutputStream getOS(FileOutputStream out) {
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(out);

            return os;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public FileOutputStream getFS(String fileName) {
        FileOutputStream fs;
        try {
            fs = new FileOutputStream(fileName);

            return fs;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-7s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }


    String getCustomerHeaders() {
        return String.format("||%-36s||%-20s||%-20s||%-3s||%-4s||%-4s||", "CUSTOMER ID", "NAME", "PASSWORD", "AGE", "CRED", "CHEX");
    }


}
