package acct;

import bank_package.Customer;

import java.util.UUID;

/**

 * Created by robert on 3/10/2015.
 */


public interface Account {

    double getBalance();

    String getType();

    double getMinRequiredBalance();

    double deposit(double amount);

    double withdraw(double amount);

    Integer getACCOUNT_NUMBER();

    boolean checkWithdrawLimits(double withdrawal);

    Account applyForNewAccount(Customer customer, double openingBalance);

    UUID getOwner();
}
