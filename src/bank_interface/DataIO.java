package bank_interface;


import bank_package.RealBank;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

/*DATA Input/Output class for storing all the write -to-file methods and headers, etc.*/
public class DataIO {
    String hiding = "ishallpass";
    private RealBank realBank;
    private Hashtable customerHashTable = new Hashtable(50);
    private Hashtable accountHashTable = new Hashtable(500);
/*
    private PrintWriter writer = getPW(System.getProperty("user.dir") + "\\bankInformation.txt");
*/


    public DataIO() {
    }

    void printAllCustomerPrivateInformation(Integer customerHashKey, Hashtable customerAccounts) {

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(getCustomerHeaders());
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(this.getRealBank().getCustomerTable().get(customerHashKey).toString());

        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        printAccountInformation(customerAccounts);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Finished printing customer information...");



    }

    void printAccountInformation(Hashtable customerAccounts) {

        int tempCount = 10;
        Enumeration<Integer> enumer = customerAccounts.keys();

        while (enumer.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }

            Integer tempKey = enumer.nextElement();

            if (customerAccounts.containsKey(tempKey)) {
                System.out.println(customerAccounts.get(tempKey).toString());
            } else if (!customerAccounts.containsKey(tempKey) && !customerAccounts.isEmpty())
                System.out.println("Wrong key.");

            else
                System.out.println("Something else happened.");
            tempCount++;
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Finished printing accounts.");
    }

    /*public void writeCustomerAndAccountInformationToFile(String fileName) {

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
    }*/

    public void printAllCustomerInformation() {

        Enumeration<Integer> enumKeys = realBank.getCustomerTable().keys();

        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getCustomerHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }
            System.out.println(realBank.getCustomerTable().get(enumKeys.nextElement()).toString());
            tempCount++;
        }

    }

    public void printAllAccountInformation() {

        Enumeration<Integer> enumKeys = realBank.getAccountHashTable().keys();

        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }
            System.out.println(realBank.getAccountHashTable().get(enumKeys.nextElement()).toString());
            tempCount++;
        }

    }

    /*public void writeCustomerInformationToFile(String fileName) {

        if (!fileName.equalsIgnoreCase("DEFAULT"))
            writer = getPW(fileName);

        Enumeration<Integer> enumKeys = customerHashTable.keys();

        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getCustomerHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }
            writer.println(customerHashTable.get(enumKeys.nextElement()).toString());
            tempCount++;
        }
        writer.close();
        System.out.println("Finished writing customers to file.");

    }*/

    /*public void writeAccountInformationToFile(String fileName) {

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
    }*/

    public void saveAllBankDataToFile(RealBank newRealBank) {

        try {

            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/bankData.txt");
            ObjectOutputStream bankDataWriter = new ObjectOutputStream(fos);

            bankDataWriter.writeObject(newRealBank);


            fos.close();
            bankDataWriter.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void readAllBankDataFromFile() {

        try {

            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/bankData.txt");
            ObjectInputStream bankDataReader = new ObjectInputStream(fis);

            //while (bankDataReader.available() > 0) {
            this.realBank = (RealBank) bankDataReader.readObject();
            this.customerHashTable = realBank.getCustomerTable();
            this.accountHashTable = realBank.getAccountHashTable();
            //}


            /*bankDataReader.close();
            fis.close();*/

        } catch (EOFException p) {
            p.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException k){
            k.printStackTrace();
            System.exit(1);
        }

    }

    public RealBank getRealBank(){
        return realBank;
    }

    public void setRealBank(RealBank newRealBank) {
        realBank = newRealBank;
    }


    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-7s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }

    String getCustomerHeaders() {
        return String.format("||%-36s||%-20s||%-20s||%-3s||%-4s||%-4s||", "CUSTOMER ID", "NAME", "PASSWORD", "AGE", "CRED", "CHEX");
    }

}
