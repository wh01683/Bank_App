package bank_package;
import java.util.Random;
import java.util.UUID;


public class Customer {
    private final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    final int NUM_IN_HEX = HEX_ALPHABET.length();
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


    public Customer() {
        this.CUSTOMER_ID = new UUID(64, 64);
        this.age = ageS.intGet();
        this.name = nameS.stringGet();
        if (this.age > 17)
            this._cred = fillCredReport(this.age);
        this._score = new ChexSystems();

    }

    public Customer(String tempName, int tempAge) {
        this.CUSTOMER_ID = new UUID(64, 64);
        this.age = tempAge;
        this.name = tempName;
        if (this.age > 17)
            this._cred = fillCredReport(this.age);
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
        double accountBalance = credBalance.doubleGet();
        int lenCredHistory = credHistory.intGet();
        int latePaymentsOnRecord = latePayments.intGet();
        int recentCredInquiries = credInquiries.intGet();

        return new CreditReport(age, latePaymentsOnRecord, recentCredInquiries, credLimit,
                accountBalance, lenCredHistory);
    }


}
