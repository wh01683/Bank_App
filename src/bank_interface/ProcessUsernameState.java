package bank_interface;

import bank_package.BankProxy;

/**
 * Created by robert on 3/15/2015.
 */
public class ProcessUsernameState implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    /**
     * HasAccount creates a new HasAccount state used by the CustomerInterface
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
     * enterEmail after registering or stating they have an account, the customer is prompted to enter their UUID here.
     *           This method does not verify whether or not the UUID belongs to the customer or not. The user simply
     *           enters their UUID and the method checks whether or not the bank has the UUID on file through the bankProxy.
     *           They are given 5 attempts before the system closes. Upon a successful match, the state is changed to
     *           ProcessPasswordState and the customer is prompted for their password.
     *  @param email customer's email passed from the gui form to retrieve the customer's actual email, used to check
     *              password for validity
     *
     *              */
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
     * @param password customer's password*/
    @Override
    public String enterPassword(String password) {
        return ("You must enter your Email first.");

    }

    /**
     * saves all bank information to file, changes the current state to LoggedOffState, and brings up the first menu
 * */
    @Override
    public void logOff() {
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
    }

    /**
     * not allowed in this state
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
     * not allowed in this state
     *
     * @param accountRequest*/
    @Override
    public String addAccount(String accountRequest) {
        return ("You must log in first.");
    }

}
