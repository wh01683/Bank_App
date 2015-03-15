package bank_package;

import acct.Account;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;


public class Customer implements Serializable {

    private final UUID CUSTOMER_ID;
    private final String NAME;
    private final String PASSWORD;
    private final Random r = new Random();
    private final CreditReport _cred;
    private final ChexSystems _score = new ChexSystems();
    private final int age;
    private final uScanner REQUEST_SCANNER = new uScanner("\nWhat would you like to know more about?. \nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final Hashtable<Integer, Account> accountHashtable = new Hashtable<Integer, Account>(400);
    private final RandomGenerator random = new RandomGenerator();

    public Customer(String tempName, int tempAge, String password, CreditReport newCreditReport, ChexSystems newScore) {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();

        this.age = tempAge;
        this.NAME = tempName;
        this._cred = newCreditReport;
        this.PASSWORD = password;

    }

    public Customer() {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();
        this.NAME = this.random.nameGen(2, r.nextInt(20));
        this._cred = new CreditReport();
        this.age = _cred.getCUSTOMER_AGE();
        this.PASSWORD = this.random.passwordGen(0, r.nextInt(15) + 5);


    }

    public Hashtable<Integer, Account> getAccountHashtable() {
        return this.accountHashtable;
    }
    public String getPASSWORD() {
        return this.PASSWORD;
    }

    public UUID getUUID() {
        return this.CUSTOMER_ID;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.NAME;
    }

    public int getChexSystemsScore() {
        return this._score.getScore();
    }

    public int getCreditScore() {
        return this._cred.getCreditScore();
    }


    void printAllCustomerInformation() {


        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(getCustomerHeaders());
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(this.toString());
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        this.printAccountInformation();
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Finished printing customer information...");
        printInformation(REQUEST_SCANNER.stringGet());

    }



    public void addAccount(Account newAccount) {
        if (!(newAccount == null))
            this.accountHashtable.put(newAccount.getACCOUNT_NUMBER(), newAccount);

    }

    void printAccountInformation() {

        Enumeration<Integer> enumKeys = accountHashtable.keys();
        int tempCount = 10;

        while (enumKeys.hasMoreElements()) {
            if (tempCount == 10) {
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                tempCount = 0;
            }

            Integer key = enumKeys.nextElement();
            Account temp = this.accountHashtable.get(key);
            System.out.println(temp.toString());
            tempCount++;
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Finished printing accounts.");
    }
    public void printInformation(String request) {

        if (request.equalsIgnoreCase("CHEX"))
            System.out.println("Your ChexSystems score is currently " + this.getChexSystemsScore() + ".");
        else if (request.equalsIgnoreCase("CREDIT"))
            System.out.println("Your Credit Score is currently " + this.getCreditScore() + ".");
        else if (request.equalsIgnoreCase("ACCOUNTS"))
            this.printAccountInformation();
        else if (request.equalsIgnoreCase("ALL"))
            this.printAllCustomerInformation();
        else if (request.equalsIgnoreCase("RETURN"))
            System.out.println("Returning to previous menu.");
        else {
            System.out.println("Could not process your request: " + request + "Please try again");
            printInformation(REQUEST_SCANNER.stringGet());
        }
    }

    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }

    String getCustomerHeaders() {
        return String.format("||%-36s||%-20s||%-20s||%-3s||%-4s||%-4s||", "CUSTOMER ID", "NAME", "PASSWORD", "AGE", "CRED", "CHEX");
    }

    @Override
    public String toString() {
        return String.format("||%-36s||%-20s||%-20s||%-3d||%-4d||%-4d||", this.CUSTOMER_ID, this.NAME,
                this.PASSWORD, this.age, this.getCreditScore(), this.getChexSystemsScore());
    }

    public Account getAccount(Integer accountNumber) {
        if (this.accountHashtable.containsKey(accountNumber))
            return this.accountHashtable.get(accountNumber);
        else
            return null;
    }

}
