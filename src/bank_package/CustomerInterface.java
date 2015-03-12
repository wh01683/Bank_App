package bank_package;


import java.util.Hashtable;
import java.util.UUID;

public class CustomerInterface {

    private static Customer cust;
    private static Bank bank;
    private static Hashtable<Integer, Customer> customerHashtable;
    private static CustomerInterface ourInstance;
    private uScanner nameS = new uScanner("Please enter your name: ", 2, 50);
    private uScanner ageS = new uScanner("Please enter your age: ", 14, 99);
    private uScanner latePayments = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private uScanner credInquiries = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private uScanner credBalance = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000.0);
    private uScanner credHistory = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private uScanner credLim = new uScanner("Please enter your total credit limit.", -1.0, 2000000000.0);

    private CustomerInterface(UUID newCustID, Bank newBank) {
        bank = newBank;
        customerHashtable = bank.getCustomerTable();

        if (!customerHashtable.containsKey(newCustID.hashCode())) {
            /*prompt user to created a new account*/
        }
        cust = customerHashtable.get(newCustID.hashCode());
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

        return new Customer(tempName, tempAge, tempCreditReport, tempScore);


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
