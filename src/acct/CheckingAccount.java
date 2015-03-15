package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;

import java.io.Serializable;



class CheckingAccount implements Account, Serializable {

    private final String TYPE = "CHECKING";
    private final int ACCOUNT_NUMBER;
    private final double MINIMUM_REQUIRED_BALANCE = 0;
    private final Customer OWNER;
    private final RandomGenerator random = new RandomGenerator();
    private final double overDraftFee = 35; //35 dollar default over draft fee... it's what my bank charges
    private double overDraftProtection = -750.0; //set to -750 dollars over draft protection allowed
    private final double INTEREST_RATE = .01;
    private double accountBalance;

    public CheckingAccount(Customer owner, double openingBalance) {

        this.OWNER = owner;
        this.accountBalance = openingBalance;
        calculateOverdraftProtection();
        this.ACCOUNT_NUMBER = random.acctGen();
    }


    public boolean checkWithdrawLimits(double withdrawal) {
        return withdrawal < (this.overDraftFee + this.accountBalance);
    }

    @Override
    public String toString() {

        return String.format("||%-10s||%-10d||%-20.2f||%-20s||%-30s||%-4d||%-6.0f||%-7.0f||", this.TYPE, this.ACCOUNT_NUMBER, this.accountBalance,
                this.OWNER.getName(), this.OWNER.getUUID().toString(), this.OWNER.getChexSystemsScore(), this.overDraftProtection, this.getMinRequiredBalance());
    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
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
        if (this.accountBalance >= (amount + overDraftProtection)) {
            this.accountBalance -= amount;
            return amount;
        } else return -1;
    }

    public void update() {
        this.accountBalance *= (this.INTEREST_RATE+1);
    }
    @Override
    public Account applyForNewAccount(Customer customer, double openingBalance) {

        if(decideApproved(customer, openingBalance)){
            return new CheckingAccount(customer, openingBalance);
        }
        else{
            System.out.println("Sorry, " + customer.getName() + ". You do not qualify for a Savings Account at this time.");
            return null;
        }

    }

    private boolean decideApproved(Customer customer,double openingBalance) {
        boolean tempApproved = true;
        //tempApproved = !(openingBalance < this.MINIMUM_REQUIRED_BALANCE);
        //tempApproved = !(customer.getChexSystemsScore() < 300);
        return tempApproved;
    }

}
