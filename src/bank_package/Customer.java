package bank_package;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;


class Customer {

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    final int NUM_IN_HEX = HEX_ALPHABET.length();
    private final String ALPHA_NUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private final int LENGTH_OF_ALPHA_NUMERIC = ALPHA_NUMERIC.length();
    private final int NUM_IN_ALPHABET = ALPHABET.length();
    //ABOVE FIELDS ARE USED FOR CREATING RANDOM CUSTOMERS ONLY

    private final UUID CUSTOMER_ID;
    private final String NAME;
    private final String PASSWORD;
    Random r = new Random();
    private CreditReport _cred;
    private ChexSystems _score;
    private Hashtable<Integer, Account> accountHashtable = new Hashtable<Integer, Account>(400);
    private int age;
    private int ChexSystemsScore; //intialize to 0 indicating no prior history

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
        this.NAME = nameGen(2, 50);
        this._cred = new CreditReport(this.age, true);
        this._score = new ChexSystems();
        this.PASSWORD = passwordGen(5, 15);
        this.ChexSystemsScore = _score.getScore();
        this.addCertificateOfDeposit(r.nextDouble() * 50000);
        this.addCheckingAccount(r.nextDouble() * 50000);
        this.addIndividualRetirementAccount(r.nextDouble() * 500000);
        this.addSavingsAccount(r.nextDouble() * 20000);

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

    public void addSavingsAccount(double openingBalance) {
        SavingsAccountApplication temp = new SavingsAccountApplication(this, openingBalance);
        if (temp.screeningResult()) {
            this.accountHashtable.put(acctGen(), new SavingsAccount(openingBalance, this));
        }
    }

    public void addCertificateOfDeposit(double openingBalance) {
        CertificateOfDepositApplication temp = new CertificateOfDepositApplication(this, openingBalance);
        if (temp.screeningResult()) {
            this.accountHashtable.put(acctGen(), new CertificateOfDepositAccount(openingBalance, r.nextInt(500), this));
        }
    }

    public void addCheckingAccount(double openingBalance) {
        CheckingAccountApplication temp = new CheckingAccountApplication(this, openingBalance);
        if (temp.screeningResult()) {
            this.accountHashtable.put(acctGen(), new CheckingAccount(openingBalance, (r.nextInt(1000) * -1), this));
        }
    }

    public void addIndividualRetirementAccount(double openingBalance) {
        IndividualRetirementAccountApplication temp = new IndividualRetirementAccountApplication(this, openingBalance);
        if (temp.screeningResult()) {
            this.accountHashtable.put(acctGen(), new IndividualRetirementAccount(openingBalance, this));
        }
    }

    public int getChexSystemsScore() {
        return this._score.getScore();
    }

    public int getCreditScore() {
        return this._cred.getCreditScore();
    }

    private String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }

    private String passwordGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i < maxLength; i++) {
            temp += ALPHA_NUMERIC.charAt(r.nextInt(LENGTH_OF_ALPHA_NUMERIC));
        }
        return temp;
    }

    private int acctGen() {
        int temp = 100000000;

        return temp += r.nextInt(99999999);
    }

    public void printAllCustomerInformation() {
        System.out.println("\n ID: " + this.CUSTOMER_ID + " Pass: " + this.PASSWORD + " Name: " + this.NAME + " Age: " + this.getAge() + " Cred: " + this.getCreditScore()
                + " Chex: " + this.getChexSystemsScore() + " ");
        this.printAccountInformation();

    }

    public void printAccountInformation() {

        Enumeration<Integer> enumKeys = accountHashtable.keys();

        while (enumKeys.hasMoreElements()) {
            Integer key = enumKeys.nextElement();
            Account temp = accountHashtable.get(key);
            System.out.println(" Type: " + temp.getType() + " acct #: " + key + " balance: " + temp.getBalance() + " ");
        }

    }

}