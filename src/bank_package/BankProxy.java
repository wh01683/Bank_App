package bank_package;

import acct.Account;

import java.util.Hashtable;
import java.util.UUID;


public class BankProxy implements Bank {

    private final RealBank realBank;

    public BankProxy(RealBank newRealBank) {
        this.realBank = newRealBank;
    }

    @Override
    public void addAccount(Account newAccount) {
        this.realBank.addAccount(newAccount);
    }

    @Override
    public Customer requestCustomer(UUID customerUUID) {
        if (!(this.realBank == null)) {
            return this.realBank.requestCustomer(customerUUID);
        } else {
            return null;
        }

    }

    @Override
    public Hashtable requestCustomerAccounts(UUID customerUUID) {
        if (!(this.realBank == null)) {
            return this.realBank.requestCustomerAccounts(customerUUID);
        } else {
            return null;
        }
    }

    @Override
    public boolean removeAccount(Integer accountNumber) {

        if (!(this.realBank == null)) {
            this.realBank.removeAccount(accountNumber);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasAccount(Integer accountNumber) {
        return this.realBank.hasAccount(accountNumber);
    }

    @Override
    public boolean hasCustomer(UUID customerUUID) {
        return this.realBank.hasCustomer(customerUUID);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        if (!this.realBank.hasCustomer(customer.getUUID())) {
            this.realBank.addCustomer(customer);
            return true;
        } else
            return false;

    }
}
