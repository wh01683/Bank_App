package bank_package;

import acct.Account;

import java.util.Hashtable;
import java.util.UUID;


/*Bank Interface defines the methods available to the Bank Proxy and ultimately the customer. the goal is to restrict
* their access and not give customers the ability to edit balances(aside from making a deposit or withdrawal), add/remove
* other customers, etc.*/

interface Bank {

    public void addAccount(Account newAccount);

    public Customer requestCustomer(UUID customerUUID);

    public Hashtable requestCustomerAccounts(UUID customerUUID);

    public boolean removeAccount(Integer accountNumber);

    public boolean hasAccount(Integer accountNumber);

    public boolean hasCustomer(UUID customerUUID);

    public boolean addCustomer(Customer customer);

}
