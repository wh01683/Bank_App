package bank_interface;

import bank_package.BankProxy;
import utility.uScanner;

import java.util.UUID;

/**
 * Created by robert on 3/15/2015.
 */
public class HasIncorrectUUID implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    /**
     * HasIncorrectUUID constructs a new HasIncorrectUUID state used by CustomerInterface class
     *
     * @param newCustomerInterface Customer interface instance passed via Customer Interface constructor, used to set
     *                             the new current state of the customer interface
     * @param newBankProxy         bank proxy object used to retrieve information about the customer
     */
    public HasIncorrectUUID(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {

        bankProxy = newBankProxy;
        this.customerInterface = newCustomerInterface;

    }

    /**
     * enterUUID after registering or stating they have an account, the customer is prompted to enter their UUID here.
     * This method does not verify whether or not the UUID belongs to the customer or not. The user simply
     * enters their UUID and the method checks whether or not the bank has the UUID on file through the bankProxy.
     * They are given 5 attempts before the system closes. Upon a successful match, the state is changed to
     * HasCorrectUUID and the customer is prompted for their password.
     */

    public void enterUUID() {
        uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 0, 37);
        UUID newCustomerUUID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());

        if (bankProxy.requestCustomer(newCustomerUUID) == null) {
            /*if the customerHashTable does not contain the provided customer ID, the system will display a prompt
            * and ask them again if they would like to register. If they do not, the user is prompted for their UUID again
            * They are given 5 attempts total before the system exits.*/
            System.out.println("We could not find your ID, please try again.");
            boolean wantsToRegister = wantsToRegister();
            int uuidCounter = 1;

            while (bankProxy.requestCustomer(newCustomerUUID) == null && uuidCounter < 7) {
                if (uuidCounter == 6) {
                    System.out.println("All attempts exhausted. System exiting.");
                    System.exit(1);
                }
                if (wantsToRegister) {
                    customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
                    customerInterface.hasAccount(false);
                } else if (uuidCounter < 6) {
                    System.out.println(uuidCounter + " attempts remaining of 5. Please try again.");
                    newCustomerUUID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
                }
                uuidCounter++;
            }
            customerInterface.setCustomerInterfaceState(customerInterface.hasIncorrectUUID);
        }

        customerInterface.setCustomerInterfaceState(customerInterface.hasIncorrectUUID);

    }
/**
 * enterPassword not allowed in this state
 * */
@Override
public void enterPassword() {
        System.out.println("You must enter your UUID first.");
        customerInterface.enterUUID();
    }
/**
 * hasAccount redirects the customer based on current state
 * */
@Override
public void hasAccount(boolean isRegistered) {

        if (isRegistered) {
            customerInterface.setCustomerInterfaceState(customerInterface.hasIncorrectUUID);
            customerInterface.enterUUID();
        } else
            customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
        customerInterface.hasAccount(false);

    }
/**
 * logOff saves bank data to file, changes current state to LoggedOff, and brings up the first menu
 * */
@Override
public void logOff() {
    System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        customerInterface.hasAccount(false);
    }

    /**
     * requestInformation not allowed in this state
     * */
    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }
/**
 * startTransaction not allowed in this state
 * */
@Override
public void startTransaction() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }
/**
 * addAccount not allowed in this state
 * */
@Override
public void addAccount() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }
/**
 * wantsToRegister on incorrect UUID enter, customer is asked whether they would like to register and redirected appropriately
 *
 * @return true if they want to register, false otherwise
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
            customerInterface.logOff();
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }
}
