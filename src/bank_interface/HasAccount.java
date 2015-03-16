package bank_interface;

import bank_package.Bank;
import bank_package.Customer;
import bank_package.uScanner;

import java.util.UUID;

/**
 * Created by robert on 3/15/2015.
 */
public class HasAccount implements CustomerInterfaceState {

    CustomerInterface customerInterface;
    Bank bank;
    Customer customer;

    public HasAccount(CustomerInterface newCustomerInterface, Bank newBank) {

        this.customerInterface = newCustomerInterface;
        this.bank = newBank;

    }

    @Override
    public void enterUUID() {
        uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 35, 37);
        UUID newCustomerUUID;
        newCustomerUUID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
        if (bank.requestCustomer(newCustomerUUID).equals(null)) {
            /*if the customerHashTable does not contain the provided customer ID, the system will display a prompt
            * and ask them again if they would like to register. If they do not, the user is prompted for their UUID again
            * They are given 5 attempts total before the system exits.*/
            System.out.println("We could not find your ID, please try again.");
            boolean wantsToRegister = wantsToRegister();
            int uuidCounter = 1;

            while (bank.requestCustomer(newCustomerUUID).equals(null) && uuidCounter < 7) {
                if (uuidCounter == 6) {
                    System.out.println("All attempts exhausted. System exiting.");
                    System.exit(1);
                }
                if (wantsToRegister) {
                    customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
                } else if (uuidCounter < 6) {
                    System.out.println(uuidCounter + " attempts remaining of 5. Please try again.");
                    newCustomerUUID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
                }
                uuidCounter++;
            }
            customerInterface.setCustomerInterfaceState(customerInterface.hasIncorrectUUID);
        }

        customerInterface.setCustomerInterfaceState(customerInterface.hasCorrectUUID);

    }

    @Override
    public void enterPassword() {
        System.out.println("You must enter your UUID first.");
    }

    @Override
    public void hasAccount(boolean isRegistered) {
        System.out.println("You are already registered.");
    }

    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);

    }

    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
    }

    @Override
    public void startTransaction() {
        System.out.println("You must log in first.");
    }

    @Override
    public void addAccount() {
        System.out.println("You must log in first.");
    }

    private boolean wantsToRegister() {
        final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES, NO, RETURN, EXIT", 2, 6);
        String answer = WANT_REGISTER_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES")) {
            return true;
        } else if (answer.equalsIgnoreCase("NO")) {
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
        } else if (answer.equalsIgnoreCase("RETURN")) {
            this.customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }
}
