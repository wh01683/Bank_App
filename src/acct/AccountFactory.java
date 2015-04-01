package acct;

import bank_package.Customer;
import utility.uScanner;

import java.io.Serializable;


public class AccountFactory implements Serializable {

    private final uScanner openingBalanceScanner = new uScanner("Please enter available opening balance.", 0, 2000000000);

    /*Account factory for creating and applying to different types of accounts*/

    /**
     * Method used to create accounts using a factory format. The request from the customer is passed as a string
     * and it is evaluated and matched to the desired account constructor. The customer applying for the account is also
     * passed through the params to provide information required to open the account.
     *
     * @param accountType String representation of the desired account type.
     * @param customer    customer applying for the account
     * @return returns the desired, newly constructed account if the customer qualified for the account, returns null
     * otherwise
     */
    public Account getAccount(String accountType, Customer customer) {
        if (accountType == null)
            return null;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return new CheckingAccount(customer, tempOpeningBalance).applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return new SavingsAccount(customer, tempOpeningBalance).applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("IRA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return new IndividualRetirementAccount(customer, tempOpeningBalance).applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("CD")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return new CertificateOfDepositAccount(customer, tempOpeningBalance, 10).applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("MMA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return new MoneyMarketAccount(customer, tempOpeningBalance).applyForNewAccount(customer, tempOpeningBalance);
        }
        else
            return null;
    }

}

