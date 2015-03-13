package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

/**
 * Created by robert on 3/11/2015.
 */
public class CheckingAccount implements Account {

    private final String TYPE = "CHECKING ACCOUNT";
    private final int ACCOUNT_NUMBER;
    private final double MINIMUM_REQUIRED_BALANCE = 0;
    private final Customer OWNER;
    private RandomGenerator random = new RandomGenerator();

    private double overDraftProtection = -750.0; //set to -750 dollars over draft protection allowed
    private double overDraftFee = 35; //35 dollar default over draft fee... it's what my bank charges
    private CheckingAccountApplication checkingAccountApplication;
    private double interestRate;
    private double accountBalance;

    public CheckingAccount(Customer owner, double openingBalance) {

        this.OWNER = owner;
        this.accountBalance = openingBalance;
        calculateOverdraftProtection();
        this.ACCOUNT_NUMBER = random.acctGen();
    }

    @Override
    public String toString() {
        return TYPE + "-" + this.ACCOUNT_NUMBER + "-" + this.OWNER.getName() + "-" + this.OWNER.getUUID() + "-" +
                this.OWNER.getChexSystemsScore() + "-" + this.overDraftProtection + "-" + this.getMinRequiredBalance();
    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    @Override
    public Account getNewAccount(Customer customer, double openingBalance) {
        return new CheckingAccount(customer, openingBalance);
    }

    @Override
    public double getBalance() {
        return this.accountBalance;
    }

    @Override
    public void setBalance(double newBalance) {
        this.accountBalance = newBalance;
    }

    @Override
    public double getInterest() {
        return this.interestRate;
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
        return this.MINIMUM_REQUIRED_BALANCE;
    }

    @Override
    public double deposit(double amount) {
        this.accountBalance += amount;
        return amount;
    }

    private void calculateOverdraftProtection() {
        if (this.OWNER.getChexSystemsScore() < 200)
            this.overDraftProtection = -750;
        else if (this.OWNER.getChexSystemsScore() < 300)
            this.overDraftProtection = -1500;
        else
            this.overDraftProtection = -2500;
    }
    @Override
    public double withdraw(double amount) {
        return 0;
    }
}
