package bank_interface;

import acct.Account;
import acct.AccountFactory;
import bank_package.BankProxy;
import utility.uScanner;

import java.util.Hashtable;

/**
 * Created by robert on 3/15/2015.
 */
class LoggedIn implements CustomerInterfaceState {

    private static BankProxy bankProxy;
    private final uScanner ACCOUNT_REQUESTER_SCANNER = new uScanner("What type of account would you like to add?\nCHECKING, SAVINGS, MMA, IRA, CD, RETURN", -1, 10);
    private final uScanner INFORMATION_REQUEST_SCANNER = new uScanner("What would you like to know more about?\nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final uScanner TRANSACTION_REQUEST_SCANNER = new uScanner("What transaction would you like to process?\nDEPOSIT, WITHDRAW, TRANSFER, ACCOUNTS, RETURN", 2, 9);
    private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", -2, 200000000);
    private final uScanner REQUEST_SCANNER = new uScanner("\nWhat would you like to know more about?. \nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final CustomerInterface customerInterface;
    private final DataIO dataIO;
    private final AccountFactory accountFactory = new AccountFactory();


    public LoggedIn(CustomerInterface newCustomerInterface, BankProxy newBankProxy, DataIO newDataIO) {
        bankProxy = newBankProxy;
        dataIO = newDataIO;
        this.customerInterface = newCustomerInterface;
    }


    @Override
    public void enterUUID() {
        System.out.println("You are currently logged in.");
    }

    @Override
    public void enterPassword() {
        System.out.println("You are currently logged in.");
    }

    @Override
    public void hasAccount(boolean isRegistered) {
        System.out.println("You are currently logged in.");
    }

    @Override
    public void logOff() {
        System.out.println("Have a great day!");
        this.customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
    }

    @Override
    public void requestInformation() {
        initiateLoginProcesses();
    }

    @Override
    public void startTransaction() {
        this.processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
    }

    @Override
    public void addAccount() {

        String accountRequest = ACCOUNT_REQUESTER_SCANNER.stringGet();
        if (accountRequest.equalsIgnoreCase("RETURN")) {
            customerInterface.requestInformation();
        } else {

            Account tempAccount = accountFactory.getAccount(accountRequest, bankProxy.requestCustomer(customerInterface.getCustomerUUID()));
            if (tempAccount == null) {
                System.out.println("Account type invalid. Please try again.");
                addAccount();
            } else if (!(tempAccount == null)) {
                bankProxy.addAccount(tempAccount);
                bankProxy.requestCustomer(customerInterface.getCustomerUUID()).addAccount(tempAccount);
                System.out.println("Congratulations, " + bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getName() +
                        "!" + "You successfully added a " + tempAccount.getType() + " account under your name.\n" +
                        "Here is the information...\n");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(tempAccount.toString());
                System.out.println("\n");
                uScanner yesNo = new uScanner("Would you like to add more accounts?\nYES to create additional accounts, NO to return.", 1, 4);
                if (yesNo.stringGet().equalsIgnoreCase("YES"))
                    addAccount();
                else
                    initiateLoginProcesses();
            }

            customerInterface.requestInformation();

        }
    }

    private void initiateLoginProcesses() {
        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + customerInterface.getBankProxy().requestCustomer(customerInterface.getCustomerUUID()).getName() +
                "?\nINFORMATION, TRANSACTION, MKACCT, RMACCT, EXIT", 3, 12);
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
        } else if (processRequest.equalsIgnoreCase("EXIT")) {
            System.out.println("Swiggity Swooty, I'm Coming For That Booty!"); /*a unit exit message, telling me
                the user chose "exit". this is useful when testing random customers.*/
            System.exit(0);
        } else {
                /*if the user enters an invalid or unknown process, the method is called recursively and they're allowed
                * to try the process again.*/
            System.out.println("Your request could not be processed, please try again.");
            initiateLoginProcesses();
        }
    }

    void printInformation(String request) {

        if (request.equalsIgnoreCase("CHEX")) {
            System.out.println("Your ChexSystems score is currently " + bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getChexSystemsScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("CREDIT")) {
            System.out.println("Your Credit Score is currently " + bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getCreditScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ACCOUNTS")) {
            dataIO.printAccountInformation(bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable());
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ALL")) {
            dataIO.printAllCustomerPrivateInformation(bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getUUID().hashCode(), bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable());
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("RETURN")) {
            System.out.println("Returning to previous menu.");
            initiateLoginProcesses();
        } else {
            System.out.println("Could not process your request: " + request + " Please try again");
            printInformation(REQUEST_SCANNER.stringGet());
        }
    }

    private void processTransaction(String transactionChoice) {

        Hashtable customerAccountHashtable = bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable();

        if (!customerAccountHashtable.isEmpty()) {
        /*DEPOSIT SECTION*/
            if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
                System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                Integer tempWithdrawAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if ((tempWithdrawAccountNumber == -1)) {
                    System.out.println("Returning.\n\n\n");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                }
                if (!(bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                    double tempDepositAmount = TRANSACTION_SCANNER.doubleGet();
                    if (bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance() + tempDepositAmount > Integer.MAX_VALUE) {
                        System.out.println("Current balance too large. Could not process your deposit...\nHave you considered" +
                                "retiring?");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }
                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).deposit(tempDepositAmount);
                    System.out.printf("Successfully deposited %.2f into %s account number %d.\nYour current balance is: %.2f",
                            tempDepositAmount, bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getType(),
                            bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                            bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance());
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                } else if (bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null) {
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
                if (!(bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 1, 200000000);
                    double tempWithdrawAmount = TRANSACTION_SCANNER.doubleGet();
                    if ((bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).withdraw(tempWithdrawAmount)) == -1) {
                        System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                tempWithdrawAmount, bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else {
                        System.out.printf("You have successfully withdrawn %.2f from %s %10d.\nYour current balance is %.2f.",
                                tempWithdrawAmount, bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getType(),
                                bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                                bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }

                } else if (bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null) {
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


                    } else if (tempTransferFromAccountNumber == tempTransferToAccountNumber) { /*checks for the same account number*/
                        System.out.println("You may not transfer to the same account.");
                        startTransaction();


                    } else if (bankProxy.hasAccount(tempTransferFromAccountNumber)) { /*checks From account again*/

                        uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                        double transferAmount = TRANSACTION_SCANNER.doubleGet();

                        if (bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).withdraw(transferAmount) == -1) {
                                /*if withdrawal will result in a negative 1 balance, will not let them withdraw*/
                            System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.\n",
                                    transferAmount, bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getBalance());
                            bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable().get(tempTransferFromAccountNumber).deposit(transferAmount);
                            withdrew = false; /*transfer will not continue if this is false*/


                        } else { /*if they CAN withdraw, they make it here*/
                            bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).withdraw(transferAmount);
                        }/*if they have too much money, they cannot deposit and thus cannot withdraw.*/


                        if ((bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getBalance() + transferAmount) > Integer.MAX_VALUE) {
                            System.out.println("Current balance too large. Could not process your transfer...\nHave you considered" +
                                    "retiring?");
                            deposit = false; /*since funds added back to FROM account and customer cannot deposit, both set to false*/
                            withdrew = false;
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet()); /*sends them back to menu*/
                        } else {/*if they CAN deposit, they get here*/
                            bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).deposit(transferAmount);
                            withdrew = true; /*if they make it here, they processed both a deposit into TO account and a withdraw*/
                            deposit = true; /**/
                        }


                        if (withdrew && deposit) {
                            System.out.printf("You successfully transferred %.2f from %s %d into account %s %d.\nYour " +
                                            "current balances are %.2f and %.2f respectively.\n", transferAmount,
                                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getType(),
                                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getACCOUNT_NUMBER(),
                                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getType(),
                                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getACCOUNT_NUMBER(),
                                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getBalance(),
                                    bankProxy.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getBalance());
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


            } else {
                System.out.println("Request could not be processed. Please try again.");
                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                initiateLoginProcesses();
            }

        }

        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());

    }




    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }
}
