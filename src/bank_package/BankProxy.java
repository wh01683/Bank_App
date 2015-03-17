package bank_package;

import acct.Account;

import java.util.Hashtable;
import java.util.UUID;


public class BankProxy implements Bank {

    private RealBank realBank;

    public BankProxy(RealBank newRealBank) {
        this.realBank = newRealBank;
    }

    @Override
    public void addAccount(Account newAccount) {
        realBank.addAccount(newAccount);
    }

    @Override
    public Customer requestCustomer(UUID customerUUID) {
        if (!(realBank == null)) {
            return realBank.requestCustomer(customerUUID);
        } else {
            return null;
        }

    }

    @Override
    public Hashtable requestCustomerAccounts(UUID customerUUID) {
        if (!(realBank == null)) {
            return realBank.requestCustomerAccounts(customerUUID);
        } else {
            return null;
        }
    }

    @Override
    public boolean removeAccount(Integer accountNumber) {

        if (!(realBank == null)) {
            realBank.removeAccount(accountNumber);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasAccount(Integer accountNumber) {
        return realBank.hasAccount(accountNumber);
    }

    @Override
    public boolean hasCustomer(UUID customerUUID) {
        return realBank.hasCustomer(customerUUID);
    }

    @Override
    public boolean addCustomer(Customer customer) {
        realBank.addCustomer(customer);
        return true;
    }
}
