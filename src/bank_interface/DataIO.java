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

    /**printAllCustomerPrivateInformation() prints all information concerning the customer including all information
     *                                      they have on file and all accounts they own. this method is invoked if the
     *                                      user requests "all" from the information menu
    *
    * @param customerHashKey: key of the customer requesting the information. the key is used to look up the information
    * of the customer requesting it
    *
    * @param customerAccounts: HashTable of the accounts owned by the customer.
    *
     */
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

    /** printAccountInformation prints passed accounts from a hash table. these will almost always be a hashtable
     *                                from one customer to provide personal information regarding all accounts they own
    *
    * @param customerAccounts: HashTable to be printed
     */
    void printAccountInformation(Hashtable customerAccounts) {
        try {
            int tempCount = 10;
            Enumeration customerAccountEnumeration = customerAccounts.keys();

            while (customerAccountEnumeration.hasMoreElements()) {
                if (tempCount == 10) {
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println(getAccountHeaders());
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    tempCount = 0;
                }

                Integer tempKey = (Integer) customerAccountEnumeration.nextElement();

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

    /**
     * writeCustomerAndAccountInformationToFile writes BOTH the customer and the customer's account formation to a file
     * using nested hashtable enumerations. when a customer is printed, the
     * method will print all of the accounts associated with that particular
     * customer when the method has printed all the accounts associated with that
     * customer, we will move on to the next customer
     */

    public void writeCustomerAndAccountInformationToFile() {

        try {
            PrintWriter writer = new PrintWriter(String.format("%s/writeBankData.txt", System.getProperty("user.dir")));

            Enumeration enumKeys = getRealBank().getCustomerTable().keys();
            while (enumKeys.hasMoreElements()) {
                Integer key = (Integer) enumKeys.nextElement();
                writer.println(getRealBank().getCustomerTable().get(key).toString());
                Enumeration acctKeys = getRealBank().getAccountHashTable().keys();
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                writer.println(getCustomerHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                writer.println(getAccountHeaders());

                while (acctKeys.hasMoreElements()) {
                    Integer acctKey = (Integer) acctKeys.nextElement();
                    writer.println(getRealBank().getAccountHashTable().get(acctKey).toString());
        }
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                writer.println(getCustomerHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            }
            writer.close();

        } catch (FileNotFoundException f) {
            System.out.println("bankData.txt not found in current directory.");
            System.exit(1);
        }
        System.out.println("Finished writing to file.");
    }

    /**
     *  printAllCustomerInformation prints all customer information to the console.
    */

    public void printAllCustomerInformation() {

        Enumeration enumKeys = getRealBank().getCustomerTable().keys();

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

    /**
     * printAllAccountInformation() prints all information concerning ACCOUNTS ONLY to the console. will contain
     *                              information related to the user associated with the account, but will only print out accounts on file.
     * */
    public void printAllAccountInformation() {

        try {
            Enumeration enumKeys = getRealBank().getAccountHashTable().keys();

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
        } catch (NullPointerException n) {
            System.out.println("Null pointer thrown in DataIO : printAllAccountInformation");
            System.exit(1);
        }
    }

    /**
     *  writeCustomerInformationToFile() writes the current RealBank object's CUSTOMER information to a file. does not print account information
     */
    public void writeCustomerInformationToFile() {
        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/writeBankData.txt");
            Enumeration enumKeys = this.getRealBank().getCustomerTable().keys();

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
            System.out.println("writeBankData.txt not found in current directory.");
            System.exit(1);
        }
        System.out.println("Finished writing customers to file.");

    }

    /**
     * writeAccountInformationToFile() writes the current RealBank object's ACCOUNT information to a txt file in a format legible by humans
     * */
    public void writeAccountInformationToFile() {

        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/writeBankData.txt");

            Enumeration enumKeys = getRealBank().getAccountHashTable().keys();
            int tempCount = 10;

            while (enumKeys.hasMoreElements()) {
                if (tempCount == 10) {
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    writer.println(getAccountHeaders());
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    tempCount = 0;
        }

                writer.println(getRealBank().getAccountHashTable().get(enumKeys.nextElement()).toString());
                tempCount++;
            }
            writer.close();
        } catch (FileNotFoundException f) {
            System.out.printf("writeBankData.txt not found in current directory.");
            System.exit(1);
        }
        System.out.println("Finished writing accounts to file.");
    }


    /**saveAllBankDataToFile saves the current RealBank object to a file named bankData.txt in the user's current
     *                       directory using an object output stream. the File output stream and object output stream
     *                       are closed when they are done.
     *
     * @param newRealBank to be saved, useful for passing and saving the most current bank
     */
    public void saveAllBankDataToFile(RealBank newRealBank) {

        try {

            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/bankData.txt");
            ObjectOutputStream bankDataWriter = new ObjectOutputStream(fos);

            bankDataWriter.writeObject(newRealBank);


            fos.close();
            bankDataWriter.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        } catch (NullPointerException n) {
            System.out.printf("Null Pointer caught in DataIO: saveAllBankDataToFile");
            System.exit(1);
        }
    }


    /**realAllBankDataFromFile() reads bank information from a file called "bankData.txt" located in the project directory
    *                            (Bank_App) using an object input stream. sets current RealBank object to the new type
     *                            case object read from the file.
     */
    public void readAllBankDataFromFile() {

        try {

            File newFile = new File(System.getProperty("user.dir") + "/bankData.txt");

            if(!newFile.createNewFile()){
            }

            FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/bankData.txt");
            ObjectInputStream bankDataReader = new ObjectInputStream(fis);

            this.realBank = (RealBank) bankDataReader.readObject();


            /*bankDataReader.close();
            fis.close();

            **** DO I NEED TO CLOSE THE INPUT STREAMS? SO FAR, NO PROBLEMS*/

        }catch(FileNotFoundException f){
            System.out.printf("File not found in DataIO : readAllBankDataFromFile");
            System.exit(1);
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

    /**getRealBank() Used to access current bank information, and the bank data most recently loaded from file.
    *
    * @return current RealBank object or a temporary if current RealBank is null.*/
    public RealBank getRealBank() {
        /*checks to make sure the current realBank object is not null before passing it on to the invoker*/
        if (!(this.realBank == null)) {
            return realBank;
        } else {/*if realBank IS null, a temporary instantiated realBank is returned and the invoker is notified
        this is implemented to expose and catch bugs*/
            System.out.println("realBank == null, returning temporary bank.");
            return new RealBank("temp", 1, 1);
        }

    }

    /** setRealBank sets the current RealBank object to the new instance. used when the bank has been updated and
    *               is no longer current
     * @param newRealBank most current realBank object to be used for information printing and retrieval
     */
    public void setRealBank(RealBank newRealBank) {
        realBank = newRealBank;
    }


    /**
     * getAccountHeaders stores headers used when printing account information to the console.
     *
     * @return returns string representation of the headers*/
    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-7s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }
/**
 * getCustomerHeaders stores headers used when printing customer information to the console
 *
 * @return string representation of the headers*/
    String getCustomerHeaders() {
        return String.format("||%-36s||%-20s||%-20s||%-3s||%-4s||%-4s||", "CUSTOMER ID", "NAME", "PASSWORD", "AGE", "CRED", "CHEX");
    }

}
