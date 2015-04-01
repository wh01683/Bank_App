package acct;

import bank_package.Customer;
import utility.RandomGenerator;

import java.io.Serializable;
import java.util.UUID;


class MoneyMarketAccount implements Account, Serializable {

    private final String TYPE = "MMA";
    private final Integer ACCOUNT_NUMBER;
    private final double MINIMUM_REQUIRED_BALANCE = 5000;
    private final Customer OWNER;
    private double accountBalance;


    /**
     * creates a new Certificate of Deposit account with a given customer (used to retrieve information for approval)
     * and opening balance offered by the customer
     *
     * @param customer       customer applying for the account
     * @param openingBalance initial balance to be deposited (must be above min required balance)
     */

    public MoneyMarketAccount(Customer customer, double openingBalance) {

        this.OWNER = customer;
        this.accountBalance = openingBalance;
        RandomGenerator random = new RandomGenerator();
        this.ACCOUNT_NUMBER = random.acctGen();
    }

    /**
     * returns a String representation of the account
     *
     * @return a string representation of the account
     */
    @Override
    public String toString() {
        return String.format("||%-10s||%-10d||%-20.2f||%-20s||%-30s||%-4d||%-6d||%-7.0f||", this.TYPE, this.ACCOUNT_NUMBER, this.accountBalance,
                this.OWNER.getName(), this.OWNER.getUUID(), this.OWNER.getChexSystemsScore(), 0, this.getMinRequiredBalance());

    }

    /**
     * retrieves the account number associated with the account
     * @return returns the Integer account number
     * */
    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    /**
     * checks to see whether or not the requested withdrawal is greater than the amount currently in the account
     * @param withdrawal proposed withdrawal amount
     * @return returns true if the customer may make a withdrawal, false otherwise
     * */
    public boolean checkWithdrawLimits(double withdrawal) {
        return withdrawal <= this.accountBalance;
    }


    /**
     * retrieves the current balance of the account
     *
     * @return current balance of the account
     */
    @Override
    public double getBalance() {
        return this.accountBalance;
    }

    /**
     * returns the "TYPE" of the account (i.e. Checking, Savings, etc..)
     *
     * @return returns the Type string of the account
     * */
    @Override
    public String getType() {
        return this.TYPE;
    }

    /**
     * retrieves the minimum balance required to open the account
     * @return minimum required balance
     * */
    @Override
    public double getMinRequiredBalance() {
        return this.MINIMUM_REQUIRED_BALANCE;
    }


    /**
     * deposits a specific amount into the account
     * @param amount amount to be deposited
     * @return returns the same amount passed, used for confirmation
     *
     * */
    @Override
    public double deposit(double amount) {
        this.accountBalance += amount;
        return amount;
    }

    /**
     * withdraws a specific amount from the account
     * @param amount amount to be withdrawn
     * @return returns the same amount withdrawn as confirmation. if the withdrawal was unable to process, -1 is returned.
     * */
    @Override
    public double withdraw(double amount) {

        if (checkWithdrawLimits(amount)) {
            this.accountBalance -= amount;
            return amount;
        } else {
            return -1;
        }
    }
// --Commented out by Inspection START (3/27/15 8:02 PM):
//    public void update() {
//        double INTEREST_RATE = .01;
//        this.accountBalance *= (INTEREST_RATE +1);
//    }
// --Commented out by Inspection STOP (3/27/15 8:02 PM)

    /**
     * applies the customer for a new account by passing the customer and the proposed opening balance
     *
     * @param customer customer object applying for the account
     * @param openingBalance proposed opening balance for the account
     * @return returns the newly created account object if the customer qualified, else returns null
     * */
    @Override
    public Account applyForNewAccount(Customer customer, double openingBalance) {

        if(decideApproved(customer, openingBalance)){
            return new MoneyMarketAccount(customer, openingBalance);
        }
        else{
            System.out.println("Sorry, " + customer.getName() + ". You do not qualify for a Money Market Account at this time.");
            return null;
        }

    }
    /**
     * returns the UUID of the owner associated with the account
     * @return UUID object of the customer associated with the account
     *
     * */
    @Override
    public UUID getOwner() {
        return this.OWNER.getUUID();

    }

    /**
     * local method to decided whether the customer qualifies for the account or not using the customer's information, the
     * proposed opening balance, and the desired term length passed through parameters.
     *
     * @param customer customer applying for the account
     * @param openingBalance proposed opening balance
     * @return returns true if the customer qualifies, false otherwise
     * */
    private boolean decideApproved(Customer customer,double openingBalance) {
        boolean tempApproved;
        tempApproved = !(openingBalance < this.MINIMUM_REQUIRED_BALANCE | customer.getChexSystemsScore() < 300 |
                customer.getCreditScore() < 300);
        return tempApproved;
    }
}
