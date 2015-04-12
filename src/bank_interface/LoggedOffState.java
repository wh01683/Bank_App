package bank_interface;


import bank_package.BankProxy;

public class LoggedOffState implements CustomerInterfaceState {

    private final CustomerInterface customerInterface;
    /**
     * LoggedOffState creates a LoggedOffState state used by the customer interface class (default start state)
     *
     * @param newCustomerInterface customer interface instance passed through customer interface constructor. used to
     *                             set and update states of the customer interface
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
 * logOff not allowed in this state, user is already logged off
 * */
@Override
    public void logOff() {
    customerInterface.saveBankDataToFile();
        System.out.println("You are already logged off.");

}

    /**
 * startTransaction not allowed in this state
     *
     * @param transactionChoice
     * @param accountFromNumber
     * @param accountToNumber
     * @param withdrawAmount
     * @param depositAmount*/
@Override
public String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber, double withdrawAmount, double depositAmount) {
    return ("You must log in first.");

}
/**
 * addAccount not allowed in this state
 *
 * @param accountRequest*/
@Override
public String addAccount(String accountRequest) {
    return ("You must log in first.");

}
}
