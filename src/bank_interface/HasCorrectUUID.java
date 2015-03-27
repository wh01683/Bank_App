package bank_interface;

import bank_package.BankProxy;
import utility.uScanner;

/**
 * Created by robert on 3/15/2015.
 */
public class HasCorrectUUID implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    /**
     * HasCorrectUUID state class used by CustomerInterface, represents the state entered when the customer provides
     * a correct UUID and must then enter a password
     *
     * @param newBankProxy         new BankProxy object passed from the CustomerInterface constructor; used to retrieve customer
     *                             information for password verification and etc.
     * @param newCustomerInterface instance of the customerInterface, used to set the customerInterface state
     */
    public HasCorrectUUID(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {
        bankProxy = newBankProxy;
        this.customerInterface = newCustomerInterface;


    }


    /**
     * startLoginProcess useless in HasCorrectUUID state
     *
     * @param isRegistered false by default, not necessary here
     */
    @Override
    public void startLoginProcess(boolean isRegistered) {
        System.out.println("You are already registered.");
    }

    /**
     * logOff saves current bank data to a file and logs the customer off
     * */
    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        customerInterface.hasAccount(false);

    }

    /**
     * requestInformation not used in this state, they have not entered their password yet
     * */
    @Override
    public void requestInformation() {

        System.out.println("Must enter your pass word first.");
        customerInterface.enterPassword();

    }

    /**
     * startTransaction not used in this state. If this method is ever invoked during this state, the customer is
     *                  prompted for their password before allowing them to proceed.
     * */
    @Override
    public void startTransaction() {
        System.out.println("Must enter your pass word first.");
        customerInterface.enterPassword();
    }

    /**
     * addAccount not used in this state. If this method is ever invoked during this state, the customer is
     *            prompted for their password before allowing them to proceed.
     * */
    @Override
    public void addAccount() {
        System.out.println("Must enter your pass word first.");
        customerInterface.enterPassword();
    }

    /**
     * enterUUID not used in this state. If this method is ever invoked during this state, the customer is
     *           prompted for their password before allowing them to proceed.
     */
    @Override
    public void enterUUID() {
        System.out.println("You have already entered your UUID, please enter your password.");
        customerInterface.enterPassword();
    }

    /** enterPassword creates a final String variable "realPass" to store the value of the customer's actual password on
     *                file. Customer is prompted for their password as long as the String they entered does not match
     *                their password on file. Customers are given 5 attempts before the system exits. Upon a successful
     *                match, the current state is set to "LoggedOn" and the "requestInformation" method is invoked to
     *                initialize the logged-in process loop inside the LoggedOn class.
     * */
    @Override
    public void enterPassword() {
        /*if they do have an account, they are requested to provide their UUID*/
        /*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*/
        final String realPass = bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getPASSWORD();
        /*the user is prompted for their password, which is stored in enteredPass*/
        uScanner PASSWORD_SCANNER = new uScanner("Please enter your password.\nBACK, LOGOFF.", 4, 20);
        String enteredPass = PASSWORD_SCANNER.alphaNumericStringGet();

        while (!(enteredPass.equalsIgnoreCase("BACK")) | !(enteredPass.equalsIgnoreCase("LOGOFF"))) {
            int attempts = 1;
        /*they will be prompted for their password as long as their REAL PASSWORD and their ENTERED PASSWORD do not match
        * and as long as their attempts do not exceed 4.*/
        while (!enteredPass.equals(realPass) && attempts < 6) {
            if (attempts == 5) {
                System.out.println("Maximum attempts reached. Exiting.");
                System.exit(1);
            } else {
                System.out.println("Invalid password. Try Again. " + attempts + " of 4 attempts exhausted.");
                attempts++;
                enteredPass = PASSWORD_SCANNER.alphaNumericStringGet();
            }
        }

        if (enteredPass.equals(realPass)) {
            System.out.println("Congratulations! Your input password " + enteredPass + " matches your real password" +
                    " on file, " + realPass + "\nYou may now access your bank account information!\n");
            System.out.println("\n----------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
            customerInterface.setCustomerInterfaceState(customerInterface.loggedIn);
            customerInterface.requestInformation();
        } else {
            System.out.println("Invalid password, exiting.");
            System.exit(1);
        }
        }

        if (enteredPass.equalsIgnoreCase("BACK")) {
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
            customerInterface.enterUUID();
        }
        if (enteredPass.equalsIgnoreCase("LOGOFF")) {
            System.out.println("Have a great day!");
            customerInterface.saveBankDataToFile();
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
            customerInterface.hasAccount(false);
        }
    }
}
