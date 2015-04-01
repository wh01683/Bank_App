package bank_interface;

import acct.Account;
import acct.AccountFactory;
import bank_package.BankProxy;
import utility.uScanner;

import java.util.Hashtable;

/**
 * @author robert
 * Created on 3/15/2015
 */
public class LoggedInState implements CustomerInterfaceState {

    private static DataIO dataIO;
    private static BankProxy bankProxy;
    private final uScanner ACCOUNT_REQUESTER_SCANNER = new uScanner("What type of account would you like to add?\nCHECKING, SAVINGS, MMA, IRA, CD, RETURN", -1, 10);
    private final uScanner INFORMATION_REQUEST_SCANNER = new uScanner("What would you like to know more about?\nCHEX, CREDIT, ACCOUNTS, ALL, MENU, LOGOFF", 2, 9);
    private final uScanner TRANSACTION_REQUEST_SCANNER = new uScanner("What transaction would you like to process?\nDEPOSIT, WITHDRAW, TRANSFER, ACCOUNTS, RETURN", 2, 9);
    private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", -2, 200000000);
    private final uScanner REQUEST_SCANNER = new uScanner("\nWhat would you like to know more about?. \nCHEX, CREDIT, ACCOUNTS, ALL, MENU, LOGOFF", 2, 9);
    private final CustomerInterface customerInterface;
    private final AccountFactory accountFactory = new AccountFactory();


    /**
     * LoggedInState constructor of the LoggedInState state class, utilized by CustomerInterface class.
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
        this.customerInterface = newCustomerInterface;
    }


    /**
     * enterUUID method prompts user for their UUID to login. in this state (Logged In), the method is unnecessary
     *
     * @param email customer's email*/
    @Override
    public void enterEmail(String email) {
        System.out.println("You are currently logged in.");
    }

    /**
     * enterPassword enterPassword method prompts user for the password. In this state (Logged In), the method is unnecessary
     *
     * @param password customer's password*/
    @Override
    public void enterPassword(String password) {
        System.out.println("You are currently logged in.");
    }

    /**
     * logOff logs the user out of the system and resets the CustomerInterface state after saving all current data to
     *        the appropriate file.
     *        */
    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        customerInterface.saveBankDataToFile();
        customerInterface.setCustomerInterfaceState(customerInterface.loggedOffState);
    }

    /**
     * requestInformation initiates the "logged in" process, allowing users to add/remove accounts, access information, etc
     * */
    @Override
    public void requestInformation() {
        initiateLoginProcesses();
    }

    /**
     * startTransaction invokes the processTransaction method and calls the uScanner to prompt the user for a choice
     * */
    @Override
    public void startTransaction() {
        this.processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
    }

    /**addAccount allows the logged in customer to add a new bank account under their name by passing their request
     *            (as a string) to the AccountFactory object. If a null account is returned by the Account Factory,
     *            the customer did not qualify for the account and nothing is added. Otherwise, the Account Factory returns
     *            a new account object created with their desired opening balance. Once the account is created, the
     *            information of the newly created account is printed back to the customer for confirmation. After the
     *            new account is added, the customer is given the opportunity to continue adding more accounts or return
     *            to the previous menu or log off. If at any time the method does not recognize an input string, the
     *            method is called recursively to give the opportunity to correct their input.
     *
     *            */
    @Override
    public void addAccount() {
        try {
            String accountRequest = ACCOUNT_REQUESTER_SCANNER.stringGet();
            if (accountRequest.equalsIgnoreCase("RETURN")) {
                customerInterface.requestInformation();
            } else {

                Account tempAccount = accountFactory.getAccount(accountRequest, bankProxy.requestCustomer(customerInterface.getCustomer().getEmail()));
                if (tempAccount == null) {
                    System.out.println("Account type invalid. Please try again.");
                    addAccount();
                } else {
                    bankProxy.addAccount(tempAccount);
                    bankProxy.requestCustomer(customerInterface.getCustomer().getEmail()).addAccount(tempAccount);
                    System.out.println("Congratulations, " + bankProxy.requestCustomer(customerInterface.getCustomer().getEmail()).getName() +
                            "!" + "You successfully added a " + tempAccount.getType() + " account under your name.\n" +
                            "Here is the information...\n");
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println(getAccountHeaders());
                    System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println(tempAccount.toString());
                    System.out.println("\n");
                    uScanner yesNo = new uScanner("Would you like to add more accounts?\nYES, NO, LOGOFF", 1, 4);
                    String moreAccounts = yesNo.stringGet();
                    if (moreAccounts.equalsIgnoreCase("YES")) {
                addAccount();
                    } else if (moreAccounts.equalsIgnoreCase("NO")) {
                        initiateLoginProcesses();
                    } else if (moreAccounts.equalsIgnoreCase("LOGOFF")) {
                        customerInterface.logOff();
                    } else {
                        System.out.println("Request could not be processed.");
                        addAccount();
            }
        }
                addAccount();
            }
        } catch (NullPointerException q) {
            System.out.printf("Null pointer caught in LoggedInState : addAccount");
            addAccount();
        }
    }

    /**
     * initiateLoginProcesses called when the customer first logs in. Prompts the user for directions and redirects them
     *                        towards the appropriate methods based on the customer's request string.
     *
     *                        */
    private void initiateLoginProcesses() {

        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + customerInterface.getCustomer().getName() +
                "?\nINFORMATION, TRANSACTION, MKACCT, RMACCT, LOGOFF, EXIT", 3, 12);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            /*essentially an infinite loop only ending when the customer exits, runs in to an exit-worthy error, or
            * trips "isLoggedIn" to false.*/
        String processRequest = PROCESS_REQUEST_SCANNER.stringGet();

        if (processRequest.equalsIgnoreCase("INFORMATION")) {
            this.printInformation(INFORMATION_REQUEST_SCANNER.stringGet());
            initiateLoginProcesses();
            } else if (processRequest.equalsIgnoreCase("TRANSACTION")) {
            this.processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
            initiateLoginProcesses();
            } else if (processRequest.equalsIgnoreCase("MKACCT")) {
            this.addAccount();
            initiateLoginProcesses();
            } else if (processRequest.equalsIgnoreCase("RMACCT")) {
            System.out.println("Which account would you like to remove?");
            Integer rmAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
            if (bankProxy.removeAccount(rmAccountNumber)) {
                System.out.printf("Account %10d was successfully removed.\n", rmAccountNumber);
                initiateLoginProcesses();
            } else {
                System.out.println("Account not found, try again.\n");
                bankProxy.removeAccount(ACCOUNT_NUMBER_SCANNER.intGet());
            }
        } else {
                /*if the user enters an invalid or unknown process, the method is called recursively and they're allowed
                * to try the process again.*/
            System.out.println("Your request could not be processed, please try again.");
            initiateLoginProcesses();
        }
    }

    /**
     * printInformation invoked by the initiateLoginProcess method, this method prints information requested by the user.
     *                  the method is called recursively after information is printed to mimic staying in the same menu.
     *                  During each iteration, the request string is checked for commands matching various "logOffRequest"
     *                  strings, defined by the method of similar name.
     *
     * @param request s                Account tempAccountToBeRemoved = accountHashtable.get(accountNumber);
    tring representation of the information requested by the user.
     * */
    private void printInformation(String request) {
        try {

            if (request.equalsIgnoreCase("CHEX")) {
                System.out.println("Your ChexSystems score is currently " + customerInterface.getCustomer().getChexSystemsScore() + ".");
                printInformation(REQUEST_SCANNER.stringGet());
            } else if (request.equalsIgnoreCase("CREDIT")) {
                System.out.println("Your Credit Score is currently " + customerInterface.getCustomer().getCreditScore() + ".");
                printInformation(REQUEST_SCANNER.stringGet());
            } else if (request.equalsIgnoreCase("ACCOUNTS")) {
                dataIO.printAccountInformation(customerInterface.getCustomer().getAccountHashtable());
                printInformation(REQUEST_SCANNER.stringGet());
            } else if (request.equalsIgnoreCase("ALL")) {
                dataIO.printAllCustomerPrivateInformation(customerInterface.getCustomer().getUUID().hashCode(), customerInterface.getCustomer().getAccountHashtable());
                printInformation(REQUEST_SCANNER.stringGet());
            } else {
                System.out.println("Could not process your request: " + request + " Please try again");
                printInformation(REQUEST_SCANNER.stringGet());
            }

        } catch (NullPointerException e) {
            System.out.printf("Null pointer caught in LoggedInState : printInformation");
            printInformation(REQUEST_SCANNER.stringGet());
        }


    }

    /**
     * processTransaction method to process transactions requested by the user. A request in string form is passed through
     *                    as a param. During each transaction choice, the method handles cases such as: owner does not own
     *                    account, invalid account number, depositing too large of an amount, withdrawing too large, and attempting
     *                    to withdraw or deposit negative amounts
     *
     *                    Transfer requests also handle cases where the transfer TO and transfer FROM account numbers are
     *                    the same, attempting to initialize transfers while only 1 account is owned, and cases where one
     *                    half of the transaction is valid (method will undo the other half)
     *
     * @param transactionChoice string representation of the transaction desired by the customer.
     * */
    private void processTransaction(String transactionChoice) {


        try {
            Hashtable customerAccountHashtable = customerInterface.getCustomer().getAccountHashtable();

            if (!customerAccountHashtable.isEmpty()) {
        /*DEPOSIT SECTION*/
                if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
                    System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                    Integer tempWithdrawAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                    if ((tempWithdrawAccountNumber == -1)) {
                        System.out.println("Returning.\n\n\n");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }
                    if (!(customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber) == null)) {
                        uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                        double tempDepositAmount = TRANSACTION_SCANNER.doubleGet();
                        if (customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getBalance() + tempDepositAmount > Integer.MAX_VALUE) {
                            System.out.println("Current balance too large. Could not process your deposit...\nHave you considered" +
                                    "retiring?");
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                        }
                        customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).deposit(tempDepositAmount);
                        System.out.printf("Successfully deposited %.2f into %s account number %d.\nYour current balance is: %.2f",
                                tempDepositAmount, customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getType(),
                                customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                                customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else if (customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber) == null) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(transactionChoice);
                    }

                }



        /*WITHDRAW SECTION*/

            else if (transactionChoice.equalsIgnoreCase("WITHDRAW")) {
                System.out.println("You have chosen " + transactionChoice + ". From which account would you like to " + transactionChoice + "?");
                Integer tempWithdrawAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if ((tempWithdrawAccountNumber == -1)) {
                    System.out.println("Returning.");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                }
                    if (!(customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 1, 200000000);
                    double tempWithdrawAmount = TRANSACTION_SCANNER.doubleGet();
                        if ((customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).withdraw(tempWithdrawAmount)) == -1) {
                        System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                tempWithdrawAmount, customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else {
                        System.out.printf("You have successfully withdrawn %.2f from %s %10d.\nYour current balance is %.2f.",
                                tempWithdrawAmount, customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getType(),
                                customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                                customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }

                    } else if (customerInterface.getCustomer().getAccount(tempWithdrawAccountNumber) == null) {
                    System.out.printf("Account %10d not found. Please re-enter your account number.", tempWithdrawAccountNumber);
                    processTransaction(transactionChoice);
                }

            }


        /*TRANSFER SELECTION*/
            else if (transactionChoice.equalsIgnoreCase("TRANSFER")) {
                System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");


                Integer tempTransferToAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                boolean deposit = true;
                boolean withdrew = true;


                if ((tempTransferToAccountNumber == -1)) { /*return request back to transaction selection*/
                    System.out.println("Returning.");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                } else if (bankProxy.hasAccount(tempTransferToAccountNumber)) { /*checks to make sure account exists*/
                    System.out.println("From which account would you like to " + transactionChoice + "?");
                    Integer tempTransferFromAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();


                    if ((tempTransferFromAccountNumber == -1)) { /*if TO account exists, user is prompted for their FROM account #. checks for return request*/
                        System.out.println("Returning.");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());


                    } else if ((!bankProxy.hasAccount(tempTransferFromAccountNumber))) { /*if FROM account does not exist, go back*/
                        System.out.printf("Could not find desired account number %d... please try again.", tempTransferFromAccountNumber);
                        processTransaction(transactionChoice);


                    } else if (tempTransferFromAccountNumber.equals(tempTransferToAccountNumber)) { /*checks for the same account number*/
                        System.out.println("You may not transfer to the same account.");
                        startTransaction();


                    } else if (bankProxy.hasAccount(tempTransferFromAccountNumber)) { /*checks From account again*/

                        uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                        double transferAmount = TRANSACTION_SCANNER.doubleGet();

                        if (customerInterface.getCustomer().getAccount(tempTransferFromAccountNumber).withdraw(transferAmount) == -1) {
                                /*if withdrawal will result in a negative 1 balance, will not let them withdraw*/
                            System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.\n",
                                    transferAmount, customerInterface.getCustomer().getAccount(tempTransferFromAccountNumber).getBalance());
                            customerInterface.getCustomer().getAccountHashtable().get(tempTransferFromAccountNumber).deposit(transferAmount);
                            withdrew = false; /*transfer will not continue if this is false*/


                        } else { /*if they CAN withdraw, they make it here*/
                            customerInterface.getCustomer().getAccount(tempTransferFromAccountNumber).withdraw(transferAmount);
                        }/*if they have too much money, they cannot deposit and thus cannot withdraw.*/


                        if ((customerInterface.getCustomer().getAccount(tempTransferToAccountNumber).getBalance() + transferAmount) > Integer.MAX_VALUE) {
                            System.out.println("Current balance too large. Could not process your transfer...\nHave you considered" +
                                    "retiring?");
                            deposit = false; /*since funds added back to FROM account and customer cannot deposit, both set to false*/
                            withdrew = false;
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet()); /*sends them back to menu*/
                        } else {/*if they CAN deposit, they get here*/
                            customerInterface.getCustomer().getAccount(tempTransferToAccountNumber).deposit(transferAmount);
                            withdrew = true; /*if they make it here, they processed both a deposit into TO account and a withdraw*/
                            deposit = true; /**/
                        }


                        if (withdrew) {
                            System.out.printf("You successfully transferred %.2f from %s %d into account %s %d.\nYour " +
                                            "current balances are %.2f and %.2f respectively.\n", transferAmount,
                                    customerInterface.getCustomer().getAccount(tempTransferFromAccountNumber).getType(),
                                    customerInterface.getCustomer().getAccount(tempTransferFromAccountNumber).getACCOUNT_NUMBER(),
                                    customerInterface.getCustomer().getAccount(tempTransferToAccountNumber).getType(),
                                    customerInterface.getCustomer().getAccount(tempTransferToAccountNumber).getACCOUNT_NUMBER(),
                                    customerInterface.getCustomer().getAccount(tempTransferFromAccountNumber).getBalance(),
                                    customerInterface.getCustomer().getAccount(tempTransferToAccountNumber).getBalance());
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                        }

                    } else if (bankProxy.hasAccount(tempTransferFromAccountNumber)) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(transactionChoice);
                    }


                } else if (!bankProxy.hasAccount(tempTransferToAccountNumber)) {
                    System.out.println("Account not found. Please re-enter your account number.");
                    processTransaction(transactionChoice);
                }


            } else if (transactionChoice.equalsIgnoreCase("ACCOUNTS")) {
                printInformation("ACCOUNTS");


            } else if (transactionChoice.equalsIgnoreCase("RETURN")) {
                    System.out.println("Returning to previous menu.");
                    initiateLoginProcesses();
                }
            } else {
                System.out.println("Request could not be processed. Please try again.");
                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                initiateLoginProcesses();
            }

        } catch (NullPointerException e) {
            System.out.printf("Null pointer caught in LoggedInState : processTransaction");
            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
        }
        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());

    }


    /**
     * getAccountHeaders stores headers used for displaying account information to the user
     *
     * @return String representation of the account information headers.
 * */
    private String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }
}
