package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

/**
 * Created by robert on 3/13/2015.
 */
public class MoneyMarketAccount implements Account {

    private final String TYPE = "MMA";
    private final Integer ACCOUNT_NUMBER;
    private final double MINIMUM_REQUIRED_BALANCE = 5000;
    private final Customer OWNER;
    private RandomGenerator random = new RandomGenerator();
    private double interestRate = .01;
    private double accountBalance;

    public MoneyMarketAccount(Customer owner, double openingBalance) {

        this.OWNER = owner;
        this.accountBalance = openingBalance;
        this.ACCOUNT_NUMBER = random.acctGen();
    }

    @Override
    public String toString() {
        return String.format("||%-10s||%-10d||%-20.2f||%-20s||%-30s||%-4d||%-6d||%-7.0f||", this.TYPE, this.ACCOUNT_NUMBER, this.accountBalance,
                this.OWNER.getName(), this.OWNER.getUUID(), this.OWNER.getChexSystemsScore(), 0, this.getMinRequiredBalance());

    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }


    public boolean checkWithdrawLimits(double withdrawal) {
        if (withdrawal > this.accountBalance)
            return false;

        else
            return true;
    }

    @Override
    public Account getNewAccount(Customer customer, double openingBalance) {
        return new MoneyMarketAccount(customer, openingBalance);
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

    @Override
    public double withdraw(double amount) {

        if (checkWithdrawLimits(amount)) {
            this.accountBalance -= amount;
            return amount;
        } else {
            return -1;
        }
    }
}
