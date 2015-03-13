package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;
import bank_package.uScanner;

import java.util.Random;

/**
 * Created by robert on 3/13/2015.
 */
public class AccountFactory {

    private final uScanner termLengthScanner = new uScanner("Please enter desired term length. Please note, this is fixed.", 0, 49);
    private final uScanner openingBalanceScanner = new uScanner("Please enter available opening balance.", -1, 2000000000);
    private RandomGenerator random = new RandomGenerator();
    private Random r = new Random();

    /*Account factory for creating and applying to different types of accounts*/

    public Account getAccount(String accountType, Customer customer) {
        if (accountType == null)
            return null;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new CheckingAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new CheckingAccount(customer, tempOpeningBalance);
            else return null;
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new SavingsAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new SavingsAccount(customer, tempOpeningBalance);
            else return null;
        } else if (accountType.equalsIgnoreCase("IRA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new IndividualRetirementAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new IndividualRetirementAccount(customer, tempOpeningBalance);
            else return null;
        } else if (accountType.equalsIgnoreCase("CD")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new CertificateOfDepositApplication(customer, tempOpeningBalance).screeningResult())
                return new CertificateOfDepositAccount(customer, tempOpeningBalance, termLengthScanner.intGet());
            else return null;
        } else if (accountType.equalsIgnoreCase("MMA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            if (new MoneyMarketAccountApplication(customer, tempOpeningBalance).screeningResult())
                return new MoneyMarketAccount(customer, tempOpeningBalance);
            else return null;
        }
        return null;
    }

    public Account getRandomAccount(Customer customer) {

        String accountType = random.accountTypeGen();

        if (accountType == null)
            return null;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            double tempOpeningBalance = r.nextDouble() * 500000;
            CheckingAccountApplication checkApp = new CheckingAccountApplication(customer, tempOpeningBalance);
            if (checkApp.screeningResult())
                return new CheckingAccount(customer, tempOpeningBalance);
            else return null;
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            double tempOpeningBalance = r.nextDouble() * 500000;
            SavingsAccountApplication saveApp = new SavingsAccountApplication(customer, tempOpeningBalance);
            if (saveApp.screeningResult())
                return new SavingsAccount(customer, tempOpeningBalance);
            else return null;
        } else if (accountType.equalsIgnoreCase("IRA")) {
            double tempOpeningBalance = r.nextDouble() * 500000;
            IndividualRetirementAccountApplication iraApp = new IndividualRetirementAccountApplication(customer, tempOpeningBalance);
            if (iraApp.screeningResult())
                return new IndividualRetirementAccount(customer, tempOpeningBalance);
            else return null;
        } else if (accountType.equalsIgnoreCase("CD")) {
            double tempOpeningBalance = r.nextDouble() * 500000;
            CertificateOfDepositApplication cdApp = new CertificateOfDepositApplication(customer, tempOpeningBalance);
            if (cdApp.screeningResult())
                return new CertificateOfDepositAccount(customer, tempOpeningBalance, random.getInts(0, 50));
            else return null;
        } else if (accountType.equalsIgnoreCase("MMA")) {
            double tempOpeningBalance = r.nextDouble() * 500000;
            MoneyMarketAccountApplication mmaApp = new MoneyMarketAccountApplication(customer, tempOpeningBalance);
            if (mmaApp.screeningResult())
                return new MoneyMarketAccount(customer, tempOpeningBalance);
            else return null;
        }
        return null;
    }
}

