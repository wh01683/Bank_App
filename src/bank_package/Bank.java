package bank_package;

public class Bank {

    private double balance;

    public Bank(double newBalance) {
        this.balance = newBalance;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double newBalance) {
        this.balance = newBalance;
    }

}
