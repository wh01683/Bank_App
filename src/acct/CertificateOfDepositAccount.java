package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;
import bank_package.uScanner;
import java.io.Serializable;


class CertificateOfDepositAccount implements Account, Serializable{
    private static final uScanner termLength = new uScanner("Please enter desired Term Length. Please note this is fixed.", 0, 49);
    private final double INTEREST_RATE;
    private final Integer ACCOUNT_NUMBER;
    private final String TYPE = "FIXED CD";
    private final double MINIMUM_REQUIRED_BALANCE = 1000.00;
    private final int TERM_LENGTH;
    private final Customer OWNER;
    private final RandomGenerator random = new RandomGenerator();
    private double accountBalance;

    public CertificateOfDepositAccount(Customer customer, double openingBalance, int termLength) {

        this.OWNER = customer;
        this.INTEREST_RATE = calculateInterestRate(termLength);
        this.accountBalance += openingBalance;
        this.TERM_LENGTH = termLength;
        this.ACCOUNT_NUMBER = random.acctGen();

    }

    @Override
    public boolean checkWithdrawLimits(double withdrawal) {
        return withdrawal <= this.accountBalance;
    }


    @Override
    public String toString() {
        return String.format("||%-10s||%-10d||%-20.2f||%-20s||%-30s||%-4d||%-6d||%-7.0f||", this.TYPE, this.ACCOUNT_NUMBER, this.accountBalance,
                this.OWNER.getName(), this.OWNER.getUUID(), this.OWNER.getChexSystemsScore(), 0, this.getMinRequiredBalance());
    }

    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    @Override
    public double getBalance() {
        return this.accountBalance;
    }

    private double calculateInterestRate(int termLength) {

        /*Minimum length term is 1 month.*/

        if (termLength > 48)
            return .15;
        else if (termLength > 36)
            return .12;
        else if (termLength > 24)
            return .1;
        else if (termLength > 18)
            return .07;
        else if (termLength > 12)
            return .05;
        else if (termLength > 0)
            return .03;
        else
            return 0;
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

        int desiredTermLength = termLength.intGet();

        if(decideApproved(customer, openingBalance, desiredTermLength)){
            return new CertificateOfDepositAccount(customer, openingBalance, desiredTermLength);
        }
        else{
            System.out.println("Sorry, " + customer.getName() + ". You do not qualify for a Fixed-Term Certificate of Deposit Account at this time.");
            return null;
        }

    }

    private boolean decideApproved(Customer customer,double openingBalance, int desiredTermLength) {
        boolean tempApproved;
        tempApproved = !((openingBalance < this.MINIMUM_REQUIRED_BALANCE) | (desiredTermLength<1) | (desiredTermLength>48));
        tempApproved = !(customer.getChexSystemsScore() < 450 | customer.getCreditScore() < 300);
        return tempApproved;
    }

    public Account applyForNewRandomAccount(Customer customer, double openingBalance) {

        int desiredTermLength = random.getInts(-5,55);

        if(decideApproved(customer, openingBalance, desiredTermLength)){
            return new CertificateOfDepositAccount(customer, openingBalance, desiredTermLength);
        }
        else{
            System.out.println("Sorry, " + customer.getName() + ". You do not qualify for a Fixed-Term Certificate of Deposit Account at this time.");
            return null;
        }

    }
}
