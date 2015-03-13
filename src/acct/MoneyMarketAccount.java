package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

/**
 * Created by robert on 3/13/2015.
 */
public class MoneyMarketAccount implements Account {

    private final String TYPE = "MONEY MARKET ACCOUNT";
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
        return TYPE + "-" + this.ACCOUNT_NUMBER + "-" + this.OWNER.getName() + "-" + this.OWNER.getUUID() + "-" +
                this.OWNER.getChexSystemsScore() + "-" + this.getMinRequiredBalance();
    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
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
        this.accountBalance -= amount;
        return amount;
    }

}