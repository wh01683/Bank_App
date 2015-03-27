package bank_interface;


import bank_package.BankProxy;
import bank_package.CreditReport;
import bank_package.Customer;
import utility.uScanner;

public class LoggedOffState implements CustomerInterfaceState {

    /*login or register prompt.*/
    private static final uScanner logInOrRegister = new uScanner("----LOGIN--------------REGISTER-------------EXIT----", 4, 8);
    private static BankProxy bankProxy;
    private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
    private final uScanner AGE_SCANNER = new uScanner("Please enter your age: ", 14, 99);
    private final uScanner NUM_LATE_PAYMENTS_SCANNER = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private final uScanner CREDIT_INQUIRIES_NUMBER = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private final uScanner CREDIT_BALANCE_SCANNER = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000);
    private final uScanner CREDIT_HISTORY_SCANNER = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private final uScanner CREDIT_LIMIT_SCANNER = new uScanner("Please enter your total credit limit.", -1, 2000000000);
    private final uScanner EMAIL_GET_SCANNER = new uScanner("Please enter your e-mail address. We totally won't sell it!", 0, 100);
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
     */
    @Override
    public void enterEmail() {
        System.out.println("No.");
        customerInterface.startLoginProcess(false);
    }
/**
 * enterPassword not allowed in this state
 * */
@Override
    public void enterPassword() {
        System.out.println("You must enter your UUID first.");
    customerInterface.startLoginProcess(false);
    }

    /**startLoginProcess: this is the entry point to the program they simply type whether they want to login or register,
  *           and they are redirected to the appropriate state and the appropriate handling method based on what they choose
  *
  *@param isRegistered: default false.
  *
  **/
 @Override
 public void startLoginProcess(boolean isRegistered) {

        String loginOrRegister = logInOrRegister.stringGet();


        if (loginOrRegister.equalsIgnoreCase("REGISTER")) {
            //registerNewCustomer(true);
        } else if (loginOrRegister.equalsIgnoreCase("LOGIN")) {
            customerInterface.setCustomerInterfaceState(customerInterface.processUsernameState);
            customerInterface.enterEmail();
        } else if (loginOrRegister.equalsIgnoreCase("EXIT")) {
            System.out.printf("Exiting..");
            System.exit(0);
        } else {
            System.out.printf("Request could not be processed.");
            startLoginProcess(false);
        }


    }
/**
 * logOff not allowed in this state, user is already logged off
 * */
@Override
    public void logOff() {
    customerInterface.saveBankDataToFile();
        System.out.println("You are already logged off.");
    customerInterface.startLoginProcess(false);
    }
/**
 * requestInformation not allowed in this state
 * */
@Override
    public void requestInformation() {
        System.out.println("You must log in first.");
    customerInterface.startLoginProcess(false);
    }
/**
 * startTransaction not allowed in this state
 * */
@Override
    public void startTransaction() {
        System.out.println("You must log in first.");
    customerInterface.startLoginProcess(false);
    }
/**
 * addAccount not allowed in this state
 * */
@Override
    public void addAccount() {
        System.out.println("You must log in first.");
    customerInterface.startLoginProcess(false);
    }


    /**
     * getNewCustomerInformation method invoked to register a new customer using information input by the user to pass on
     * to the Customer constructor. Prompts user for their name, email, custom password, and invokes
     * the fillCreditReport method if the user is at least 18 years old. The password is checked for
     * strength using a local instantiation of the PasswordChecker class and the email is checked
     * for validity using a local instantiation of the EmailValidator class. After creating the new
     * customer, the static CustomerInterface-wide UUID is set to that of the new customer to be
     * used for information retrieval later.
     */
    private void getNewCustomerInformation(String tempName, String tempEmail, int tempAge, String tempPassword, CreditReport tempCreditReport) {

        Customer newCustomer = new Customer(tempName, tempEmail, tempAge, tempPassword, tempCreditReport);
        if (bankProxy.addCustomer(newCustomer)) {
            System.out.printf("You have been successfully added, %s.\nYour registered Email is %s. Your" +
                            " password is %s.\nYou may now log on and experience all the benefits we have to offer!\n",
                    newCustomer.getName(), newCustomer.getEmail(), newCustomer.getPASSWORD());
            customerInterface.setCustomer(newCustomer);
        } else {
            System.out.println("Cannot add customer. Logging off.");
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
            customerInterface.startLoginProcess(false);
        }
    }

    /**
     * fillCreditReportInformation This method is used when creating a new customer. This method is only called if the new customer
     * is older than 17 years old. They are requested to fill in their credit information.
     *
     * @param tempAge: age passed to the fill credit report. In reality, it is unnecessary because the user will never
     *                 see this method if their age is less than 18; the tempAge is printed purely for debugging and verification
     * @return new CreditReport returns a new credit report for the customer, filled in with their provided information
     */
    CreditReport fillCredReportInformation(int tempAge) {
        double amountOfLatePayments = 0;

        System.out.println("Since you are " + tempAge + " years old, you must provide some credit information.");
        double credLimit = CREDIT_LIMIT_SCANNER.doubleGet();
        double accountBalance = CREDIT_BALANCE_SCANNER.doubleGet();
        int lenCredHistory = CREDIT_HISTORY_SCANNER.intGet();
        int latePaymentsOnRecord = NUM_LATE_PAYMENTS_SCANNER.intGet();
        if (latePaymentsOnRecord > 0)
            amountOfLatePayments = getLatePaymentAmounts(latePaymentsOnRecord);
        int recentCredInquiries = CREDIT_INQUIRIES_NUMBER.intGet();

        return new CreditReport(tempAge, latePaymentsOnRecord, amountOfLatePayments, recentCredInquiries, credLimit,
                accountBalance, lenCredHistory);
    }

    /**
     * wantsToRegister asks the user if they want to register or not, whilst giving them the option to return, logoff,
     * exit the system entirely, or register.
     *
     * @return true if the customer would like to register.
     */
    private boolean wantsToRegister() {
        final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES, NO, LOGOFF, EXIT", 2, 6);

        String answer = WANT_REGISTER_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES")) {
            return true;
        } else if (answer.equalsIgnoreCase("NO")) {
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
        } else if (answer.equalsIgnoreCase("LOGOFF")) {
            customerInterface.logOff();
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }

    /**
     * getLatePaymentAmounts This method is called by the fillCreditReportInformation method IFF the user had more than
     * 0 late payments on record
     *
     * @param newNumberOfLatePayments: number of latePayments on customer's record
     * @return latePay.doubleGet() returns a new double value with the total value of late payments on record
     */
    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0, 2000000000);
        return latePay.doubleGet();
    }


}
