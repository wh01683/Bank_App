package acct;

import bank_package.Customer;

/**
 * Created by robert on 3/11/2015.
 */
class IndividualRetirementAccountApplication implements AccountApplication {
    private static final double MIN_BALANCE = 100.0;
    private final Customer _cust;
    private boolean approved;


    public IndividualRetirementAccountApplication(Customer newCustomer, double openingBalance) {
        this._cust = newCustomer;
        this.approved = decideApproved(openingBalance);
    }
    public boolean screeningResult() {

        return this.approved;
    }

    private boolean decideApproved(double openingBalance) {
        this.approved = true;
        this.approved = !(openingBalance < MIN_BALANCE);
        this.approved = !(this._cust.getChexSystemsScore() < 300 | this._cust.getCreditScore() < 300);
        return this.approved;

    }
}
