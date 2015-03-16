package bank_interface;

import bank_package.CreditReport;
import bank_package.Customer;
import bank_package.RealBank;
import utility.uScanner;

class HasNoAccount implements CustomerInterfaceState {

    private static RealBank bank;
    private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
    private final uScanner AGE_SCANNER = new uScanner("Please enter your age: ", 14, 99);
    private final uScanner NUM_LATE_PAYMENTS_SCANNER = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private final uScanner CREDIT_INQUIRIES_NUMBER = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private final uScanner CREDIT_BALANCE_SCANNER = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000);
    private final uScanner CREDIT_HISTORY_SCANNER = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private final uScanner CREDIT_LIMIT_SCANNER = new uScanner("Please enter your total credit limit.", -1, 2000000000);
    private final CustomerInterface customerInterface;

    public HasNoAccount(CustomerInterface customerInterface, RealBank bank) {
        this.customerInterface = customerInterface;
        HasNoAccount.bank = bank;
    }


    @Override
    public void enterUUID() {
        System.out.println("You do not have an account with us yet.");
    }

    @Override
    public void enterPassword() {
        System.out.println("You must register first.");
    }

    /*@hasAccount
   *
   * This method asks the user whether they would like to register or not, accepting a non-case-sensitive
   * yes for true and a non-case-sensitive no as false
   *
   * @param void: not required
   * @return boolean: returns user's answer in boolean form*/
    @Override
    public void hasAccount(boolean isRegistered) {
        if (!isRegistered) {
            registerNewCustomer();
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
        } else {
            System.out.println("You are already registered.");
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
        }
    }

    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        this.customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);

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


    void registerNewCustomer() {

        boolean wantsRegister = wantsToRegister();
         /*if the customer does NOT have an account and the customer WANTS to register, the new customer will be registered
         * the current newCustomerID of the instance will be set to the newly registered customer's UUID and the new customer is
         * added to the instance's customerHashTable*/

        if (wantsRegister) {
            Customer newCustomer = getNewCustomerInformation();

            System.out.println("Congratulations, " + newCustomer.getName() + "! You have successfully registered.\nYour new Customer ID is " +
                    newCustomer.getUUID() + ". DO NOT LOSE THIS!\nYour password is " + newCustomer.getPASSWORD() + ". You may now log in and experience everything " +
                    "we have to offer!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            customerInterface.getBANK().addCustomer(newCustomer);
            customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
            customerInterface.setCustomerUUID(newCustomer.getUUID());
        } else {
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
            customerInterface.hasAccount(false);
        }
    }


    private Customer getNewCustomerInformation() {
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

        Customer newCustomer = new Customer(tempName, tempAge, tempPassword, tempCreditReport);
        bank.addCustomer(newCustomer);
        return newCustomer;
    }

    /*@hasAccount
   *
   * This method asks the user whether they have an account or not, accepting a non-case-sensitive yes for true and
   * a non-case-sensitive no as false
   *
   * @param void: not required
   * @return boolean: returns user's answer in boolean form. will ALWAYS return true or false, the method calls itself
   * recursively until the user can enter a valid response.*/
   /* private boolean hasAccount() {
        String answer = this.HAVE_ACCOUNT_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES"))
            return true;

        else if (answer.equalsIgnoreCase("NO"))
            return false;

        else if (answer.equalsIgnoreCase("RETURN")) {
            getInstance(BANK);
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else if(answer.equals(dataIO.hiding)){
            dataIO.printAllCustomerInformation();
        }
        else
            System.out.println("Incorrect response.");

        return hasAccount();

    }*/


    /*@fillCreditReportInformation
    * This method is used when creating a new customer. This method is only called if the new customer is older than 17 years
    * old. They are requested to fill in their credit information.
    *
    * @param tempAge: age passed to the fill credit report. In reality, it is unnecessary because the user will never
    *                 see this method if their age is less than 18; the tempAge is printed purely for debugging and verification
    * @return new CreditReport: returns a new credit report for the customer, filled in with their provided information*/
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

    private boolean wantsToRegister() {
        final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES, NO, RETURN, EXIT", 2, 6);

        String answer = WANT_REGISTER_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES")) {
            return true;
        } else if (answer.equalsIgnoreCase("NO")) {
            customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        } else if (answer.equalsIgnoreCase("RETURN")) {
            this.customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }

    /*@getLatePaymentAmounts
    * This method is called by the fillCreditReportInformation method IFF the user had more than 0 late payments on record
    *
    * @param newNumberOfLatePayments: number of latePayments on customer's record
    * @return latePay.doubleGet(): returns a new double value with the total value of late payments on record*/
    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0, 2000000000);
        return latePay.doubleGet();
    }

}
