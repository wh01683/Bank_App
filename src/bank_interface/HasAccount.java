package bank_interface;

import bank_package.BankProxy;
import utility.uScanner;

import java.util.UUID;

/**
 * Created by robert on 3/15/2015.
 */
class HasAccount implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    public HasAccount(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {

        this.customerInterface = newCustomerInterface;
        bankProxy = newBankProxy;

    }

    @Override
    public void enterUUID() {
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

    }

    @Override
    public void enterPassword() {
        System.out.println("You must enter your UUID first.");
        customerInterface.enterUUID();
    }

    @Override
    public void hasAccount(boolean isRegistered) {
        System.out.println("You are already registered.");
    }

    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        customerInterface.hasAccount(false);

    }

    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }

    @Override
    public void startTransaction() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }

    @Override
    public void addAccount() {
        System.out.println("You must log in first.");
        customerInterface.enterUUID();
    }

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
