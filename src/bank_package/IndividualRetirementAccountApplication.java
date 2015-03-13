package bank_package;

/**
 * Created by robert on 3/11/2015.
 */
public class IndividualRetirementAccountApplication implements AccountApplication {
    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public IndividualRetirementAccountApplication(Customer newCustomer) {
        this._cust = newCustomer;
        this.approved = decideApproved();
    }

    public boolean screeningResult() {

        return this.approved;
    }

    private boolean decideApproved() {

        if (this._cust.getChexSystemsScore() < 500 | this._cust.getCreditScore() < 500) this.approved = false;
        else if (this._cust.getAge() < 18) this.approved = false;
        else this.approved = true;

        return this.approved;

    }
}
