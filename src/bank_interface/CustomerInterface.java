package bank_interface;

import bank_package.Bank;

import java.util.UUID;

/*This "CustomerInterface" class will be the class which primarily interacts with the customer. When the customer
* logs in to the bank system, they will be prompted for their CUSTOMER ID. This is their primary login name. The
* interface will prompt the user to create a new account if they do not have a CUSTOMER ID, or if they supplied a
* CUSTOMER ID which is not found in the hash table. Upon finding the correct customer associated with their CUSTOMER ID,
* the customer interface will prompt the user for their PASSWORD_SCANNER. Their PASSWORD_SCANNER is submitted for verification, comparing
* their input with the PASSWORD_SCANNER string of the CUSTOMER object associated with that CUSTOMER ID. Upon verification, the
* customer may then access the information of all accounts associated with that customer, balances, interest rates, etc.
* They may also apply for additional accounts (CD, IRA, Savings, etc) through this interface. They may also deposit/
* withdraw money from their accounts and transfer money between accounts.
*
*/
class CustomerInterface {
    private static Bank BANK;
    private static CustomerInterface SINGLETON_INSTANCE;
    private static UUID CustomerUUID;

    final CustomerInterfaceState loggedOff;
    final CustomerInterfaceState loggedIn;
    final CustomerInterfaceState hasAccount;
    final CustomerInterfaceState hasNoAccount;
    final CustomerInterfaceState hasCorrectUUID;
    final CustomerInterfaceState hasIncorrectUUID;


    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/

    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    private CustomerInterfaceState currentCustomerInterfaceState;

    private CustomerInterface(Bank newBank) {
        BANK = newBank;
        //uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 35, 37);

        loggedOff = new LoggedOff(this, newBank);
        hasAccount = new HasAccount(this, newBank);
        hasNoAccount = new HasNoAccount(this, newBank);
        hasCorrectUUID = new HasCorrectUUID(this, newBank);
        hasIncorrectUUID = new HasIncorrectUUID(this, newBank);
        loggedIn = new LoggedIn(this, newBank);

        currentCustomerInterfaceState = loggedOff;


    }

    public static CustomerInterface getInstance(Bank thisBank) {
        if (!(SINGLETON_INSTANCE == null)) {
            return SINGLETON_INSTANCE;
        } else
            return new CustomerInterface(thisBank);
    }

    public void START() {

        while (this.currentCustomerInterfaceState.equals(hasNoAccount) | this.currentCustomerInterfaceState.equals(loggedOff))
            this.hasAccount(false);
        while (this.currentCustomerInterfaceState.equals(hasAccount) | this.currentCustomerInterfaceState.equals(hasIncorrectUUID))
            this.enterUUID();
        while (this.currentCustomerInterfaceState.equals(hasCorrectUUID))
            this.enterPassword();

        while (!(this.currentCustomerInterfaceState.equals(loggedOff))) {
            this.loggedIn.requestInformation();
        }

        START();




    }

    public UUID getCustomerUUID() {
        return CustomerUUID;
    }

    public void setCustomerUUID(UUID newCustomerUUID) {
        CustomerUUID = newCustomerUUID;
    }

    public void setCustomerInterfaceState(CustomerInterfaceState newCustomerInterfaceState) {
        this.currentCustomerInterfaceState = newCustomerInterfaceState;
    }

    public void enterUUID() {
        currentCustomerInterfaceState.enterUUID();
    }

    public void enterPassword() {
        currentCustomerInterfaceState.enterPassword();
    }

    public void hasAccount(boolean hasAccount) {
        currentCustomerInterfaceState.hasAccount(hasAccount);
    }

    public void requestInformation() {
        currentCustomerInterfaceState.requestInformation();
    }

    public void startTransaction() {
        currentCustomerInterfaceState.startTransaction();
    }

    public void addAccount() {
        currentCustomerInterfaceState.addAccount();
    }

    public Bank getBANK() {
        return BANK;
    }



}
