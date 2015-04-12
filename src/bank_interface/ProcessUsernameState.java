package bank_interface;

import bank_package.BankProxy;

/**
 * Created by robert on 3/15/2015.
 */
public class ProcessUsernameState implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    /**
     * creates a new state representing the customer interface state when the user confirms that they have an account and
     * must then enter their email
     *
     * @param newBankProxy         BankProxy object passed by the CustomerInterface constructor. used to retrieve and set
     *                             certain customer information
     * @param newCustomerInterface instance of CustomerInterface, used to set new states when the state changes.
     */
    public ProcessUsernameState(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {

        this.customerInterface = newCustomerInterface;
        bankProxy = newBankProxy;

    }

    /**
     * after registering or stating they have an account, the customer is prompted to enter their UUID here.
     *           This method does not verify whether or not the UUID belongs to the customer or not. The user simply
     *           enters their UUID and the method checks whether or not the bank has the UUID on file through the bankProxy.
     *           They are given 5 attempts before the system closes. Upon a successful match, the state is changed to
     *           ProcessPasswordState and the customer is prompted for their password.
     *  @param email customer's email passed from the gui form to retrieve the customer's actual email, used to check
     *              password for validity
     *
     * @return returns feedback to the user based on the outcome of the email entering process
     **/
    @Override
    public String enterEmail(String email) {

        try {

            if (!(bankProxy.hasCustomer(email))) {
                return ("Email not found.");
            } else if (bankProxy.hasCustomer(email)) {
                customerInterface.setCustomerInterfaceState(customerInterface.processPasswordState);
                customerInterface.setCustomer(bankProxy.requestCustomer(email));
            }
        } catch (IllegalArgumentException p) {
            System.out.printf("Invalid argument");
        }

        return ("Could not process request.");
    }

    /**
     * not allowed in this state
     *
     * @param password customer's password
     * @return returns feedback to the user depending on the outcome of the password entering process*/
    @Override
    public String enterPassword(String password) {
        return ("You must enter your Email first.");

    }

    /**
     * saves all bank information to file, changes the current state to LoggedOffState, and brings up the first menu
     **/
    @Override
    public void logOff() {
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
    }

    /** startTransaction not allowed in this state
     *
     * @param transactionChoice String version of the user's transaction choice (transfer, withdraw, deposit)
     * @param accountFromNumber Account number of the account to take money FROM
     * @param accountToNumber Account number of the accoun tot put money IN
     * @param withdrawAmount Amount of money to withdraw. For transfers, this will equal deposit
     * @param depositAmount Amount of money to deposit. For transfers, this will equal withdraw
     *
     * @return returns feedback to the customer based on the outcome of the transaction process
     * */
    @Override
    public String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber, double withdrawAmount, double depositAmount) {
        return ("You must log in first.");
    }

    /**
     * not allowed in this state
     *
     * @param accountRequest String representation of the user's desired account type
     * @return returns feedback to the user depending on the outcome of the account adding process.*/
    @Override
    public String addAccount(String accountRequest) {
        return ("You must log in first.");
    }

}
