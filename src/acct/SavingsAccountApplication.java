package acct;

import bank_package.Bank;
import bank_package.Customer;

/**
 * Created by robert on 3/11/2015.
 */
public class SavingsAccountApplication implements AccountApplication {

    private static final double MIN_BALANCE = 5.0;
    private final Customer _cust;
    private Bank _bank;
    private boolean approved;

    public SavingsAccountApplication(Customer newCustomer, double openingBalance) {
        this._cust = newCustomer;
        this.approved = decideApproved(openingBalance);

    }

    public boolean screeningResult() {
        return this.approved;
    }

    private boolean decideApproved(double openingBalance) {
        if (openingBalance < MIN_BALANCE) this.approved = false;
        else if (this._cust.getChexSystemsScore() < 300) this.approved = false;
        else if (this._cust.getAge() < 11) this.approved = false;
        else this.approved = true;

        return this.approved;
    }

    public double getMinBalance() {
        return MIN_BALANCE;
    }
}