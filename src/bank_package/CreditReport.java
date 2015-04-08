package bank_package;

import java.io.Serializable;

public class CreditReport implements Serializable {
    private int creditScore;
    private int recentLatePaymentNumber;
    private int customerAge;
    private int recentCreditInquiries;
    private int lengthOfCreditHistory;
    private double creditLimit;
    private double creditUsed;
    private double creditAccountBalance;
    private double amountOfLatePayments;


    public CreditReport(int age, int latePaymentsOnRecord, double amountOfLatePayments, int recentCredInquiries,
                        double credLimit, double accountBalance, int lenCredHistory) {

        this.customerAge = age;
        this.recentLatePaymentNumber = latePaymentsOnRecord;
        this.amountOfLatePayments = amountOfLatePayments;
        this.recentCreditInquiries = recentCredInquiries;
        this.creditLimit = credLimit;
        this.creditAccountBalance = accountBalance;
        this.lengthOfCreditHistory = lenCredHistory;
        this.creditScore = calculateCreditScore();
        this.creditUsed = (this.creditAccountBalance / this.creditLimit) * 100;
    }

    public CreditReport(int age) {
    /*for customers of age lesser than 18 and greater than 0*/
        this.customerAge = age;
        this.recentLatePaymentNumber = 0;
        this.recentCreditInquiries = 0;
        this.creditLimit = 0;
        this.creditAccountBalance = 0;
        this.lengthOfCreditHistory = 0;
        this.creditScore = calculateCreditScore();
        this.creditUsed = (this.creditLimit / this.creditAccountBalance) * 100;
        this.amountOfLatePayments = 0;
    }

    /**
     * getCreditScore gets the user's pre-calculated credit score
     *
     * @return the customer's pre-calculated credit score.
     */
    public int getCreditScore() {
        return this.creditScore;
    }

    /**
     * calculateCreditScore calculates the credit score for the customer. the minimum credit score is 100, so the score
     * is initialized to 100. 10% of the score is not calculated in this class, so that portion(79)
     * is automatically added to the score. The rest of the score is calculated by adding the
     * sub scores calculated by the other methods in the class.
     *
     * @return returns the customer's calculated credit score.
     */
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

    /** getCustomerAge returns the customer's age
     *
     * @return customer's age*/
    public int getCustomerAge() {
        return this.customerAge;
    }

    /** calcNewCreditScore calculates the customer's credit sub score based on how many new credit lines they've
     *                     recently acquired or inquired about. This includes applying for credit cards, mortgages,
     *                     loans, etc.
     * @return credit inquiry sub score
     */
    private int calcNewCreditScore() {
        if (this.recentCreditInquiries < 8)
            return 80 - (this.recentCreditInquiries * 10);
        else return 0;
    }

    /**calcPaymentHistoryScore calculates their portion of the credit score dependent upon how many late payments
     *                         they've made late, and the total amounts the late payments amount to. The sub score is
     *                         calculated based on a "severity multiplier" assigned based on how many late payments they
     *                         have. this results in a lower subscore if the user has many late payments vs. someone with
     *                         the same AMOUNT of late payments, but a lower frequency
     *
     * @return late payment portion of the customer's credit score.*/
    private int calcPaymentHistoryScore() {
        //min 0, max 279 (35% of credit score)
        double severity;
        int tempScore;
        switch (this.recentLatePaymentNumber) {
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

        double latePaymentMultiplier = severity * this.amountOfLatePayments;

        if (latePaymentMultiplier == 0 | latePaymentMultiplier < 1000) tempScore = 279;
        else if(latePaymentMultiplier < 3000) tempScore = 200;
        else if (latePaymentMultiplier < 5000) tempScore = 100;
        else tempScore = 0;

        return tempScore;

    }

    /**calcAmtOwedScore calculates the portion of the customer's credit score dependent on the amount of money they owe.
     *                  if the customer's current balance is equal to 0 or if their creditUsed percentage is less than
     *                  10, they automatically get the max score. if their creditUsed percentage is 50% or above, their
     *                  score for this portion is automatically 0
     *
     * @return the "amount owed" portion of their credit score.*/
    private int calcAmtOwedScore() {
        //max is 239.7
        int tempScore;

        if (this.creditAccountBalance == 0 | this.creditUsed < 10) tempScore = 239;
        else if (this.creditUsed < 20) tempScore = 200;
        else if (this.creditUsed < 50) tempScore = 100;
        else tempScore = 0;

        return tempScore;
    }

    /**calcLenHistoryScore calculates the portion of the customer's credit score dependent on how long their credit
     *                     history is. Here, I simply find the percentage of the customer's age that they've had credit
     *                     and add that to 20 for their score. It's not correct, but produces reliable fake figures.
     * @return returns the history portion of the customer's credit score.*/
    private int calcLenHistoryScore() {
        //max is 120
        int tempScore = 20;

        tempScore += (this.lengthOfCreditHistory / (this.customerAge + 1)) * 100;

        if (tempScore > 120) {
            tempScore = 120;
        } else if (tempScore < 0) {
            tempScore = 0;
        }

        return tempScore;
    }

}
