package acct;

import bank_package.Customer;
import bank_package.uScanner;

/**
 * Created by robert on 3/13/2015.
 */
public class AccountFactory {

    uScanner termLengthScanner = new uScanner("Please enter desired term length. Please note, this is fixed.", 0, 49);
    uScanner openingBalanceScanner = new uScanner("Please enter available opening balance.", -1, 2000000000);

    /*Account factory for creating and applying to different types of accounts*/

    public Account getAccount(String accountType, Customer customer) {
        if (accountType == null)
            return null;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new CheckingAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new CheckingAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new SavingsAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new SavingsAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("IRA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new IndividualRetirementAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new IndividualRetirementAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("CD")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new CertificateOfDepositApplication(customer, tempOpeningBalance).screeningResult())
                return new CertificateOfDepositAccount(customer, tempOpeningBalance, termLengthScanner.intGet());
        }

        return null;
    }

}
