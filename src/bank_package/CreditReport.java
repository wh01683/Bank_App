package bank_package;

import java.util.*;

class CreditReport {
    static Scanner in = new Scanner(System.in);
    private final int CREDIT_SCORE;
    private final int RECENT_LATE_PAYMENT_NUMBER;
    private final int CUSTOMER_AGE;
    private final int RECENT_CREDIT_INQUIRIES;
    private final int LENGTH_OF_CREDIT_HISTORY;
    private final double CREDIT_LIMIT;
    private final double CREDIT_USED;
    private final double CREDIT_ACCOUNT_BALANCE;
    private double latePaymentAmounts;

    public CreditReport(int age, int latePaymentsOnRecord, int recentCredInquiries, double credLimit,
                        double accountBalance, int lenCredHistory) {

        this.CUSTOMER_AGE = age;
        this.RECENT_LATE_PAYMENT_NUMBER = latePaymentsOnRecord;
        uScanner latePay = new uScanner("You indicated you have " + this.RECENT_LATE_PAYMENT_NUMBER + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0.0, 2000000000.0);
        this.RECENT_CREDIT_INQUIRIES = recentCredInquiries;
        this.CREDIT_LIMIT = credLimit;
        this.CREDIT_ACCOUNT_BALANCE = accountBalance;
        this.LENGTH_OF_CREDIT_HISTORY = lenCredHistory;
        this.CREDIT_SCORE = calculateCreditScore();
        this.CREDIT_USED = (this.CREDIT_LIMIT / this.CREDIT_ACCOUNT_BALANCE) * 100;

		/*if (this.RECENT_LATE_PAYMENT_NUMBER > 0) this.RECENT_LATE_PAYMENT_AMOUNT = latePay.doubleGet();
        else this.RECENT_LATE_PAYMENT_AMOUNT = 0;*/

    }

    public CreditReport(int age) {

        this.CUSTOMER_AGE = age;
        this.RECENT_LATE_PAYMENT_NUMBER = 0;
        this.RECENT_CREDIT_INQUIRIES = 0;
        this.CREDIT_LIMIT = 0;
        this.CREDIT_ACCOUNT_BALANCE = 0;
        this.LENGTH_OF_CREDIT_HISTORY = 0;
        this.CREDIT_SCORE = calculateCreditScore();
        this.CREDIT_USED = (this.CREDIT_LIMIT / this.CREDIT_ACCOUNT_BALANCE) * 100;
        this.latePaymentAmounts = 0;
    }

    public void setLatePaymentAmount(double newPayments) {
        this.latePaymentAmounts = newPayments;
    }

    public int getCreditScore() {
        return this.CREDIT_SCORE;
    }

    private int calculateCreditScore() {
        int tempScore = 100; //base score
        tempScore += 159; //I automatically add 159 because I do not take into account variables comprising
        //20% of the credit score: types of credit in use and new credit.
        tempScore += calcPaymentHistoryScore() + calcAmtOwedScore() + calcLenHistoryScore();

        return tempScore;

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

        double latePaymentMult = severity * this.latePaymentAmounts;

        if (latePaymentMult == 0 || latePaymentMult < 1000) tempScore = 279;
        if (latePaymentMult < 3000) tempScore = 200;
        if (latePaymentMult < 5000) tempScore = 100;
        else tempScore = 0;

        return tempScore;

    }

    private int calcAmtOwedScore() {
        //max is 239.7
        int tempScore;

        if (this.CREDIT_ACCOUNT_BALANCE == 0 || this.CREDIT_USED < 10) tempScore = 239;
        if (this.CREDIT_USED < 20) tempScore = 200;
        if (this.CREDIT_USED < 50) tempScore = 100;
        else tempScore = 0;

        return tempScore;
    }

    private int calcLenHistoryScore() {
        //max is 120
        int tempScore = 20;

        tempScore += this.CUSTOMER_AGE;

        if (tempScore > 120) tempScore = 120;

        return tempScore;
    }

}
