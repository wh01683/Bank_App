package acct;

import bank_package.Customer;

/**

 * Created by robert on 3/10/2015.
 */


public interface Account {

    public double getBalance();

    public void setBalance(double newBalance);

    public double getInterest();

    public String getType();

    public Customer getOwner();

    public double getMinRequiredBalance();

    public double deposit(double amount);

    public double withdraw(double amount);

    public Account getNewAccount(Customer customer, double openingBalance);

    public Integer getACCOUNT_NUMBER();
}
