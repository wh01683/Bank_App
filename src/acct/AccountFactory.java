package acct;

import bank_package.Customer;
import utility.RandomGenerator;
import utility.uScanner;

import java.io.Serializable;


public class AccountFactory implements Serializable {

    private final uScanner openingBalanceScanner = new uScanner("Please enter available opening balance.", 0, 2000000000);

    private final RandomGenerator randomGenerator = new RandomGenerator();
    private final IndividualRetirementAccount tempIRA = new IndividualRetirementAccount(new Customer(), randomGenerator.getDubs(-50, 500));
    private final CheckingAccount tempCheck = new CheckingAccount(new Customer(), randomGenerator.getDubs(-50, 500));
    private final SavingsAccount tempSave = new SavingsAccount(new Customer(), randomGenerator.getDubs(-50, 500));
    private final CertificateOfDepositAccount tempCD = new CertificateOfDepositAccount(new Customer(), randomGenerator.getDubs(-50, 500), randomGenerator.getInts(0, 55));
    private final MoneyMarketAccount tempMMA = new MoneyMarketAccount(new Customer(), randomGenerator.getDubs(-50, 500));

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
            double tempOpeningBalance = randomGenerator.getDubs(20.0, 15000.0);
            return tempCheck.applyForNewAccount(customer,tempOpeningBalance);
        } else if (randomAccountType.equalsIgnoreCase("SAVINGS")) {
            double tempOpeningBalance = randomGenerator.getDubs(20.0, 15000.0);
            return tempSave.applyForNewAccount(customer, tempOpeningBalance);
        } else if (randomAccountType.equalsIgnoreCase("IRA")) {
            double tempOpeningBalance = randomGenerator.getDubs(20.0, 15000.0);
            return tempIRA.applyForNewAccount(customer, tempOpeningBalance);
        } else if (randomAccountType.equalsIgnoreCase("CD")) {
            double tempOpeningBalance = randomGenerator.getDubs(20.0, 15000.0);
            return tempCD.applyForNewRandomAccount(customer, tempOpeningBalance);
        } else if (randomAccountType.equalsIgnoreCase("MMA")) {
            double tempOpeningBalance = randomGenerator.getDubs(20.0, 15000.0);
            return tempMMA.applyForNewAccount(customer, tempOpeningBalance);
        } else if (randomAccountType.equalsIgnoreCase("RETURN"))
            return null;

        else return null;
    }

}

