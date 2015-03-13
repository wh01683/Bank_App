package bank_package;


import java.util.Hashtable;
import java.util.UUID;

/*This "CustomerInterface" class will be the class which primarily interacts with the customer. When the customer
* logs in to the bank system, they will be prompted for their CUSTOMER ID. This is their primary login name. The
* interface will prompt the user to create a new account if they do not have a CUSTOMER ID, or if they supplied a
* CUSTOMER ID which is not found in the hash table. Upon finding the correct customer associated with their CUSTOMER ID,
* the customer interface will prompt the user for their password. Their password is submitted for verification, comparing
* their input with the password string of the CUSTOMER object associated with that CUSTOMER ID. Upon verification, the
* customer may then access the information of all accounts associated with that customer, balances, interest rates, etc.
* They may also apply for additional accounts (CD, IRA, Savings, etc) through this interface. They may also deposit/
* withdraw money from their accounts and transfer money between accounts.
*
* ToDo: make a Customer UUID scanner, re-prompt customer for UUID if they enter a UUID not found in the customerHashtable*/

public class CustomerInterface {

    private static Customer cust;
    private static Bank bank;
    private static Hashtable<Integer, Customer> customerHashtable;
    private static CustomerInterface ourInstance;
    private static boolean LOGGED_IN = false;
    private uScanner nameS = new uScanner("Please enter your name: ", 2, 50);
    private uScanner ageS = new uScanner("Please enter your age: ", 14, 99);
    private uScanner latePayments = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private uScanner credInquiries = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private uScanner credBalance = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000.0);
    private uScanner credHistory = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private uScanner credLim = new uScanner("Please enter your total credit limit.", -1.0, 2000000000.0);
    private uScanner uuid = new uScanner("Please enter the Customer ID you received when you registered.", 0, 16);
    private uScanner password = new uScanner("Please enter your password", 4, 16);

    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/
    private CustomerInterface(UUID newCustID, Bank newBank) {
        bank = newBank;
        customerHashtable = bank.getCustomerTable();
        String realPass;
        String enteredPass;

        if (!customerHashtable.containsKey(newCustID.hashCode())) {
            System.out.println("We could not find your ID, please try again.");
            newCustID = UUID.fromString(uuid.alphaNumericStringGet());
        }
        cust = customerHashtable.get(newCustID.hashCode());
        realPass = cust.getPASSWORD();
        enteredPass = password.stringGet();
        int attempts = 0;

        while (!enteredPass.equals(realPass) && attempts < 6) {
            LOGGED_IN = false;
            if (attempts == 5) {
                System.out.println("Maximum attempts reached. Exiting.");
                System.exit(1);
            } else {
                System.out.println("Invalid password. Try Again. " + attempts + " attempts remaining.");
                attempts++;
                enteredPass = password.stringGet();
            }
        }

        if (realPass.equals(enteredPass)) LOGGED_IN = true;

    }

    public static CustomerInterface getInstance(UUID newCustomerID, Bank thisBank) {
        if (!(ourInstance == null))
            return ourInstance;
        else return new CustomerInterface(newCustomerID, thisBank);
    }

    private Customer registerNewCustomer() {
        String tempName = nameS.stringGet();
        int tempAge = ageS.intGet();
        CreditReport tempCreditReport;
        if (tempAge < 17)
            tempCreditReport = new CreditReport(0);
        else
            tempCreditReport = fillCredReportInformation(tempAge);
        ChexSystems tempScore = new ChexSystems();
        String tempPassword = password.stringGet();

        return new Customer(tempName, tempAge, tempPassword, tempCreditReport, tempScore);


    }

    private CreditReport fillCredReportInformation(int tempAge) {
        System.out.println("Since you are " + tempAge + " years old, you must provide some credit information.");
        double credLimit = credLim.doubleGet();
        double amountOfLatePayments = 0;
        double accountBalance = credBalance.doubleGet();
        int lenCredHistory = credHistory.intGet();
        int latePaymentsOnRecord = latePayments.intGet();
        if (latePaymentsOnRecord > 0)
            amountOfLatePayments = getLatePaymentAmounts(latePaymentsOnRecord);
        int recentCredInquiries = credInquiries.intGet();

        return new CreditReport(tempAge, latePaymentsOnRecord, amountOfLatePayments, recentCredInquiries, credLimit,
                accountBalance, lenCredHistory);
    }

    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0.0, 2000000000.0);
        return latePay.doubleGet();
    }


}