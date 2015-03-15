package acct;

import bank_package.Customer;
import bank_package.RandomGenerator;
import bank_package.uScanner;

import java.util.Random;



public class AccountFactory {

    private final uScanner openingBalanceScanner = new uScanner("Please enter available opening balance.", -1.0, 2000000000.0);

    private RandomGenerator randomGenerator = new RandomGenerator();
    private IndividualRetirementAccount tempIRA;
    private CheckingAccount tempCheck;
    private SavingsAccount tempSave;
    private CertificateOfDepositAccount tempCD;
    private MoneyMarketAccount tempMMA;

    /*Account factory for creating and applying to different types of accounts*/

    public Account getAccount(String accountType, Customer customer) {
        if (accountType == null)
            return null;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
                return tempCheck.applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
                return tempSave.applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("IRA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return tempIRA.applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("CD")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return tempCD.applyForNewAccount(customer, tempOpeningBalance);
        } else if (accountType.equalsIgnoreCase("MMA")) {
            double tempOpeningBalance = openingBalanceScanner.doubleGet();
            return tempMMA.applyForNewAccount(customer, tempOpeningBalance);
        }
        else
            return null;
    }


    public Account getRandomAccount(Customer customer) {
        String randomAccountType = randomGenerator.accountTypeGen();
        if (randomAccountType == null)
                return null;
            if (randomAccountType.equalsIgnoreCase("CHECKING")) {
                double tempOpeningBalance = randomGenerator.getDubs(20, 15000);
                return tempCheck.applyForNewAccount(customer, tempOpeningBalance);
            } else if (randomAccountType.equalsIgnoreCase("SAVINGS")) {
                double tempOpeningBalance = randomGenerator.getDubs(20, 15000);
                return tempSave.applyForNewAccount(customer, tempOpeningBalance);
            } else if (randomAccountType.equalsIgnoreCase("IRA")) {
                double tempOpeningBalance = randomGenerator.getDubs(20, 15000);
                return tempIRA.applyForNewAccount(customer, tempOpeningBalance);
            } else if (randomAccountType.equalsIgnoreCase("CD")) {
                double tempOpeningBalance = randomGenerator.getDubs(20, 15000);
                return new CertificateOfDepositAccount(customer,tempOpeningBalance,randomGenerator.getInts(-5,52));
            } else if (randomAccountType.equalsIgnoreCase("MMA")) {
                double tempOpeningBalance = randomGenerator.getDubs(20, 15000);
                return tempMMA.applyForNewAccount(customer, tempOpeningBalance);
            } else
                return null;
        }


}

