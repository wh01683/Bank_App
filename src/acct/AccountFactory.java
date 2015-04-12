package acct;

import bank_package.Customer;


public class AccountFactory {

    private static AccountNumberGenerator accountNumberGenerator = new AccountNumberGenerator();

    /*Account factory for creating and applying to different types of accounts*/

    public static AccountNumberGenerator getAccountNumberGenerator() {
        return accountNumberGenerator;
    }

    public static void setAccountNumberGenerator(AccountNumberGenerator accountNumberGenerator) {
        AccountFactory.accountNumberGenerator = accountNumberGenerator;
    }

    /**
     * Method used to create accounts using a factory format. The request from the customer is passed as a string
     * and it is evaluated and matched to the desired account constructor. The customer applying for the account is also
     * passed through the params to provide information required to open the account.
     *
     * @param accountType String representation of the desired account type.
     * @param customer customer applying for the account
     * @param openingBalance opening balance of the account to be created
     * @return returns the desired, newly constructed account if the customer qualified for the account, returns null
     * otherwise
     */
    public Account getAccount(String accountType, Customer customer, double openingBalance) {
        if (accountType == null)
            return null;
        if (accountType.equalsIgnoreCase("CHECKING")) {
            return new CheckingAccount(customer, openingBalance, accountNumberGenerator.acctGen()).
                    applyForNewAccount(customer, openingBalance, accountNumberGenerator.acctGen());
        } else if (accountType.equalsIgnoreCase("SAVINGS")) {
            return new SavingsAccount(customer, openingBalance, accountNumberGenerator.acctGen()).
                    applyForNewAccount(customer, openingBalance, accountNumberGenerator.acctGen());
        } else if (accountType.equalsIgnoreCase("IRA")) {
            return new IndividualRetirementAccount(customer, openingBalance, accountNumberGenerator.acctGen()).
                    applyForNewAccount(customer, openingBalance, accountNumberGenerator.acctGen());
        } else if (accountType.equalsIgnoreCase("CD")) {
            return new CertificateOfDepositAccount(customer, openingBalance, accountNumberGenerator.acctGen()).
                    applyForNewAccount(customer, openingBalance, accountNumberGenerator.acctGen());
        } else if (accountType.equalsIgnoreCase("MMA")) {
            return new MoneyMarketAccount(customer, openingBalance, accountNumberGenerator.acctGen()).
                    applyForNewAccount(customer, openingBalance, accountNumberGenerator.acctGen());
        }
        else
            return null;
    }
}

