package bank_interface;

import bank_package.BankProxy;
import bank_package.Customer;
import bank_package.RealBank;

import java.util.UUID;

/**
 * @author William Robert Howerton III
 * @version 2.1.5
 *
 * This "CustomerInterface" class will be the class which primarily interacts with the customer. When the customer
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


public class CustomerInterface {
    private static CustomerInterface SINGLETON_INSTANCE;
    private static UUID CustomerUUID;
    private static DataIO dataIO;
    private static RealBank realBank;
    private static BankProxy bankProxy;
    private static Customer customer;
    final CustomerInterfaceState loggedOffState;
    final CustomerInterfaceState loggedInState;
    final CustomerInterfaceState processUsernameState;
    final CustomerInterfaceState processPasswordState;
    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/
    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    private CustomerInterfaceState currentCustomerInterfaceState;


    /**
     * CustomerInterface creates a new customer interface based around the real bank object passed. user information is
     * retrieved from the real bank is used to verify the user retrieved for the user upon logging in
     *
     * @param newRealBank a RealBank object to build the Customer Interface around
     */
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

        loggedOffState = new LoggedOffState(this);
        loggedInState = new LoggedInState(this, bankProxy, dataIO);
        processUsernameState = new ProcessUsernameState(this, bankProxy);
        processPasswordState = new ProcessUsernameState(this, bankProxy);

        currentCustomerInterfaceState = loggedOffState;



    }

    /** getInstance: Singleton instance retriever.. ensures no two instances of customer interface exist at any
     *                one time.
     *
     * @param thisBank RealBank to pass to the CustomerInterface constructor
     *
     * @return SINGLETON_INSTANCE:
     *                      if the current SINGLETON_INSTANCE is null, a new instance is constructed and
     *                       returned. otherwise, this method will return the current instance.
     */
    public static CustomerInterface getInstance(RealBank thisBank) {
        if (!(SINGLETON_INSTANCE == null)) {
            return SINGLETON_INSTANCE;
        } else {
            SINGLETON_INSTANCE = new CustomerInterface(thisBank);
            return SINGLETON_INSTANCE;
        }
    }

    /** START(): after the CustomerInterface is instantiated, the Start() function is called to get everything
     * moving in the right direction. the while loops call the correct methods depending on which state it is in.
    *
     * */
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


        while (this.currentCustomerInterfaceState.equals(loggedOffState)) {
            this.startLoginProcess(true);
        }
        while (!this.currentCustomerInterfaceState.equals(loggedOffState)) {
            while (this.currentCustomerInterfaceState.equals(processUsernameState)) {
                this.enterUUID();
            }
            while (this.currentCustomerInterfaceState.equals(processPasswordState)) {
                this.enterPassword();
            }
        }
    }


    /** getCustomer: returns UUID of the customer currently logged in
     *
     * @return returns the UUID object of the customer currently logged in
     * */
    public Customer getCustomer() {
        if (!(customer == null)) {
            return customer;
        } else {
            System.out.printf("Customer not set, returning null");
            return null;
        }
    }

    /** setCustomerUUID(UUID newCustomerUUID) updates the UUID of the customer interface
     *
     * @param newCustomer new customer to which this instance of the CustomerInterface belongs
     * */
    public void setCustomer(Customer newCustomer) {
        try {
            customer = newCustomer;
        } catch (NullPointerException uuid) {
            System.out.printf("Null pointer caught in CustomerInterface : setCustomerUUID\nparameter cannot be null.");
        }
    }

    /** setCustomerInterfaceState utilizing the State design pattern, this method updates the current Customer Interface state with the
     *                                 new state.
     *
     * @param newCustomerInterfaceState new state passed to Customer Interface
     *
     * */
    public void setCustomerInterfaceState(CustomerInterfaceState newCustomerInterfaceState) {
        this.currentCustomerInterfaceState = newCustomerInterfaceState;
    }

    /** logOff: method logOff() is called on current state of the customer interface
     *               disconnects the user from the user interface.
     * */
    public void logOff() {
        currentCustomerInterfaceState.logOff();
    }

    /**enterUUID: method enterUUID() is called on current state of the customer interface
     *                  prompts user to enter their provided UUID
     *                  Note: function changes based on current state
     * */
    public void enterUUID() {
        currentCustomerInterfaceState.enterEmail();
    }

    /** enterPassword called on the current state of the customer interface
     *                     prompts user to enter their password
     *                     Note: function changes based on current state
     * */
    public void enterPassword() {
        currentCustomerInterfaceState.enterPassword();
    }

    /** startLoginProcess: method startLoginProcess() is called on current state of the customer interface
     *                   essentially initializes the registration process
     *                   Note: function changes based on current state
     *
     * @param processUsernameState passing false will ask the user if they would like to register, passing true will automatically
     *                   direct them to fill out their customer information
     * */
    public void startLoginProcess(boolean processUsernameState) {
        currentCustomerInterfaceState.startLoginProcess(processUsernameState);
    }

    /** requestInformation start of the login process; prompts user for desired actions to take during the login
     *                          process
     */
    public void requestInformation() {
        currentCustomerInterfaceState.requestInformation();
    }

    public void startTransaction() {
        currentCustomerInterfaceState.startTransaction();
    }

    /**addAccount initiates the account adding process
     *
     * */
    public void addAccount() {
        currentCustomerInterfaceState.addAccount();
    }

    /** getBankProxy returns current bankProxy of the customer interface.
     *@return current bank proxy object
     * */
    public BankProxy getBankProxy() {
        return bankProxy;
    }

    /** saveBankDataToFile calls on the bank proxy object to save all information to file through object output stream
     *
     * */
    public void saveBankDataToFile() {
        bankProxy.saveAllBankDataToFile();
    }


}
