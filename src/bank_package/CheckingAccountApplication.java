package bank_package;/*
 * 


 * FACADE CLASS
 * 
 * 
 * */

import java.util.Scanner;

public class CheckingAccountApplication {

    static Scanner in = new Scanner(System.in);
    private Bank _bank;
    private CreditReport _cred;
    private Customer _cust;
    private boolean approved;
    private MoBettaScanner name = new MoBettaScanner("Please enter your name: ", 2, 50);
    private MoBettaScanner age = new MoBettaScanner("Please enter your age: ", 14, 99);
    private MoBettaScanner latePayments = new MoBettaScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private MoBettaScanner startBalance = new MoBettaScanner("Please enter your starting balance: ", 1, 2000000000.0);
    private MoBettaScanner credInquiries = new MoBettaScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private MoBettaScanner credBalance = new MoBettaScanner("Please enter your current outstanding credit card balance.", -1, 2000000000.0);
    private MoBettaScanner credHistory = new MoBettaScanner("Please enter the length of your credit history in years: ", -1, 99);
    private MoBettaScanner credLim = new MoBettaScanner("Please enter your total credit limit.", -1, 2000000000.0);


    public CheckingAccountApplication() {

        String localName = name.stringGet();
        int localAge = age.intGet();
        double localBankBal = startBalance.doubleGet();
        this._cust = new Customer(localName, localAge);
        this._bank = new Bank(localBankBal);

        if (localAge > 17) {
            this._cred = getCredReport(localAge);
        } else if (localAge < 18)
            this._cred = new CreditReport(localAge);

        this.approved = decideApproved();

        in.close();

    }

    public void PrintScreenResult() {
        if (!this.approved)
            System.out.println("You have been declined, " + this._cust.getName() + ". We apologize.");
        else
            System.out.println("Congratulations " + this._cust.getName() + "! You have been approved.");
    }

    private boolean decideApproved() {
        //just random stuff here, no real world calculations involved
        if (this._cust.getName().equals("Robert")) return true;
        else if (this._cust.getName().equals("Phillip")) return true;
        else if (this._cred.getCreditScore() < 500 && this._cust.getChexSystemsScore() < 500) return false;
        else if (this._cust.getChexSystemsScore() > 700) return true;
        else if (this._cust.getChexSystemsScore() > 500 && this._bank.getBalance() > 1000) return true;
        else return true;

    }

    private CreditReport getCredReport(int tempAge) {

        int age = tempAge;
        System.out.println("Since you are " + tempAge + " years old, you must provide some credit information.");
        double credLimit = credLim.doubleGet();
        double accountBalance = credBalance.doubleGet();
        int lenCredHistory = credHistory.intGet();
        int latePaymentsOnRecord = latePayments.intGet();
        int recentCredInquiries = credInquiries.intGet();

        CreditReport tempcr = new CreditReport(age, latePaymentsOnRecord, recentCredInquiries, credLimit,
                accountBalance, lenCredHistory);


        return tempcr;

    }


}
