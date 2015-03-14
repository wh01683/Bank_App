package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

/**
 * Created by robert on 3/12/2015.
 */
public class IndividualRetirementAccount implements Account {

    private final double MIN_BALANCE = 100.00;
    private final String TYPE = "MM-IRA";
    private final Integer ACCOUNT_NUMBER;
    private final Customer OWNER;
    private RandomGenerator random = new RandomGenerator();
    private double interestRate;
    private double accountBalance;


    public IndividualRetirementAccount(Customer owner, double openingBalance) {
        this.accountBalance = openingBalance;
        this.OWNER = owner;
        this.interestRate = calculateInterestRate();
        this.ACCOUNT_NUMBER = random.acctGen();
    }

    @Override
    public Account getNewAccount(Customer customer, double openingBalance) {
        return new IndividualRetirementAccount(customer, openingBalance);
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
        return amount;
    }

    private double calculateInterestRate() {
        if (this.accountBalance > 99999.0) {
            this.interestRate = .15;
            return .15;
        } else this.interestRate = .10;
        return .10;
    }
}
