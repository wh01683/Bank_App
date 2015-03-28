package bank_interface;


import bank_package.BankProxy;

public class LoggedOffState implements CustomerInterfaceState {

    // --Commented out by Inspection START (3/27/15 8:02 PM):
//    /*login or register prompt.*/
//    private static final uScanner logInOrRegister = new uScanner("----LOGIN--------------REGISTER-------------EXIT----", 4, 8);
// --Commented out by Inspection STOP (3/27/15 8:02 PM)
    private static BankProxy bankProxy;
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner AGE_SCANNER = new uScanner("Please enter your age: ", 14, 99);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner NUM_LATE_PAYMENTS_SCANNER = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner CREDIT_INQUIRIES_NUMBER = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner CREDIT_BALANCE_SCANNER = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner CREDIT_HISTORY_SCANNER = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner CREDIT_LIMIT_SCANNER = new uScanner("Please enter your total credit limit.", -1, 2000000000);
    // --Commented out by Inspection (3/27/15 8:02 PM):private final uScanner EMAIL_GET_SCANNER = new uScanner("Please enter your e-mail address. We totally won't sell it!", 0, 100);
    private final CustomerInterface customerInterface;
    /**
     * LoggedOffState creates a LoggedOffState state used by the customer interface class (default start state)
     *
     * @param newCustomerInterface customer interface instance passed through customer interface constructor. used to
     *                             set and update states of the customer interface
     */
    public LoggedOffState(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {
        this.customerInterface = newCustomerInterface;
        bankProxy = newBankProxy;
    }

    /**
     * enterUUID not allowed in this state
     * @param email
     */
    @Override
    public void enterEmail(String email) {
        customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
        customerInterface.enterEmail(email);
    }
/**
 * enterPassword not allowed in this state
 *
 * @param password*/
@Override
public void enterPassword(String password) {
    customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
    }


/**
 * logOff not allowed in this state, user is already logged off
 * */
@Override
    public void logOff() {
    customerInterface.saveBankDataToFile();
        System.out.println("You are already logged off.");

}
/**
 * requestInformation not allowed in this state
 * */
@Override
    public void requestInformation() {
        System.out.println("You must log in first.");

}
/**
 * startTransaction not allowed in this state
 * */
@Override
    public void startTransaction() {
        System.out.println("You must log in first.");

}
/**
 * addAccount not allowed in this state
 * */
@Override
    public void addAccount() {
        System.out.println("You must log in first.");

}


// --Commented out by Inspection START (3/27/15 8:02 PM):
//    /**
//     * used to send the new customer's information from the register new customer form in the gui to the customer interface.
//     * new customer information passed through the params and a new customer is created with the given information
//     *
//     * @param tempName name of the new customer passed from the gui form
//     * @param tempEmail email of the new customer passed from the gui form. Check for validity before passing
//     * @param tempAge age of the new customer passed from the gui form.
//     * @param tempPassword password of the new customer passed from the gui form. Check for validity before passing.
//     * @param tempCreditReport credit report of the new customer. created separately using the gui form.
//     *
//     *
//     */
//    private void setNewCustomerInformation(String tempName, String tempEmail, int tempAge, String tempPassword, CreditReport tempCreditReport) {
//
//        Customer newCustomer = new Customer(tempName, tempEmail, tempAge, tempPassword, tempCreditReport);
//        if (bankProxy.addCustomer(newCustomer)) {
//            System.out.printf("You have been successfully added, %s.\nYour registered Email is %s. Your" +
//                            " password is %s.\nYou may now log on and experience all the benefits we have to offer!\n",
//                    newCustomer.getName(), newCustomer.getEmail(), newCustomer.getPASSWORD());
//            customerInterface.setCustomer(newCustomer);
//        } else {
//            System.out.println("Cannot add customer. Logging off.");
//            customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
//
//        }
//    }
// --Commented out by Inspection STOP (3/27/15 8:02 PM)
}
