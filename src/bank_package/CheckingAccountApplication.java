package bank_package;


public abstract class CheckingAccountApplication implements AccountApplication {

    private Bank _bank;
    private boolean approved;
    private Customer _cust;


    public CheckingAccountApplication(Customer newCustomer) {
        this.approved = decideApproved();
    }

    public String screeningResult() {
        if (!this.approved)
            return ("You have been declined, " + this._cust.getName() + ". We apologize.");
        else
            return ("Congratulations " + this._cust.getName() + "! You have been approved.");
    }

    private boolean decideApproved() {
        boolean appr = true;

        if (this._cust.getChexSystemsScore() < 400) this.approved = false;
        else if (this._cust.getAge() < 15) this.approved = false;
        else this.approved = true;

        return appr;

    }


}
