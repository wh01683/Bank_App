package acct;

import bank_package.Customer;

/**

 * Created by robert on 3/10/2015.
 */


public interface Account {

    public double getBalance();

    public String getType();

    public double getMinRequiredBalance();

    public double deposit(double amount);

    public double withdraw(double amount);

    public Integer getACCOUNT_NUMBER();

    public boolean checkWithdrawLimits(double withdrawal);

    public Account applyForNewAccount(Customer customer, double openingBalance);
}
