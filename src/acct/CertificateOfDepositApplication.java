package acct;

import bank_package.Bank;
import bank_package.Customer;


/**
 * Created by robert on 3/11/2015.
 */
public class CertificateOfDepositApplication implements AccountApplication {

    private static final double MIN_BALANCE = 1000.0;
    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public CertificateOfDepositApplication(Customer newCustomer, double openingBalance) {
        this._cust = newCustomer;
        this.approved = decideApproved(openingBalance);
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
