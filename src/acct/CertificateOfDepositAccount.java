package acct;

import bank_package.Customer;

import java.io.Serializable;
import java.util.UUID;


class CertificateOfDepositAccount implements Account, Serializable{
    private final Integer ACCOUNT_NUMBER;
    private final String TYPE = "FIXED CD";
    private final double MINIMUM_REQUIRED_BALANCE = 1000.00;
    private final Customer OWNER;
    private Integer termLength;
    private double accountBalance;

    /**
     * creates a new Certificate of Deposit account with a given customer (used to retrieve information for approval),
     * opening balance offered by the customer, and desired term length.
     *  @param customer customer applying for the account
     * @param openingBalance initial balance to be deposited (must be above min required balance)
     * @param accountNumber account number of the newly created account.
     */
    public CertificateOfDepositAccount(Customer customer, double openingBalance, Integer accountNumber) throws NullPointerException {

        this.OWNER = customer;
        double INTEREST_RATE = calculateInterestRate(this.termLength);
        this.accountBalance += openingBalance;
        this.ACCOUNT_NUMBER = accountNumber;

    }

    /**
     * checks to see whether or not the requested withdrawal is greater than the amount currently in the account
     * @param withdrawal proposed withdrawal amount
     * @return returns true if the customer may make a withdrawal, false otherwise
     * */
    @Override
    public boolean checkWithdrawLimits(double withdrawal) {
        return withdrawal <= this.accountBalance;
    }
    /**
     * retrieves the account number associated with the account
     * @return returns the Integer account number
     * */
    public Integer getACCOUNT_NUMBER() {
        return this.ACCOUNT_NUMBER;
    }

    /**
     * retrieves the current balance of the account
     * @return current balance of the account
     *
     * */
    @Override
    public double getBalance() {
        return this.accountBalance;
    }

    /**
     * calculates interest rate for the account based on the requested term length
     * @param termLength length of term (months). Min 1, Max 48
     * @return interest rated granted based on term length
     * */
    private double calculateInterestRate(int termLength) {

        /*Max length term is 48 months.*/

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

    /**
     * applies the customer for a new account by passing the customer and the proposed opening balance
     *
     * @param customer customer object applying for the account
     * @param openingBalance proposed opening balance for the account
     * @param accountNumber account number of the new account
     * @return returns the newly created account object if the customer qualified, else returns null
     * */
    @Override
    public Account applyForNewAccount(Customer customer, double openingBalance, Integer accountNumber) {

        if (decideApproved(customer, openingBalance)) {
            return new CertificateOfDepositAccount(customer, openingBalance, accountNumber);
        }
        else{
            this.termLength = 0;
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
    private boolean decideApproved(Customer customer, double openingBalance) {
        return !(customer.getChexSystemsScore() < 450 | customer.getCreditScore() < 300 | openingBalance < this.MINIMUM_REQUIRED_BALANCE);
    }

    /**
     * sets the term length of the CD to the new desired term length
     *
     * @param newTermLength term length provided by user from drop-down menu
     */
    public void setTermLength(Integer newTermLength) {
        this.termLength = newTermLength;
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
}
