package bank_package;

/**
 * Created by robert on 3/11/2015.
 */
public class CheckingAccount implements Account {

    /*ToDo: implement observer to watch for new accounts created calculate account number from the number
    * of accounts already created*/
    final double ACCOUNT_NUMBER, COMPOUND_FREQUENCY;
    final boolean WITHDRAWALS_ALLOWED;
    final Customer OWNER;

    public CheckingAccount(double interest, Customer owner, double compFreq,
                           boolean withdrawAllowed) {
        this.OWNER = owner;
        this.COMPOUND_FREQUENCY = compFreq;
        this.WITHDRAWALS_ALLOWED = withdrawAllowed;
    }




}
