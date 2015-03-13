package acct;

import bank_package.Bank;
import bank_package.Customer;

/**
 * Created by robert on 3/11/2015.
 */
public class IndividualRetirementAccountApplication implements AccountApplication {
    private static final double MIN_BALANCE = 100.0;
    private final Customer _cust;
    private Bank _bank;
    private boolean approved;


    public IndividualRetirementAccountApplication(Customer newCustomer, double openingBalance) {
        this._cust = newCustomer;
        this.approved = decideApproved(openingBalance);
    }

    public double getMinBalance() {
        return MIN_BALANCE;
    }

    public boolean screeningResult() {

        return this.approved;
    }

    private boolean decideApproved(double openingBalance) {
        if (openingBalance < MIN_BALANCE) this.approved = false;
        if (this._cust.getChexSystemsScore() < 300 | this._cust.getCreditScore() < 300) this.approved = false;
        else this.approved = true;

        return this.approved;

    }
}
