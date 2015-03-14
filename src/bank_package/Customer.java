package bank_package;

import acct.Account;
import acct.AccountFactory;

import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;


public class Customer {

    private final UUID CUSTOMER_ID;
    private final String NAME;
    private final String PASSWORD;
    private final Random r = new Random();
    private final CreditReport _cred;
    private final ChexSystems _score;
    private final int age;
    private final int ChexSystemsScore; //intialize to 0 indicating no prior history
    private Hashtable<Integer, Account> accountHashtable = new Hashtable<Integer, Account>(400);
    private AccountFactory testAccountFactory = new AccountFactory();
    private RandomGenerator random = new RandomGenerator();

    public Customer(String tempName, int tempAge, String password, CreditReport newCreditReport, ChexSystems newScore) {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();

        this.age = tempAge;
        this.NAME = tempName;
        this._cred = newCreditReport;
        this._score = newScore;
        this.PASSWORD = password;
        this.ChexSystemsScore = _score.getScore();

    }

    public Customer(boolean random) {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();
        this.age = r.nextInt(100);
        this.NAME = this.random.nameGen(2, r.nextInt(20));
        this._cred = new CreditReport(this.age, true);
        this._score = new ChexSystems();
        this.PASSWORD = this.random.passwordGen(0, r.nextInt(15) + 5);
        this.ChexSystemsScore = _score.getScore();


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


    /*public void printAllCustomerInformation() {
        System.out.println("\n ID: " + this.CUSTOMER_ID + " Name: " + this.NAME + " Pass: " + this.PASSWORD + " Age: " + this.getAge() + " Cred: " + this.getCreditScore()
                + " Chex: " + this.getChexSystemsScore() + " ");
        this.printAccountInformation();

    }*/

    public void addAccount(Account newAccount) {
        if (!(newAccount == null))
            this.accountHashtable.put(newAccount.getACCOUNT_NUMBER(), newAccount);

    }

    /*void printAccountInformation() {

        Enumeration<Integer> enumKeys = accountHashtable.keys();

        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Account temp = accountHashtable.get(key);
            System.out.println(" Type: " + temp.getType() + " acct #: " + key + " balance: " + temp.getBalance() + " ");
        }

    }*/

    @Override
    public String toString() {
        String temp = String.format("%-36s %-20s %-20s %-3d %-4d %-4d", this.CUSTOMER_ID, this.NAME,
                this.PASSWORD, this.age, this.getCreditScore(), this.getChexSystemsScore());

        return temp;
    }

}