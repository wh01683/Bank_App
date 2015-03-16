package bank_interface;

import bank_package.BankProxy;
import utility.uScanner;

/**
 * Created by robert on 3/15/2015.
 */
class HasCorrectUUID implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    public HasCorrectUUID(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {
        bankProxy = newBankProxy;
        this.customerInterface = newCustomerInterface;


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

        System.out.println("Must enter your pass word first.");
        customerInterface.enterPassword();

    }

    @Override
    public void startTransaction() {
        System.out.println("Must enter your pass word first.");
        customerInterface.enterPassword();
    }

    @Override
    public void addAccount() {
        System.out.println("Must enter your pass word first.");
        customerInterface.enterPassword();
    }

    @Override
    public void enterUUID() {
        System.out.println("You have already entered your UUID, please enter your password.");
        customerInterface.enterPassword();
    }

    @Override
    public void enterPassword() {
        /*if they do have an account, they are requested to provide their UUID*/
        /*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*/
        final String realPass = bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getPASSWORD();
        /*the user is prompted for their password, which is stored in enteredPass*/
        uScanner PASSWORD_SCANNER = new uScanner("Please enter your password.", 4, 20);
        String enteredPass = PASSWORD_SCANNER.alphaNumericStringGet();
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
                    " on file, " + realPass + "\nYou may now access your bank account information!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            customerInterface.setCustomerInterfaceState(customerInterface.loggedIn);
            customerInterface.requestInformation();
        } else {
            System.out.println("Invalid password, exiting.");
            System.exit(1);
        }
    }
}
