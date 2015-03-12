package bank_package;
import java.util.Random;
import java.util.UUID;


public class Customer {

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    final int NUM_IN_HEX = HEX_ALPHABET.length();
    private final int NUM_IN_ALPHABET = ALPHABET.length();
    private final UUID CUSTOMER_ID;
    private final String name;
    Random r = new Random();
    private uScanner nameS = new uScanner("Please enter your name: ", 2, 50);
    private uScanner ageS = new uScanner("Please enter your age: ", 14, 99);
    private uScanner latePayments = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private uScanner credInquiries = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private uScanner credBalance = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000.0);
    private uScanner credHistory = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private uScanner credLim = new uScanner("Please enter your total credit limit.", -1.0, 2000000000.0);
    private CreditReport _cred;
    private ChexSystems _score;

    private int age;
    private int ChexSystemsScore = 0; //intialize to 0 indicating no prior history


    public Customer(String tempName, int tempAge) {
        this.CUSTOMER_ID = new UUID(16, 32).randomUUID();
        this.age = tempAge;
        this.name = tempName;
        if (this.age > 17)
            this._cred = new CreditReport(this.age);
        this._cred = _cred.makeRandomCreditReport();
        this._score = new ChexSystems();

    }

    public Customer(boolean random) {
        this.CUSTOMER_ID = new UUID(16, 32).randomUUID();
        this.age = r.nextInt(100);
        this.name = nameGen(2, 50);
        this._cred = new CreditReport(this.age, true);
        this._score = new ChexSystems();

    }

    public UUID getUUID() {
        return this.CUSTOMER_ID;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public int getChexSystemsScore() {
        return this._score.getScore();
    }

    public int getCreditScore() {
        return this._cred.getCreditScore();
    }


    private CreditReport fillCredReport(int tempAge) {

        System.out.println("Since you are " + tempAge + " years old, you must provide some credit information.");
        double credLimit = credLim.doubleGet();
        double amountOfLatePayments = 0;
        double accountBalance = credBalance.doubleGet();
        int lenCredHistory = credHistory.intGet();
        int latePaymentsOnRecord = latePayments.intGet();
        if (latePaymentsOnRecord > 0)
            amountOfLatePayments = getLatePaymentAmounts(latePaymentsOnRecord);
        int recentCredInquiries = credInquiries.intGet();

        return new CreditReport(age, latePaymentsOnRecord, amountOfLatePayments, recentCredInquiries, credLimit,
                accountBalance, lenCredHistory);
    }

    private String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }


    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0.0, 2000000000.0);
        return latePay.doubleGet();
    }
}
