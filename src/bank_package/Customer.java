package bank_package;
import java.util.Random;
import java.util.UUID;


public class Customer {

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
    private boolean EligibleForCD;
    private boolean EligibleForIRA;
    private boolean EligibleForChecking;
    private boolean EligibleForSavings;
    private boolean EligibleForMMA;
    private int age;
    private int ChexSystemsScore = 0; //intialize to 0 indicating no prior history

    public Customer(String tempName, int tempAge, String password, CreditReport newCreditReport, ChexSystems newScore) {
        this.CUSTOMER_ID = new UUID(16, 32).randomUUID();
        this.age = tempAge;
        this.NAME = tempName;
        this._cred = newCreditReport;
        this._score = newScore;
        this.PASSWORD = password;

    }

    public Customer(boolean random) {
        this.CUSTOMER_ID = new UUID(16, 32).randomUUID();
        this.age = r.nextInt(100);
        this.NAME = nameGen(2, 50);
        this._cred = new CreditReport(this.age, true);
        this._score = new ChexSystems();
        this.PASSWORD = passwordGen(5, 15);

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

}
