package bank_package;

import acct.Account;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;


public class Customer implements Serializable {

    private final UUID CUSTOMER_ID;
    private final String NAME;
    private final String PASSWORD;
    private final CreditReport CREDIT_REPORT;
    private final ChexSystems CHEX_SCORE = new ChexSystems();
    private final int age;
    private Hashtable<Integer, Account> accountHashtable = new Hashtable<Integer, Account>(400);

    public Customer(String tempName, int tempAge, String password, CreditReport newCreditReport) {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();

        this.age = tempAge;
        this.NAME = tempName;
        this.CREDIT_REPORT = newCreditReport;
        this.PASSWORD = password;

    }

    public Customer() {
        this.CUSTOMER_ID = new UUID(16, 16).randomUUID();
        RandomGenerator random = new RandomGenerator();
        Random r = new Random();
        this.NAME = random.nameGen(2, r.nextInt(20));
        this.CREDIT_REPORT = new CreditReport();
        this.age = CREDIT_REPORT.getCUSTOMER_AGE();
        this.PASSWORD = random.passwordGen(0, r.nextInt(15) + 5);


    }

    public Hashtable<Integer, Account> getAccountHashtable() {
        return this.accountHashtable;
    }

    public void setAccountHashtable(Hashtable<Integer, Account> newAccountHashTable) {
        this.accountHashtable = newAccountHashTable;
    }

    public String getPASSWORD() {
        return this.PASSWORD;
    }

    public UUID getUUID() {
        return this.CUSTOMER_ID;
    }

    public String getName() {
        return this.NAME;
    }

    public int getChexSystemsScore() {
        return this.CHEX_SCORE.getScore();
    }

    public int getCreditScore() {
        return this.CREDIT_REPORT.getCreditScore();
    }

    public void addAccount(Account newAccount) {
        if (!(newAccount == null))
            this.accountHashtable.put(newAccount.getACCOUNT_NUMBER(), newAccount);

    }

    @Override
    public String toString() {
        return String.format("||%-36s||%-20s||%-20s||%-3d||%-4d||%-4d||", this.CUSTOMER_ID, this.NAME,
                this.PASSWORD, this.age, this.getCreditScore(), this.getChexSystemsScore());
    }

    public Account getAccount(Integer accountNumber) {
        final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", 0, 200000000);
        if (this.accountHashtable.containsKey(accountNumber))
            return this.accountHashtable.get(accountNumber);
        else{
            System.out.println("Could not locate your account. Please re-enter your account number.");
            return getAccount(ACCOUNT_NUMBER_SCANNER.intGet());
        }
    }

}
