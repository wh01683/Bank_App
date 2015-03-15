package bank_package;

import java.io.Serializable;
import java.util.Random;

public class CreditReport implements Serializable {
    private final int CREDIT_SCORE;
    private final int RECENT_LATE_PAYMENT_NUMBER;
    private final int CUSTOMER_AGE;
    private final int RECENT_CREDIT_INQUIRIES;
    private final int LENGTH_OF_CREDIT_HISTORY;
    private final double CREDIT_LIMIT;
    private final double CREDIT_USED;
    private final double CREDIT_ACCOUNT_BALANCE;
    private final double AMOUNT_OF_LATE_PAYMENTS;

    private final Random r = new Random();


    public CreditReport(int age, int latePaymentsOnRecord, double amountOfLatePayments, int recentCredInquiries,
                        double credLimit, double accountBalance, int lenCredHistory) {

        this.CUSTOMER_AGE = age;
        this.RECENT_LATE_PAYMENT_NUMBER = latePaymentsOnRecord;
        this.AMOUNT_OF_LATE_PAYMENTS = amountOfLatePayments;
        this.RECENT_CREDIT_INQUIRIES = recentCredInquiries;
        this.CREDIT_LIMIT = credLimit;
        this.CREDIT_ACCOUNT_BALANCE = accountBalance;
        this.LENGTH_OF_CREDIT_HISTORY = lenCredHistory;
        this.CREDIT_SCORE = calculateCreditScore();
        this.CREDIT_USED = (this.CREDIT_ACCOUNT_BALANCE / this.CREDIT_LIMIT) * 100;
    }

    public CreditReport() {
        this.CUSTOMER_AGE = r.nextInt(120);
        this.RECENT_LATE_PAYMENT_NUMBER = r.nextInt(100);
        this.AMOUNT_OF_LATE_PAYMENTS = r.nextDouble() * 2000000000.0;
        this.RECENT_CREDIT_INQUIRIES = r.nextInt(100);
        this.CREDIT_LIMIT = r.nextDouble() * 2000000000.0;
        this.CREDIT_ACCOUNT_BALANCE = r.nextDouble() * 2000000000.0;
        this.CREDIT_USED = (this.CREDIT_ACCOUNT_BALANCE / this.CREDIT_LIMIT) * 100;
        this.LENGTH_OF_CREDIT_HISTORY = r.nextInt(100);
        this.CREDIT_SCORE = calculateCreditScore();

    }

    public CreditReport(int age) {
    /*for customers of age lesser than 18 and greater than 0*/
        this.CUSTOMER_AGE = age;
        this.RECENT_LATE_PAYMENT_NUMBER = 0;
        this.RECENT_CREDIT_INQUIRIES = 0;
        this.CREDIT_LIMIT = 0;
        this.CREDIT_ACCOUNT_BALANCE = 0;
        this.LENGTH_OF_CREDIT_HISTORY = 0;
        this.CREDIT_SCORE = calculateCreditScore();
        this.CREDIT_USED = (this.CREDIT_LIMIT / this.CREDIT_ACCOUNT_BALANCE) * 100;
        this.AMOUNT_OF_LATE_PAYMENTS = 0;
    }

    public int getCreditScore() {
        return this.CREDIT_SCORE;
    }

    private int calculateCreditScore() {
        int tempScore = 100; //base score
        tempScore += 79; /*I automatically add 79 because I do not take into account variables comprising
        10% of the credit score: types of credit in use. Realistically, a users credit score would improve if they
        are using a diverse amount of payment options which require a credit score. For instance, all other things being
        equal, a user would have a HIGHER credit score if they had 2 credit cards, a car payment, and a mortgage THAN
        a user with just 4 credit cards.*/

        tempScore += calcPaymentHistoryScore() + calcAmtOwedScore() + calcLenHistoryScore() + calcNewCreditScore();
        return tempScore;
    }

    public int getCUSTOMER_AGE() {
        return this.CUSTOMER_AGE;
    }
    private int calcNewCreditScore() {
        if (this.RECENT_CREDIT_INQUIRIES < 8)
            return 80 - (this.RECENT_CREDIT_INQUIRIES * 10);
        else return 0;
    }

    private int calcPaymentHistoryScore() {
        //min 0, max 279 (35% of credit score)
        double severity;
        int tempScore;
        switch (this.RECENT_LATE_PAYMENT_NUMBER) {
            case (0):
                severity = 0;
                break;
            case (1):
                severity = 1;
                break;
            case (3):
            case (2):
                severity = 2.5;
                break;
            case (5):
            case (4):
                severity = 5;
                break;
            default:
                severity = 10;
        }

        double latePaymentMultiplier = severity * this.AMOUNT_OF_LATE_PAYMENTS;

        if (latePaymentMultiplier == 0 | latePaymentMultiplier < 1000) tempScore = 279;
        if (latePaymentMultiplier < 3000) tempScore = 200;
        if (latePaymentMultiplier < 5000) tempScore = 100;
        else tempScore = 0;

        return tempScore;

    }

    private int calcAmtOwedScore() {
        //max is 239.7
        int tempScore;

        if (this.CREDIT_ACCOUNT_BALANCE == 0 | this.CREDIT_USED < 10) tempScore = 239;
        if (this.CREDIT_USED < 20) tempScore = 200;
        if (this.CREDIT_USED < 50) tempScore = 100;
        else tempScore = 0;

        return tempScore;
    }

    private int calcLenHistoryScore() {
        //max is 120
        int tempScore = 20;

        tempScore += this.CUSTOMER_AGE - this.LENGTH_OF_CREDIT_HISTORY;

        if (tempScore > 120) tempScore = 120;

        return tempScore;
    }

}
