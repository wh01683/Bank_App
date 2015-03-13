package bank_package;

/**
 * Created by robert on 3/11/2015.
 */
public class IndividualRetirementAccountApplication implements AccountApplication {
    private static final double MIN_BALANCE = 100.0;
    private Bank _bank;
    private boolean approved;
    private Customer _cust;


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
        if (this._cust.getChexSystemsScore() < 500 | this._cust.getCreditScore() < 500) this.approved = false;
        else if (this._cust.getAge() < 18) this.approved = false;
        else this.approved = true;

        return this.approved;

    }
}
