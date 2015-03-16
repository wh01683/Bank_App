package acct;

import bank_package.Customer;
import utility.RandomGenerator;

import java.io.Serializable;
import java.util.UUID;


class IndividualRetirementAccount implements Account, Serializable {

    private final double MINIMUM_REQUIRED_BALANCE = 100.00;
    private final String TYPE = "MM-IRA";
    private final Integer ACCOUNT_NUMBER;
    private final Customer OWNER;
    private double interestRate;
    private double accountBalance;


    public IndividualRetirementAccount(Customer owner, double openingBalance) {
        this.accountBalance = openingBalance;
        this.OWNER = owner;
        this.interestRate = calculateInterestRate();
        RandomGenerator random = new RandomGenerator();
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

    private double calculateInterestRate() {
        if (this.accountBalance > 99999.0) {
            this.interestRate = .15;
            return .15;
        } else this.interestRate = .10;
        return .10;
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
        this.interestRate = calculateInterestRate();
        this.accountBalance *= (this.interestRate+1);
    }

    @Override
    public Account applyForNewAccount(Customer customer, double openingBalance) {

        if(decideApproved(customer, openingBalance)){
            return new IndividualRetirementAccount(customer, openingBalance);
        }
        else{
            System.out.println("Sorry, " + customer.getName() + ". You do not qualify for an MM-IRA at this time.");
            return null;
        }

    }

    @Override
    public UUID getOwner() {
        return this.OWNER.getUUID();

    }

    private boolean decideApproved(Customer customer,double openingBalance) {
        boolean tempApproved;
        tempApproved = !(openingBalance < this.MINIMUM_REQUIRED_BALANCE | customer.getChexSystemsScore() < 200 |
                customer.getCreditScore() < 200);
        return tempApproved;
    }


}
