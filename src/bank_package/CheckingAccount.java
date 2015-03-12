package bank_package;

/**
 * Created by robert on 3/11/2015.
 */
class CheckingAccount implements Account {

    /*ToDo: implement observer to watch for new accounts created calculate account number from the number
    * of accounts already created*/
    private final String TYPE = "CHECKING";
    private final double ACCOUNT_NUMBER;
    private final double COMPOUND_FREQUENCY;
    private final boolean WITHDRAWALS_ALLOWED;
    private final Customer OWNER;
    private double interestRate;
    private double balance;

    public CheckingAccount(double interest, Customer owner, double compFreq,
                           boolean withdrawAllowed) {
        this.interestRate = interest;
        this.OWNER = owner;
        this.COMPOUND_FREQUENCY = compFreq;
        this.WITHDRAWALS_ALLOWED = withdrawAllowed;
    }

    @Override
    public double getBalance() {
        return this.balance;
    }

    @Override
    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

    @Override
    public double getInterest() {
        return this.interestRate;
    }

    @Override
    public void setInterest(double newInterest) {
        this.interestRate = newInterest;
    }

    @Override
    public String getType() {
        return this.TYPE;
    }

    @Override
    public Customer getOwner() {
        return this.OWNER;
    }
}
