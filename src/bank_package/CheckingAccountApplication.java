package bank_package;


public class CheckingAccountApplication implements AccountApplication {

    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public CheckingAccountApplication(Customer newCustomer) {
        this._cust = newCustomer;
        this.approved = decideApproved();
    }

    public boolean screeningResult() {
        return this.approved;
    }

    private boolean decideApproved() {
        if (this._cust.getChexSystemsScore() < 400) this.approved = false;
        else if (this._cust.getAge() < 15) this.approved = false;
        else this.approved = true;

        return this.approved;

    }


}
