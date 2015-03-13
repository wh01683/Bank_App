package acct;

import bank_package.Bank;
import bank_package.Customer;

public class CheckingAccountApplication implements AccountApplication {

    private static final double MIN_BALANCE = 0;
    private Bank _bank;
    private boolean approved;
    private Customer _cust;

    public CheckingAccountApplication(Customer newCustomer, double openingBalance) {
        this._cust = newCustomer;
        this.approved = decideApproved(openingBalance);
    }

    public boolean screeningResult() {
        return this.approved;
    }

    private boolean decideApproved(double openingBalance) {
        if (openingBalance < MIN_BALANCE) this.approved = false;
        else if (this._cust.getChexSystemsScore() < 400) this.approved = false;
        else if (this._cust.getAge() < 15) this.approved = false;
        else this.approved = true;

        return this.approved;

    }


}
