package acct;

import bank_package.Customer;

/**
 * Created by robert on 3/13/2015.
 */
public class MoneyMarketAccountApplication implements AccountApplication {

    private static final double MIN_BALANCE = 5000.0;
    private final Customer _cust;
    private boolean approved;


    public MoneyMarketAccountApplication(Customer newCustomer, double openingBalance) {
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
