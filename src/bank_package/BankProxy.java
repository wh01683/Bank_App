package bank_package;

import acct.Account;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;


public class BankProxy implements Bank {

    private static RealBank realBank;

    /**
     * BankProxy creates a new proxy to a given Real Bank object. used to protect sensitive data in the real bank from
     * unauthorized access/modification.
     *
     * @param newRealBank instance of the Real Bank object to be referenced
     */
    public BankProxy(RealBank newRealBank) {
        realBank = newRealBank;
    }

    /**
     * addAccount adds a given account object to the real bank object
     *
     * @param newAccount account object to be added
     * */
    @Override
    public void addAccount(Account newAccount) {
        realBank.addAccount(newAccount);
    }

    /**
     * requestCustomer requests a single customer from the real bank given a UUID
     *
     * @param email email of the requested customer
     *
     * @return customer object associated with the given UUID
     * */
    @Override
    public Customer requestCustomer(String email) {
        if (!(realBank == null)) {
            return realBank.requestCustomer(email);
        } else {
            return null;
        }

    }

    /**
     * requestCustomerAccounts returns account hash table associated with a single customer
     *
     * @param email email of the customer requesting the accounts
     *
     * @return returns a Hashtable object with Accounts as values and their respective account numbers as keys
     * */
    @Override
    public Hashtable requestCustomerAccounts(String email) {
        if (!(realBank == null)) {
            return realBank.requestCustomerAccounts(email);
        } else {
            return null;
        }
    }

    /**
     * removeAccount removes a specific account from the real bank and from the owner
     *
     * @param accountNumber account number of the account object to be deleted
     *
     * @return true if successfully deleted, false otherwise
     * */
    @Override
    public boolean removeAccount(Integer accountNumber) {

        if (!(realBank == null)) {
            realBank.removeAccount(accountNumber);
            return true;
        } else {
            return false;
        }
    }

    /**
     * startLoginProcess asks the real bank if an account exists or not
     *
     * @param accountNumber account number associated with the account object
     *
     * @return returns true if account exists, false otherwise
     * */
    @Override
    public boolean hasAccount(Integer accountNumber) {
        return realBank.hasAccount(accountNumber);
    }

    /**
     * hasCustomer asks the bank if a customer exists or not
     *
     * @param email email associated with the desired customer
     *
     * @return true if customer exists, false otherwise
     * */
    @Override
    public boolean hasCustomer(String email) {
        return realBank.hasCustomer(realBank.getCustomerUUID(email));
    }

    /**
     * addCustomer adds a customer and associated accounts to the real bank
     *
     * @param customer customer object to be added
     *
     * @return returns true if customer was successfully added, false otherwise
     * */
    @Override
    public boolean addCustomer(Customer customer) {

        if (!(realBank == null)) {
            realBank.addCustomer(customer);
            return true;
        } else {
            return false;
        }

    }

/**
 * saveAllBankDataToFile saves all bank data to file by creating a new temporary instance of the static real bank and
 *                       writing to a file via object output stream. streams are closed after it is finished.
 * */
public void saveAllBankDataToFile() {

        RealBank nonStaticRealBank = realBank;

        try {

            FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/bankData.txt");
            ObjectOutputStream bankDataWriter = new ObjectOutputStream(fos);

            bankDataWriter.writeObject(nonStaticRealBank);


            fos.close();
            bankDataWriter.close();

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
