package bank_interface;

import bank_package.BankProxy;

/**
 * Created by robert on 3/15/2015.
 */
public class ProcessPasswordState implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    /**
     * the process password state represents the state of the customer interface when the customer enters an email that
     * IS on record, and must now enter their password
     *
     * @param newBankProxy         new BankProxy object passed from the CustomerInterface constructor; used to retrieve customer
     *                             information for password verification and etc.
     * @param newCustomerInterface instance of the customerInterface, used to set the customerInterface state
     */
    public ProcessPasswordState(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {
        bankProxy = newBankProxy;
        this.customerInterface = newCustomerInterface;


    }

    /**
     * saves current bank data to a file and logs the customer off
     * */
    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
    }

    /** initiates a transaction for the customer
     *
     * @param transactionChoice String version of the user's transaction choice (transfer, withdraw, deposit)
     * @param accountFromNumber Account number of the account to take money FROM
     * @param accountToNumber Account number of the account ot put money IN
     * @param withdrawAmount Amount of money to withdraw. For transfers, this will equal deposit
     * @param depositAmount Amount of money to deposit. For transfers, this will equal withdraw
     *
     * @return returns feedback to the user depending on the outcome of the transaction process
     * */
    @Override
    public String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber, double withdrawAmount, double depositAmount) {
        return ("Must enter your password first.");
    }

    /**
     * processes user's ENTERED account request
     * @param accountRequest String representation of the account type desired by the customer
     * @return returns feedback to the user based on the outcome of the add account process
     * */
    @Override
    public String addAccount(String accountRequest) {
        return ("Must enter your password first.");
    }

    /**
     * removes the account associated with the account number provided
     *
     * @param accountNumber account number of the account to be removed
     * @return returns feedback based on the outcome of the account removal process
     */
    @Override
    public String removeAccount(Integer accountNumber) {
        return ("You must enter your password first.");
    }

    /**
     * enterEmail not used in this state. If this method is ever invoked during this state, the customer is
     *           prompted for their password before allowing them to proceed.
     * @param email customer's email
     * @return returns feedback to the user based on the outcome of the email entering process
     */
    @Override
    public String enterEmail(String email) {
        return ("You have already entered your Email, please enter your password.");
    }

    /** creates a final String variable "realPass" to store the value of the customer's actual password on
     *                file. Customer is prompted for their password as long as the String they entered does not match
     *                their password on file. Customers are given 5 attempts before the system exits. Upon a successful
     *                match, the current state is set to "LoggedOn" and the "requestInformation" method is invoked to
     *                initialize the logged-in process loop inside the LoggedOn class.
     *
     * @param password password to check for validity
     * @return returns feedback to the customer depending on the outcome of the password entering process*/
    @Override
    public String enterPassword(String password) {

        /*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*/
        final String realPass = bankProxy.requestCustomer(customerInterface.getCustomer().getEmail()).getPASSWORD();
        /*the user is prompted for their password, which is stored in enteredPass*/
        int attempts = 1;
        /*they will be prompted for their password as long as their REAL PASSWORD and their ENTERED PASSWORD do not match
        * and as long as their attempts do not exceed 4.*/
        if (!password.equals(realPass) && attempts < 6) {
            if (attempts == 5) {
                return ("Maximum attempts reached. Exiting.");
            } else {
                attempts++;
                return ("Invalid password. Try Again. " + attempts + " of 4 attempts exhausted.");
            }
        }

        if (password.equals(realPass)) {
            customerInterface.setCustomerInterfaceState(customerInterface.loggedInState);
            return ("Login Successful.");

        }

        return ("Invalid password");
    }
}
