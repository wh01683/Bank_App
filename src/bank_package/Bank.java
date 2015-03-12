package bank_package;
public class Bank {

    private String name = "Sea Island Bank - No Shoes, No Problem!";
    private double balance;
    private int numberAccounts;

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
