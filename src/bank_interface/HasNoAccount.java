package bank_interface;

import bank_package.BankProxy;
import bank_package.CreditReport;
import bank_package.Customer;
import utility.uScanner;

public class HasNoAccount implements CustomerInterfaceState {

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
     * HasNoAccount creates the HasNoAccount state, used by the CustomerInterface class. Represents the state during which
     * the customer may choose to either register or log in under an existing profile
     *
     * @param newBankProxy      BankProxy object passed from the CustomerInterface constructor. used to access customer specific
     *                          data used for customer addition
     * @param customerInterface an instance of the customerInterface, used to set states when the state changes.
     */
    public HasNoAccount(CustomerInterface customerInterface, BankProxy newBankProxy) {
        this.customerInterface = customerInterface;
        bankProxy = newBankProxy;

    }


    @Override
    public void enterUUID() {
        System.out.println("You do not have an account with us yet.");
    }

    @Override
    public void enterPassword() {
        System.out.println("You must register first.");
    }

    /**startLoginProcess This method asks the user whether they would like to register or not, accepting a non-case-sensitive
     *            yes for true and a non-case-sensitive no as false
   *
     * @param wantsToRegister boolean is false by default, only true if the user already stated they want to register,
     *                        thereby avoiding asking them twice.
     *                        */
    @Override
    public void startLoginProcess(boolean wantsToRegister) {
        if (!wantsToRegister) {
            registerNewCustomer();
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
            customerInterface.enterUUID();
        } else {
            getNewCustomerInformation();
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
            customerInterface.enterUUID();
        }
    }

    /**
     * saves current bank data, logs the user out of the system, and changes the state to LoggedOff
     * */
    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        this.customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        customerInterface.hasAccount(false);

    }
    /**
     * not allowed in this state
     * */
    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
    }
/**
 * not allowed in this state
 * */
@Override
    public void startTransaction() {
        System.out.println("You must log in first.");
    }
/**
 * not allowed in this state
 * */
@Override
    public void addAccount() {
        System.out.println("You must log in first.");

    }

/**
 * registerNewCustomer asks the user if they would like to register with the bank and redirects them based on their response
 * */
void registerNewCustomer() {

        boolean wantsRegister = wantsToRegister();
         /*if the customer does NOT have an account and the customer WANTS to register, the new customer will be registered
         * the current newCustomerID of the instance will be set to the newly registered customer's UUID and the new customer is
         * added to the instance's customerHashTable*/

        if (wantsRegister) {
            getNewCustomerInformation();
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
        } else {
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
            customerInterface.hasAccount(false);
        }
    }

/**
 * getNewCustomerInformation method invoked to register a new customer using information input by the user to pass on
 *                           to the Customer constructor. Prompts user for their name, email, custom password, and invokes
 *                           the fillCreditReport method if the user is at least 18 years old. The password is checked for
 *                           strength using a local instantiation of the PasswordChecker class and the email is checked
 *                           for validity using a local instantiation of the EmailValidator class. After creating the new
 *                           customer, the static CustomerInterface-wide UUID is set to that of the new customer to be
 *                           used for information retrieval later.
 *                           */
private void getNewCustomerInformation() {
        PasswordChecker passwordChecker = new PasswordChecker();
        EmailValidator emailValidator = new EmailValidator();
        String tempName = NAME_SCANNER.stringGet();
        int tempAge = AGE_SCANNER.intGet();
        CreditReport tempCreditReport;
        if (tempAge < 18) /*if the user is younger than 18, they do not have a credit report so a default credit report
        is created for the user with all values initiated at 0*/
            tempCreditReport = new CreditReport(0);
        else
            tempCreditReport = fillCredReportInformation(tempAge);
        final uScanner NEW_PASSWORD_SCANNER = new uScanner("Please enter your new custom password for your account.", 5, 20);
        String tempPassword = NEW_PASSWORD_SCANNER.alphaNumericStringGet();
        while (!(passwordChecker.checkStringPassWithASCIIValues(tempPassword))) {
            System.out.printf("Password not strong enough.\n");
            tempPassword = NEW_PASSWORD_SCANNER.alphaNumericStringGet();
        }
        String tempEmail = EMAIL_GET_SCANNER.alphaNumericStringGet();
        while (!(emailValidator.validate(tempEmail))) {
            System.out.println("Not a valid e-mail address.\n");
            tempEmail = EMAIL_GET_SCANNER.alphaNumericStringGet();
        }
        Customer newCustomer = new Customer(tempName, tempEmail, tempAge, tempPassword, tempCreditReport);
        if (bankProxy.addCustomer(newCustomer)) {
            System.out.printf("You have been successfully added, %s.\nYour new Customer UUID is %s. DO NOT LOSE THIS! Your" +
                            " password is %s.\nYou may now log on and experience all the benefits we have to offer!\n",
                    newCustomer.getName(), newCustomer.getUUID(), newCustomer.getPASSWORD());
            customerInterface.setCustomerUUID(newCustomer.getUUID());
        } else {
            System.out.println("This UUID is already in our system. Logging off.");
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
            customerInterface.hasAccount(false);
        }
    }

    /**
     * fillCreditReportInformation This method is used when creating a new customer. This method is only called if the new customer
     *                             is older than 17 years old. They are requested to fill in their credit information.
    *
    * @param tempAge: age passed to the fill credit report. In reality, it is unnecessary because the user will never
    *                 see this method if their age is less than 18; the tempAge is printed purely for debugging and verification
     *
     * @return new CreditReport returns a new credit report for the customer, filled in with their provided information
     * */
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
     *                 exit the system entirely, or register.
     *
     * @return true if the customer would like to register.*/
    private boolean wantsToRegister() {
        final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES, NO, LOGOFF, EXIT", 2, 6);

        String answer = WANT_REGISTER_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES")) {
            return true;
        } else if (answer.equalsIgnoreCase("NO")) {
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
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
     *                       0 late payments on record
    *
    * @param newNumberOfLatePayments: number of latePayments on customer's record
     *
     * @return latePay.doubleGet() returns a new double value with the total value of late payments on record
     * */
    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0, 2000000000);
        return latePay.doubleGet();
    }

}
