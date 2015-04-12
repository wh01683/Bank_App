package bank_interface;

import bank_package.BankProxy;
import bank_package.Customer;
import bank_package.RealBank;

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
    private static DataIO dataIO;
    private static RealBank realBank;
    private static BankProxy bankProxy;
    private static Customer customer;
    final CustomerInterfaceState loggedOffState;
    final CustomerInterfaceState loggedInState;
    final CustomerInterfaceState processUsernameState;
    final CustomerInterfaceState processPasswordState;

    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    private CustomerInterfaceState currentCustomerInterfaceState;


    /**
     * creates a new customer interface based around the real bank object passed. user information is
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

        loggedOffState = new LoggedOffState(this, bankProxy);
        loggedInState = new LoggedInState(this, bankProxy, dataIO);
        processUsernameState = new ProcessUsernameState(this, bankProxy);
        processPasswordState = new ProcessPasswordState(this, bankProxy);

        currentCustomerInterfaceState = loggedOffState;



    }

    /** Singleton instance retriever.. ensures no two instances of customer interface exist at any
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

    /** after the CustomerInterface is instantiated, the Start() function is called to load the realBank object from file
     * and instantiate the Singleton
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

    }


    /** returns the customer object of the customer currently logged in
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

    /** updates the Customer object held by the customer interface
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

    /** utilizing the State design pattern, this method updates the current Customer Interface state with the
     *                                 new state.
     *
     * @param newCustomerInterfaceState new state passed to Customer Interface
     *
     * */
    public void setCustomerInterfaceState(CustomerInterfaceState newCustomerInterfaceState) {
        this.currentCustomerInterfaceState = newCustomerInterfaceState;
    }

    /** called on current state of the customer interface
     *               disconnects the user from the user interface.
     * */
    public void logOff() {
        currentCustomerInterfaceState.logOff();
    }

    /** processes the user's ENTERED email
     *
     * @param email email entered by the user in the gui form, used to log the customer in.
     * @return returns feedback to the user based on the outcome of the email processing
     * */
    public String enterEmail(String email) {
        return currentCustomerInterfaceState.enterEmail(email);
    }

    /** calls on the bank proxy object to save all information to file through object output stream
     * */
    public void saveBankDataToFile() {
        bankProxy.saveAllBankDataToFile();
    }

    /**
     * removes the account associated with the account number provided
     *
     * @param accountNumber account number of the account to be removed
     * @return returns feedback based on the outcome of the account removal process
     */
    public String removeAccount(Integer accountNumber) {
        return currentCustomerInterfaceState.removeAccount(accountNumber);

    }

    /**
     * allows the logged in customer to add a new bank account under their name by passing their request
     * (as a string) to the AccountFactory object. If a null account is returned by the Account Factory,
     * the customer did not qualify for the account and nothing is added. Otherwise, the Account Factory returns
     * a new account object created with their desired opening balance. Once the account is created, the
     * information of the newly created account is printed back to the customer for confirmation. After the
     * new account is added, the customer is given the opportunity to continue adding more accounts or return
     * to the previous menu or log off. If at any time the method does not recognize an input string, the
     * method is called recursively to give the opportunity to correct their input.
     *
     * @param accountRequest String representation of the account type desired by the customer
     * @param openingBalance desired opening balance for the new account
     * @return returns feedback to the user based on the add account process
     */
    public String addAccount(String accountRequest, double openingBalance) {
        return currentCustomerInterfaceState.addAccount(accountRequest, openingBalance);
    }

    /**
     * method to process transactions requested by the user. A request in string form is passed through
     * as a param. During each transaction choice, the method handles cases such as: owner does not own
     * account, invalid account number, depositing too large of an amount, withdrawing too large, and attempting
     * to withdraw or deposit negative amounts
     * <p/>
     * Transfer requests also handle cases where the transfer TO and transfer FROM account numbers are
     * the same, attempting to initialize transfers while only 1 account is owned, and cases where one
     * half of the transaction is valid (method will undo the other half)
     *
     * @param transactionChoice String version of the user's transaction choice (transfer, withdraw, deposit)
     * @param accountFromNumber Account number of the account to take money FROM
     * @param accountToNumber   Account number of the accoun tot put money IN
     * @param withdrawAmount    Amount of money to withdraw. For transfers, this will equal deposit
     * @param depositAmount     Amount of money to deposit. For transfers, this will equal withdraw
     * @return returns feedback to the user depending on the outcome of the transaction process
     */
    public String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber,
                                   double withdrawAmount, double depositAmount) {
        return currentCustomerInterfaceState.startTransaction(transactionChoice, accountFromNumber, accountToNumber,
                withdrawAmount, depositAmount);
    }

}
