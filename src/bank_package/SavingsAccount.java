package bank_package;

/**
 * Created by robert on 3/11/2015.
 */
class SavingsAccount implements Account {

    /*ToDo: implement observer to watch for new accounts created calculate account number from the number
    * of accounts already created*/
    private final String TYPE = "SAVINGS ACCOUNT";
    private final double ACCOUNT_NUMBER = 1000000;
    private final double MINIMUM_REQUIRED_BALANCE = 5;
    private final Customer OWNER;
    private SavingsAccountApplication savingsAccountApplication;
    private double interestRate;
    private double accountBalance;

    public SavingsAccount(double openingBalance, Customer owner) {
        this.OWNER = owner;
        this.accountBalance = openingBalance;
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
