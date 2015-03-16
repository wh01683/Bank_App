package bank_package;

import acct.Account;

import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by robert on 3/16/2015.
 */
public interface Bank {

    public void addAccount(Account newAccount);

    public Customer requestCustomer(UUID customerUUID);

    public Hashtable requestCustomerAccounts(UUID customerUUID);

    public boolean removeAccount(Integer accountNumber);

    public boolean hasAccount(Integer accountNumber);

    public boolean hasCustomer(UUID customerUUID);

}
