package bank_interface;


import bank_package.RealBank;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

/*DATA Input/Output class for storing all the write -to-file methods and headers, etc.*/
public class DataIO {
    String hiding = "ishallpass";
    private RealBank realBank;

    public DataIO() {
    }

    void printAllCustomerPrivateInformation(Integer customerHashKey, Hashtable customerAccounts) {
        try {


            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(getCustomerHeaders());
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(this.getRealBank().getCustomerTable().get(customerHashKey).toString());

            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            printAccountInformation(customerAccounts);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println("Finished printing customer information...");
        } catch (NullPointerException p) {
            System.out.println("Null Pointer caught in DataIO : printAllCustomerPrivateInformation");
            System.exit(1);
        }


    }

    void printAccountInformation(Hashtable customerAccounts) {
        try {
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
        } catch (NullPointerException p) {
            System.out.println("Null Pointer caught in DataIO : printAccountInformation");
            System.exit(1);
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

        Enumeration<Integer> enumKeys = getRealBank().getCustomerTable().keys();

        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getCustomerHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }
            System.out.println(getRealBank().getCustomerTable().get(enumKeys.nextElement()).toString());
            tempCount++;
        }

    }

    public void printAllAccountInformation() {

        Enumeration<Integer> enumKeys = getRealBank().getAccountHashTable().keys();

        int tempCount = 10;
        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }
            System.out.println(getRealBank().getAccountHashTable().get(enumKeys.nextElement()).toString());
            tempCount++;
        }

    }

    public void writeCustomerInformationToFile() {
        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/bankData.txt");
            Enumeration<Integer> enumKeys = this.getRealBank().getCustomerTable().keys();

            int tempCount = 10;
            while (enumKeys.hasMoreElements()) {
                if (tempCount == 10) {
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println(getCustomerHeaders());
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    tempCount = 0;
        }
                writer.println(this.getRealBank().getCustomerTable().get(enumKeys.nextElement()).toString());
                tempCount++;
            }
            writer.close();

        } catch (FileNotFoundException f) {
            System.out.println("Must create file first.");
            System.exit(1);
        }
        System.out.println("Finished writing customers to file.");

    }

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


    /*@func realAllBankDataFromFile():
    *
    * reads bank information from a file called "bankData.txt" located in the project directory (Bank_App) using an object
    * input stream. sets current RealBank object to the new type case object read from the file.
    *
    * @param null
    *
    * @return void*/
    public void readAllBankDataFromFile() {

        try {

            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/bankData.txt");
            ObjectInputStream bankDataReader = new ObjectInputStream(fis);

            this.realBank = (RealBank) bankDataReader.readObject();

            /*bankDataReader.close();
            fis.close();

            **** DO I NEED TO CLOSE THE INPUT STREAMS? SO FAR, NO PROBLEMS*/

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

    /*@func getRealBank():
    *
    * Used to access current bank information, and the bank data most recently loaded from file.
    *
    * @param:
    *
    * @return current RealBank object or a temporary if current RealBank is null.*/
    public RealBank getRealBank(){
        /*checks to make sure the current realBank object is not null before passing it on to the invoker*/
        if (!(this.realBank == null)) {
            return realBank;
        } else {/*if realBank IS null, a temporary instantiated realBank is returned and the invoker is notified
        this is implemented to expose and catch bugs*/
            System.out.println("realBank == null, returning temporary bank.");
            return new RealBank("temp", 1, 1);
        }

    }

    /*@func setRealBank:
    *
    * sets the current RealBank object to the new instance. used when the bank has been updated and is no longer current*/
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
