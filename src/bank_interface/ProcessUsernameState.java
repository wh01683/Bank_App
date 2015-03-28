package bank_interface;

import bank_package.BankProxy;
import utility.uScanner;

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
     *
     * @param email customer's email passed from the gui form to retrieve the customer's actual email, used to check
     *              password for validity
     *
     *              */
    @Override
    public void enterEmail(String email) {

        try {
            uScanner EMAIL_SCANNER = new uScanner("Please enter your Email.\nBACK, LOGOFF", 0, 50);


            String emailInput = EMAIL_SCANNER.alphaNumericStringGet();

            while (!(emailInput.equalsIgnoreCase("BACK"))) {

                if (emailInput.equalsIgnoreCase("LOGOFF")) {
                    System.out.println("Have a great day!");
                    customerInterface.saveBankDataToFile();
                    customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);


                } else if (!bankProxy.hasCustomer(emailInput)) {
            /*if the customerHashTable does not contain the provided customer ID, the system will display a prompt
            * and ask them again if they would like to register. If they do not, the user is prompted for their UUID again
            * They are given 5 attempts total before the system exits.*/
                    System.out.println("We could not find your ID, please try again.");
                    boolean wantsToRegister = wantsToRegister();
                    int emailInputCounter = 1;

                    while (!bankProxy.hasCustomer(emailInput) && emailInputCounter < 7) {
                        if (emailInputCounter == 6) {
                            System.out.println("All attempts exhausted. System exiting.");
                            System.exit(1);
                        }
                        if (wantsToRegister) {
                            customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
                        } else if (emailInputCounter < 6) {
                            System.out.println(emailInputCounter + " attempts remaining of 5. Please try again.");
                            emailInput = EMAIL_SCANNER.alphaNumericStringGet();
                            if (emailInput.equalsIgnoreCase("BACK")) {
                                customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);

                            } else if (emailInput.equalsIgnoreCase("LOGOFF")) {
                                System.out.println("Have a great day!");
                                customerInterface.logOff();
                            }
                        }
                        emailInputCounter++;
                    }
                    customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
                }

                customerInterface.setCustomerInterfaceState(customerInterface.processPasswordState);
                customerInterface.setCustomer(bankProxy.requestCustomer(emailInput));
            }

            customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);

        } catch (IllegalArgumentException p) {
            System.out.printf("Invalid UUID entered at HasAccount : enterEmail");
        }
    }

    /**
     * not allowed in this state
     *
     * @param password*/
    @Override
    public void enterPassword(String password) {
        System.out.println("You must enter your Email first.");
        customerInterface.enterEmail(password);
    }

    /**
     * saves all bank information to file, changes the current state to LoggedOffState, and brings up the first menu
 * */
    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
    }

    /**
     * not allowed in this state
 * */
    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
    }

    /**
     * not allowed in this state
 * */
    @Override
    public void startTransaction() {
        System.out.println("You must log in first.");
    }

    /**
     * not allowed in this state
 * */
    @Override
    public void addAccount() {
        System.out.println("You must log in first.");
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
            customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
        } else if (answer.equalsIgnoreCase("LOGOFF")) {
            System.out.println("Have a great day!");
            customerInterface.saveBankDataToFile();
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);

        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }
}
