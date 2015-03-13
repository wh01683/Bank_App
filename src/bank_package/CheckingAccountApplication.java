package bank_package;


public class CheckingAccountApplication implements AccountApplication {

    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public CheckingAccountApplication(Customer newCustomer) {
        this.approved = decideApproved();
    }

    public boolean screeningResult() {
        return this.approved;
    }

    private boolean decideApproved() {
        boolean approved = true;

        if (this._cust.getChexSystemsScore() < 400) this.approved = false;
        else if (this._cust.getAge() < 15) this.approved = false;
        else this.approved = true;

        return approved;

    }


}
