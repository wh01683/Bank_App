package bank_package;

/**
 * Created by robert on 3/12/2015.
 */
public class IndividualRetirementAccount implements Account {

    private final double MIN_BALANCE = 100.00;
    private Customer owner;
    private double interestRate;
    private double accountBalance;


    public IndividualRetirementAccount(double openingBalance, Customer owner) {
        this.accountBalance = openingBalance;
        this.owner = owner;
        this.interestRate = calculateInterestRate();
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
        return "MONEY MARKET INDIVIDUAL RETIREMENT ACCOUNT";
    }

    @Override
    public Customer getOwner() {
        return this.owner;
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
