package bank_interface;

import bank_package.BankProxy;
import utility.uScanner;

import java.util.UUID;

/**
 * Created by robert on 3/15/2015.
 */
public class HasAccount implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    /**
     * HasAccount creates a new HasAccount state used by the CustomerInterface
     *
     * @param newBankProxy         BankProxy object passed by the CustomerInterface constructor. used to retrieve and set
     *                             certain customer information
     * @param newCustomerInterface instance of CustomerInterface, used to set new states when the state changes.
     */
    public HasAccount(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {

        this.customerInterface = newCustomerInterface;
        bankProxy = newBankProxy;

    }

    /**
     * enterUUID after registering or stating they have an account, the customer is prompted to enter their UUID here.
     *           This method does not verify whether or not the UUID belongs to the customer or not. The user simply
     *           enters their UUID and the method checks whether or not the bank has the UUID on file through the bankProxy.
     *           They are given 5 attempts before the system closes. Upon a successful match, the state is changed to
     *           HasCorrectUUID and the customer is prompted for their password.
     * */
    @Override
    public void enterUUID() {

        try {
            uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID.\nBACK, LOGOFF", 0, 37);
            UUID newCustomerUUID;

            String uuidInput = UUID_SCANNER.alphaNumericStringGet();

            while (!(uuidInput.equalsIgnoreCase("BACK"))) {

                if (uuidInput.equalsIgnoreCase("LOGOFF")) {
                    System.out.println("Have a great day!");
                    customerInterface.saveBankDataToFile();
                    customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
                    customerInterface.hasAccount(false);
                } else if (!bankProxy.hasCustomer(UUID.fromString(uuidInput))) {
            /*if the customerHashTable does not contain the provided customer ID, the system will display a prompt
            * and ask them again if they would like to register. If they do not, the user is prompted for their UUID again
            * They are given 5 attempts total before the system exits.*/
                    System.out.println("We could not find your ID, please try again.");
                    boolean wantsToRegister = wantsToRegister();
                    int uuidCounter = 1;

                    while (!bankProxy.hasCustomer(UUID.fromString(uuidInput)) && uuidCounter < 7) {
                        if (uuidCounter == 6) {
                            System.out.println("All attempts exhausted. System exiting.");
                            System.exit(1);
                        }
                        if (wantsToRegister) {
                            customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
                            customerInterface.hasAccount(false);
                        } else if (uuidCounter < 6) {
                            System.out.println(uuidCounter + " attempts remaining of 5. Please try again.");
                            uuidInput = UUID_SCANNER.alphaNumericStringGet();
                            if (uuidInput.equalsIgnoreCase("BACK")) {
                                customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
                                customerInterface.hasAccount(false);
                            } else if (uuidInput.equalsIgnoreCase("LOGOFF")) {
                                System.out.println("Have a great day!");
                                customerInterface.logOff();
                            }
                        }
                        uuidCounter++;
                    }
                    customerInterface.setCustomerInterfaceState(customerInterface.hasIncorrectUUID);
                }

                customerInterface.setCustomerInterfaceState(customerInterface.hasCorrectUUID);
                customerInterface.setCustomerUUID(UUID.fromString(uuidInput));
                customerInterface.enterPassword();
            }

            customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);

        } catch (IllegalArgumentException p) {
            System.out.printf("Invalid UUID entered at HasAccount : enterUUID");
            enterUUID();
        }
    }

    /**
     * not allowed in this state
     * */
    @Override
    public void enterPassword() {
        System.out.println("You must enter your UUID first.");
        customerInterface.enterUUID();
    }

    /**
     * not allowed in this state
 * */
    @Override
    public void hasAccount(boolean isRegistered) {
        System.out.println("You are already registered.");
    }

    /**
     * saves all bank information to file, changes the current state to LoggedOff, and brings up the first menu
 * */
    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        customerInterface.hasAccount(false);

    }

    /**
     * not allowed in this state
 * */
    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }

    /**
     * not allowed in this state
 * */
    @Override
    public void startTransaction() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }

    /**
     * not allowed in this state
 * */
    @Override
    public void addAccount() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }

    /**
     * wantsToRegister upon failure to provide a correct UUID, the customer is asked if they would like to register. They
     *                 are redirected based on their response.
     *
     * @return recursively calls itself until the user enters a correct response or logs off/exits. Will return true if the
     *         customer wants to register.
 * */
    private boolean wantsToRegister() {
        final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES, NO, LOGOFF, EXIT", 2, 6);
        String answer = WANT_REGISTER_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES")) {
            return true;
        } else if (answer.equalsIgnoreCase("NO")) {
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
            customerInterface.enterUUID();
        } else if (answer.equalsIgnoreCase("LOGOFF")) {
            System.out.println("Have a great day!");
            customerInterface.saveBankDataToFile();
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
            customerInterface.hasAccount(false);
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }
}
