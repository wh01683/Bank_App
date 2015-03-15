package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

import java.io.Serializable;


class SavingsAccount implements Account, Serializable {

    private final String TYPE = "SAVINGS";
    private final Integer ACCOUNT_NUMBER;
    private final double MINIMUM_REQUIRED_BALANCE = 5;

    private final Customer OWNER;
    private final RandomGenerator random = new RandomGenerator();
    private final double INTEREST_RATE = .0105;
    private double accountBalance;

    public SavingsAccount(Customer owner, double openingBalance) {
        this.OWNER = owner;
        this.accountBalance = openingBalance;
        this.ACCOUNT_NUMBER = random.acctGen();
    }


    public boolean checkWithdrawLimits(double withdrawal) {
        return withdrawal <= this.accountBalance;
    }


    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    @Override
    public String toString() {
        return String.format("||%-10s||%-10d||%-20.2f||%-20s||%-30s||%-4d||%-6d||%-7.0f||", this.TYPE, this.ACCOUNT_NUMBER, this.accountBalance,
                this.OWNER.getName(), this.OWNER.getUUID(), this.OWNER.getChexSystemsScore(), 0, this.getMinRequiredBalance());
    }

    @Override
    public double getBalance() {
        return this.accountBalance;
    }

    @Override
    public String getType() {
        return this.TYPE;
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

    public void update() {
        this.accountBalance *= (this.INTEREST_RATE+1);
    }


    @Override
    public Account applyForNewAccount(Customer customer, double openingBalance) {

        if(decideApproved(customer, openingBalance)){
            return new SavingsAccount(customer, openingBalance);
        }
        else{
            System.out.println("Sorry, " + customer.getName() + ". You do not qualify for a Savings Account at this time.");
            return null;
        }

    }

    private boolean decideApproved(Customer customer,double openingBalance) {
        boolean tempApproved = true;
        //tempApproved = !(openingBalance < this.MINIMUM_REQUIRED_BALANCE);
        //tempApproved = !(customer.getChexSystemsScore() < 300 | customer.getCreditScore() < 300);
        return tempApproved;
    }
}
