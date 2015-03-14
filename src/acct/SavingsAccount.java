package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

/**
 * Created by robert on 3/11/2015.
 */
public class SavingsAccount implements Account {

    private final String TYPE = "SAVINGS";
    private final Integer ACCOUNT_NUMBER;
    private final double MINIMUM_REQUIRED_BALANCE = 5;
    private final Customer OWNER;
    private RandomGenerator random = new RandomGenerator();
    private double interestRate = .0105;
    private double accountBalance;

    public SavingsAccount(Customer owner, double openingBalance) {
        this.OWNER = owner;
        this.accountBalance = openingBalance;
        this.ACCOUNT_NUMBER = random.acctGen();
    }

    @Override
    public Account getNewAccount(Customer customer, double openingBalance) {
        return null;
    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10d %-20.2f %-20s %-30s %-4d %-6d %-4.0f", this.TYPE, this.ACCOUNT_NUMBER, this.accountBalance,
                this.OWNER.getName(), this.OWNER.getUUID(), this.OWNER.getChexSystemsScore(), 0, this.getMinRequiredBalance());
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
        return 0;
    }
}
