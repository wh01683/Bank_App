package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;
import bank_package.uScanner;

/**
 * Created by robert on 3/12/2015.
 */

public class CertificateOfDepositAccount implements Account {
    private static final uScanner termLength = new uScanner("Please enter desired Term Length. Please note this is fixed.", 0, 49);
    private final double INTEREST_RATE;
    private final Integer ACCOUNT_NUMBER;
    private final String TYPE = "FIXED TERM CERTIFICATE OF DEPOSIT";
    private final double MIN_BALANCE = 1000.00;
    private final int TERM_LENGTH;
    private final Customer OWNER;
    private final RandomGenerator random = new RandomGenerator();
    private double accountBalance;


    public CertificateOfDepositAccount(Customer customer, double openingBalance, int termLength) {

        this.OWNER = customer;
        this.INTEREST_RATE = calculateInterestRate(termLength);
        this.accountBalance += openingBalance;
        this.TERM_LENGTH = termLength;
        this.ACCOUNT_NUMBER = random.acctGen();

    }

    @Override
    public Account getNewAccount(Customer owner, double openingBalance) {
        return new CertificateOfDepositAccount(owner, openingBalance, termLength.intGet());
    }

    @Override
    public String toString() {
        return TYPE + "-" + this.ACCOUNT_NUMBER + "-" + this.accountBalance + "-" + this.OWNER.getName() + "-" + this.OWNER.getUUID() + "-" +
                this.OWNER.getChexSystemsScore() + "-" + this.getMinRequiredBalance() + "-" + this.TERM_LENGTH;
    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    @Override
    public double getBalance() {
        return this.accountBalance;
    }

    @Override
    public void setBalance(double newBalance) {
        //DOING NOTHING HERE PLEASE MOVE ALONG. THESE ARE NOT THE METHODS YOU'RE LOOKING FOR.
    }

    @Override
    public double getInterest() {
        return this.INTEREST_RATE;
    }

    private double calculateInterestRate(int termLength) {

        /*Minimum length term is 1 month.*/

        if (termLength > 48)
            return .15;
        else if (termLength > 36)
            return .12;
        else if (termLength > 24)
            return .1;
        else if (termLength > 18)
            return .07;
        else if (termLength > 12)
            return .05;
        else if (termLength > 0)
            return .03;
        else
            return 0;
    }


    @Override
    public String getType() {
        return this.TYPE;
    }

    @Override
    public Customer getOwner() {
        return this.OWNER;
    }

    @Override
    public double getMinRequiredBalance() {
        return this.MIN_BALANCE;
    }

    @Override
    public double deposit(double amount) {
        this.accountBalance += amount;
        return amount;
    }

    @Override
    public double withdraw(double amount) {
        this.accountBalance -= amount;
        return amount; //returns confirmation of amount withdrawn
    }

}
