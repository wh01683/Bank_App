package bank_package;
/*
 * 



 * FACADE CLASS
 * 
 * 
 * */

import java.util.Scanner;
public class CheckingAccountApplication {

    static Scanner in = new Scanner(System.in);
    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public CheckingAccountApplication(Customer newCustomer) {

        this.approved = decideApproved();


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
        else if (this._cust.getCreditScore() < 500 && this._cust.getChexSystemsScore() < 500) return false;
        else if (this._cust.getChexSystemsScore() > 700) return true;
        else if (this._cust.getChexSystemsScore() > 500 && this._bank.getBalance() > 1000) return true;
        else return true;

    }


}
