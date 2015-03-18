package bank_interface;

import bank_package.BankProxy;
import bank_package.RealBank;

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
    private static CustomerInterface SINGLETON_INSTANCE;
    private static UUID CustomerUUID;
    private static DataIO dataIO;
    private static RealBank realBank;
    private static BankProxy bankProxy;
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

    private CustomerInterface(RealBank newRealBank) {
        try {
            realBank = newRealBank;
            dataIO = new DataIO();
            dataIO.setRealBank(newRealBank);
            bankProxy = new BankProxy(realBank);
            //uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 35, 37);
        } catch (NullPointerException con) {
            System.out.printf("Null pointer caught in CustomerInterface : constructor");
            System.exit(1);
        }

        loggedOff = new LoggedOff(this);
        hasAccount = new HasAccount(this, bankProxy);
        hasNoAccount = new HasNoAccount(this, bankProxy);
        hasCorrectUUID = new HasCorrectUUID(this, bankProxy);
        hasIncorrectUUID = new HasIncorrectUUID(this, bankProxy);
        loggedIn = new LoggedIn(this, bankProxy, dataIO);

        currentCustomerInterfaceState = loggedOff;



    }

    /*@func getInstance: Singleton instance retriever.. ensures no two instances of customer interface exist at any
    * one time.
    * @param thisBank: RealBank to pass to the CustomerInterface constructor
    *
    * @return CustomerInterface: if the current SINGLETON_INSTANCE is null, a new instance is constructed and
    * returned. otherwise, this method will return the current instance.*/
    public static CustomerInterface getInstance(RealBank thisBank) {
        if (!(SINGLETON_INSTANCE == null)) {
            return SINGLETON_INSTANCE;
        } else {
            SINGLETON_INSTANCE = new CustomerInterface(thisBank);
            return SINGLETON_INSTANCE;
        }
    }

    /*@func START(): after the CustomerInterface is instantiated, the Start() function is called to get everything
    * moving in the right direction. the while loops call the correct methods depending on which state it is in.
    *
    * @param null
    *
    * @return void*/
    public void START() {

        try {
            dataIO.readAllBankDataFromFile();
            if (dataIO.getRealBank() != null) {
                realBank = dataIO.getRealBank();
                SINGLETON_INSTANCE = new CustomerInterface(realBank);

            } else {
                realBank = new RealBank("hello", 10, 10);
            }
        } catch (NullPointerException start) {
            System.out.printf("Null pointer caught in CustomerInterface : START()");
            System.exit(1);
        }


        while (this.currentCustomerInterfaceState.equals(hasNoAccount) | this.currentCustomerInterfaceState.equals(loggedOff)) {
            this.hasAccount(true);
        }
        while (!this.currentCustomerInterfaceState.equals(loggedOff)) {
            while (this.currentCustomerInterfaceState.equals(hasAccount) | this.currentCustomerInterfaceState.equals(hasIncorrectUUID)) {
                this.enterUUID();
            }
            while (this.currentCustomerInterfaceState.equals(hasCorrectUUID)) {
                this.enterPassword();
            }
        }

        /*dataIO.setRealBank(realBank);
        dataIO.saveAllBankDataToFile("DEFAULT");*/


    }


    public UUID getCustomerUUID() {
        if (!(CustomerUUID == null)) {
            return CustomerUUID;
        } else {
            System.out.printf("CustomerUUID in CustomerInterface is null. Returning null.");
            return null;
        }
    }

    public void setCustomerUUID(UUID newCustomerUUID) {
        try {
            CustomerUUID = newCustomerUUID;
        } catch (NullPointerException uuid) {
            System.out.printf("Null pointer caught in CustomerInterface : setCustomerUUID\nparameter cannot be null.");
        }
    }

    public void setCustomerInterfaceState(CustomerInterfaceState newCustomerInterfaceState) {
        this.currentCustomerInterfaceState = newCustomerInterfaceState;
    }

    public void logOff() {
        currentCustomerInterfaceState.logOff();
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

    public BankProxy getBankProxy() {
        return bankProxy;
    }

    public void saveBankDataToFile() {
        bankProxy.saveAllBankDataToFile();
    }

}
