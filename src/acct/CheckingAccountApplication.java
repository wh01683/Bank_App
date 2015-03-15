package acct;

import bank_package.Customer;

class CheckingAccountApplication implements AccountApplication {

    private static final double MIN_BALANCE = 0;
    private final Customer _cust;
    private boolean approved;

    public CheckingAccountApplication(Customer newCustomer, double openingBalance) {
        this._cust = newCustomer;
        this.approved = decideApproved(openingBalance);
    }

    public boolean screeningResult() {
        return this.approved;
    }

    private boolean decideApproved(double openingBalance) {
        this.approved = true;
        this.approved = !(openingBalance < MIN_BALANCE);
        this.approved = !(this._cust.getChexSystemsScore() < 400);
        this.approved = !(this._cust.getAge() < 15);

        return this.approved;

    }


}
