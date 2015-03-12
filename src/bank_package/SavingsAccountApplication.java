package bank_package;

import javafx.application.Application;


/**
 * Created by robert on 3/11/2015.
 */
public class SavingsAccountApplication implements AccountApplication {

    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public SavingsAccountApplication(Customer newCustomer) {
        this.approved = decideApproved();
    }

    public String screeningResult() {
        if (!this.approved)
            return ("You have been declined, " + this._cust.getName() + ". We apologize.");
        else
            return ("Congratulations " + this._cust.getName() + "! You have been approved.");
    }

    private boolean decideApproved() {
        boolean appr = true;

        if (this._cust.getChexSystemsScore() < 300) this.approved = false;
        else if (this._cust.getAge() < 11) this.approved = false;
        else this.approved = true;

        return appr;

    }
}
