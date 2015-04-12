package bank_interface;


import bank_package.BankProxy;

public class LoggedOffState implements CustomerInterfaceState {

    private final CustomerInterface customerInterface;
    /**
     * LoggedOffState creates a LoggedOffState state used by the customer interface class (default start state)
     *
     * @param newCustomerInterface customer interface instance passed through customer interface constructor. used to
     *                             set and update states of the customer interface
     *
     * @param newBankProxy new bank proxy object used to retrieve information
     */
    public LoggedOffState(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {
        this.customerInterface = newCustomerInterface;
        BankProxy bankProxy = newBankProxy;
    }

    /**
     * enterUUID not allowed in this state
     * @param email customer's email
     */
    @Override
    public String enterEmail(String email) {
        customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
        customerInterface.enterEmail(email);
        return null;
    }
/**
 * enterPassword not allowed in this state
 *
 * @param password customer's password*/
@Override
public String enterPassword(String password) {
    customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
    return null;
    }


/**
 * not allowed in this state, user is already logged off
 **/
@Override
    public void logOff() {
    customerInterface.saveBankDataToFile();
        System.out.println("You are already logged off.");

}

    /** startTransaction not allowed in this state
     *
     * @param transactionChoice String version of the user's transaction choice (transfer, withdraw, deposit)
     * @param accountFromNumber Account number of the account to take money FROM
     * @param accountToNumber Account number of the accoun tot put money IN
     * @param withdrawAmount Amount of money to withdraw. For transfers, this will equal deposit
     * @param depositAmount Amount of money to deposit. For transfers, this will equal withdraw
     *
     * @return returns feedback to the user depending on the outcome of the transaction process
     * */
@Override
public String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber, double withdrawAmount, double depositAmount) {
    return ("You must log in first.");
}

    /**
     * creates and adds a user specified account in the appropriate states
     *
     * @param accountRequest String representation of the account type requested by the customer
     * @param openingBalance opening balance passed to the account factory
     * @return returns feedback to the user based on the outcome of the add account process
     */
@Override
public String addAccount(String accountRequest, double openingBalance) {
    return ("You must log in first.");

}

    /**
     * removes the account associated with the account number provided
     *
     * @param accountNumber account number of the account to be removed
     * @return returns feedback based on the outcome of the account removal process
     */
    @Override
    public String removeAccount(Integer accountNumber) {
        return ("You must log in first");
    }
}
