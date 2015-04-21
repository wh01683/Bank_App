package bank_interface;

import acct.Account;
import acct.AccountFactory;
import acct.AccountNumberGenerator;
import bank_package.BankProxy;

import java.util.Hashtable;

/**
 * @author robert
 * Created on 3/15/2015
 */
public class LoggedInState implements CustomerInterfaceState {

    private static DataIO dataIO;
    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;
    private final AccountFactory accountFactory = new AccountFactory();


    /**
     * constructor of the LoggedInState state class, utilized by CustomerInterface class.
     *
     * @param newBankProxy         bank proxy object passed by the CustomerInterface class when the CustomerInterface is created
     *                             the bank proxy provides the LoggedInState state with the information it needs about the customer
     *                             and its accounts, but restricts it from editing some vital parts of the RealBank class
     * @param newCustomerInterface instance of the CustomerInterface class; used to set new states
     * @param newDataIO            new instance of a pre-created DataIO object passed by the CustomerInterface invoker.
     */
    public LoggedInState(CustomerInterface newCustomerInterface, BankProxy newBankProxy, DataIO newDataIO) {
        bankProxy = newBankProxy;
        dataIO = newDataIO;
        AccountFactory.setAccountNumberGenerator(new AccountNumberGenerator(dataIO.getRealBank().getAccountNumbersUsed()));
        this.customerInterface = newCustomerInterface;
    }


    /**
     * processes the user's ENTERED email
     *
     * @param email customer's email
     * @return returns feedback to the user based on the outcome of the email entering process*/
    @Override
    public String enterEmail(String email) {
        return ("You are currently logged in.");
    }

    /**
     * processes the user's ENTERED password
     *
     * @param password customer's password
     * @return returns feedback to the user based on the outcome of the password entering process*/
    @Override
    public String enterPassword(String password) {
        return ("You are currently logged in.");
    }

    /**logs the user out of the system and resets the CustomerInterface state after saving all current data to
     * the appropriate file.
     *        */
    @Override
    public void logOff() {
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
    }

    /** allows the logged in customer to add a new bank account under their name by passing their request
     *            (as a string) to the AccountFactory object. If a null account is returned by the Account Factory,
     *            the customer did not qualify for the account and nothing is added. Otherwise, the Account Factory returns
     *            a new account object created with their desired opening balance. Once the account is created, the
     *            information of the newly created account is printed back to the customer for confirmation. After the
     *            new account is added, the customer is given the opportunity to continue adding more accounts or return
     *            to the previous menu or log off. If at any time the method does not recognize an input string, the
     *            method is called recursively to give the opportunity to correct their input.
     *
     *
     * @param accountRequest String representation of the account type desired by the customer
     * @param openingBalance opening balance passed to the account factory
     * @return returns feedback to the user based on the add account process
     * */
    @Override
    public String addAccount(String accountRequest, double openingBalance) throws NullPointerException {

            Account tempAccount = accountFactory.getAccount(accountRequest, customerInterface.getCustomer(), openingBalance);
                if (tempAccount == null) {
                    return ("You do not qualify for a " + accountRequest + " account at this time.");
                } else {
                    bankProxy.addAccount(tempAccount);
                    customerInterface.getCustomer().addAccount(tempAccount);
                    return ("Congratulations. Account successfully added.");
                }
    }

    /**
     * removes the account associated with the account number provided
     *
     * @param accountNumber account number of the account to be removed
     * @return returns feedback based on the outcome of the account removal process
     */
    @Override
    public String removeAccount(Integer accountNumber) {
        if (this.customerInterface.getCustomer().getAccountHashtable().containsKey(accountNumber)) {
            if (bankProxy.removeAccount(accountNumber)) {
                return ("Account successfully removed.");
            } else {
                return ("Could not remove account");
            }

        } else if (bankProxy.hasAccount(accountNumber) && !(this.customerInterface.getCustomer().
                getAccountHashtable().containsKey(accountNumber))) {
            return ("Account does not belong to you.");
        } else if (!bankProxy.hasAccount(accountNumber)) {
            return ("Account not found.");
        } else {
            return ("Could not process your request.");
        }
    }



    /**
     *  method to process transactions requested by the user. A request in string form is passed through
     *                    as a param. During each transaction choice, the method handles cases such as: owner does not own
     *                    account, invalid account number, depositing too large of an amount, withdrawing too large, and attempting
     *                    to withdraw or deposit negative amounts
     *
     *                    Transfer requests also handle cases where the transfer TO and transfer FROM account numbers are
     *                    the same, attempting to initialize transfers while only 1 account is owned, and cases where one
     *                    half of the transaction is valid (method will undo the other half)
     *
     * @param transactionChoice String version of the user's transaction choice (transfer, withdraw, deposit)
     * @param accountFromNumber Account number of the account to take money FROM
     * @param accountToNumber Account number of the accoun tot put money IN
     * @param withdrawAmount Amount of money to withdraw. For transfers, this will equal deposit
     * @param depositAmount Amount of money to deposit. For transfers, this will equal withdraw
     * @return returns feedback to the user depending on the outcome of the transaction process
     * */
    public String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber,
                                   double withdrawAmount, double depositAmount) {
        try {
            Hashtable customerAccountHashtable = customerInterface.getCustomer().getAccountHashtable();

            if (customerAccountHashtable.isEmpty()) {
                return ("Customer has no account.");
            }



        /*DEPOSIT SECTION*/
                if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {

                    if (!(customerInterface.getCustomer().getAccount(accountToNumber) == null)) {
                        if (customerInterface.getCustomer().getAccount(accountToNumber).getBalance() + depositAmount > Integer.MAX_VALUE) {
                            return ("Current balance too large. Could not process your deposit.");
                        }
                        customerInterface.getCustomer().getAccount(accountToNumber).deposit(depositAmount);
                        return ("Deposit Successful");
                    } else if (customerInterface.getCustomer().getAccount(accountToNumber) == null) {
                        return ("Account not found. Please re-enter your account number.");
                    }
                }



        /*WITHDRAW SECTION*/

            else if (transactionChoice.equalsIgnoreCase("WITHDRAW")) {

                    if (!(customerInterface.getCustomer().getAccount(accountFromNumber) == null)) {
                        if ((customerInterface.getCustomer().getAccount(accountFromNumber).withdraw(withdrawAmount)) == -1) {
                            return ("Insufficient funds");
                    } else {
                            return ("Withdrawal successful.");
                    }
                    } else if (customerInterface.getCustomer().getAccount(accountFromNumber) == null) {
                        return ("Account not found.");
            }


        /*TRANSFER SELECTION*/
            else if (transactionChoice.equalsIgnoreCase("TRANSFER")) {

                        if (!bankProxy.hasAccount(accountFromNumber) || !bankProxy.hasAccount(accountToNumber)) {
                            return ("Account not found.");
                        } else if (bankProxy.hasAccount(accountToNumber)) { /*checks to make sure account exists*/
                        } else if ((!bankProxy.hasAccount(accountFromNumber))) { /*if FROM account does not exist, go back*/
                            return ("Account not found");
                        } else if (accountFromNumber.equals(accountToNumber)) { /*checks for the same account number*/
                            return ("You may not transfer to the same account.");
                        } else { /*checks From account again*/

                            if (!customerInterface.getCustomer().getAccount(accountFromNumber).checkWithdrawLimits(withdrawAmount)) {
                                /*if withdrawal will result in a negative 1 balance, will not let them withdraw*/
                                return ("Insufficient funds");
                        } else { /*if they CAN withdraw, they make it here*/
                                customerInterface.getCustomer().getAccount(accountFromNumber).withdraw(withdrawAmount);
                        }/*if they have too much money, they cannot deposit and thus cannot withdraw.*/


                            if ((customerInterface.getCustomer().getAccount(accountToNumber).getBalance() + depositAmount) > Integer.MAX_VALUE) {
                                return ("Current balance too large. Could not process your transfer.");

                        } else {/*if they CAN deposit, they get here*/
                                customerInterface.getCustomer().getAccount(accountToNumber).deposit(depositAmount);
                        }


                            return ("Transfer Successful.");
                        }
                    } else {
                        return ("Request could not be processed.");
            }

                }
        } catch (NullPointerException e) {
            System.out.printf("Null pointer caught in LoggedInState : startTransaction");
        }
        return ("Request could not be processed.");
    }


    /**
     * stores headers used for displaying account information to the user
     *
     * @return String representation of the account information headers.
 * */
    private String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }
}
