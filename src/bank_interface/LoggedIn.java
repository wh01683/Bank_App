package bank_interface;

import acct.Account;
import acct.AccountFactory;
import bank_package.Bank;
import bank_package.uScanner;

import java.util.Hashtable;

/**
 * Created by robert on 3/15/2015.
 */
public class LoggedIn implements CustomerInterfaceState {

    private static Bank bank;
    private final uScanner ACCOUNT_REQUESTER_SCANNER = new uScanner("What type of account would you like to add?\nCHECKING, SAVINGS, MMA, IRA, CD, RETURN", -1, 10);
    private final uScanner INFORMATION_REQUEST_SCANNER = new uScanner("What would you like to know more about?\nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final uScanner TRANSACTION_REQUEST_SCANNER = new uScanner("What transaction would you like to process?\nDEPOSIT, WITHDRAW, TRANSFER, ACCOUNTS, RETURN", 2, 9);
    private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", -1, 200000000);
    private final uScanner REQUEST_SCANNER = new uScanner("\nWhat would you like to know more about?. \nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final CustomerInterface customerInterface;
    private final DataIO dataIO;
    private final AccountFactory accountFactory = new AccountFactory();

    public LoggedIn(CustomerInterface newCustomerInterface, Bank newBank) {
        bank = newBank;
        this.customerInterface = newCustomerInterface;
        dataIO = new DataIO(newBank);
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

            Account tempAccount = accountFactory.getAccount(accountRequest, bank.requestCustomer(customerInterface.getCustomerUUID()));
            if (tempAccount.equals(null)) {
                System.out.println("Account type invalid. Please try again.");
                addAccount();
            } else if (!(tempAccount == null)) {
                bank.addAccount(tempAccount);
                bank.requestCustomer(customerInterface.getCustomerUUID()).addAccount(tempAccount);
                System.out.println("Congratulations, " + bank.requestCustomer(customerInterface.getCustomerUUID()).getName() +
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
        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + customerInterface.getBANK().requestCustomer(customerInterface.getCustomerUUID()).getName() + "?\nINFORMATION, TRANSACTION, MKACCT, RMACCT, RMYOURSELF EXIT", 3, 12);
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
            Integer rmAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
            if (bank.removeAccount(rmAccountNumber)) {
                System.out.printf("Account %10d was successfully removed.", rmAccountNumber);
                initiateLoginProcesses();
            } else {
                System.out.println("Account not found, try again.");
                bank.removeAccount(ACCOUNT_NUMBER_SCANNER.intGet());

            }
        } else if (processRequest.equalsIgnoreCase("RMYOURSELF")) {
            if (bank.removeCustomer(bank.requestCustomer(customerInterface.getCustomerUUID()))) {
                System.out.printf("%s has been successfully removed from our database along with all of your accounts.\n" +
                        "If this was an error, please type UNDO now.", bank.requestCustomer(customerInterface.getCustomerUUID()).getName());
                uScanner UNDO_SCANNER = new uScanner("Stay with us? UNDO", 4, 4);
                if (UNDO_SCANNER.stringGet().equalsIgnoreCase("UNDO")) {
                    bank.addCustomer(bank.requestCustomer(customerInterface.getCustomerUUID()));
                } else {
                    System.out.println("We will miss you, Goodbye.");
                    customerInterface.setCustomerInterfaceState(customerInterface.loggedOff);
                    customerInterface.requestInformation();
                }
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
            System.out.println("Your ChexSystems score is currently " + bank.requestCustomer(customerInterface.getCustomerUUID()).getChexSystemsScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("CREDIT")) {
            System.out.println("Your Credit Score is currently " + bank.requestCustomer(customerInterface.getCustomerUUID()).getCreditScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ACCOUNTS")) {
            dataIO.printAccountInformation(bank.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable());
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ALL")) {
            dataIO.printAllCustomerPrivateInformation(bank.requestCustomer(customerInterface.getCustomerUUID()).getUUID().hashCode(), bank.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable());
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

        Hashtable customerAccountHashtable = bank.requestCustomer(customerInterface.getCustomerUUID()).getAccountHashtable();

        if (!customerAccountHashtable.isEmpty()) {
        /*DEPOSIT SECTION*/
            if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
                System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                Integer tempWithdrawAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if ((tempWithdrawAccountNumber == -1)) {
                    System.out.println("Returning.\n\n\n");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                }
                if (!(bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                    double tempDepositAmount = TRANSACTION_SCANNER.doubleGet();
                    if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance() + tempDepositAmount > Integer.MAX_VALUE) {
                        System.out.println("Current balance too large. Could not process your deposit...\nHave you considered" +
                                "retiring?");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }
                    bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).deposit(tempDepositAmount);
                    System.out.printf("Successfully deposited %.2f into %s account number %10d\nYour current balance is: %10.2f",
                            tempDepositAmount, bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getType(),
                            bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                            bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance());
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                } else if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null) {
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
                if (!(bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 1, 200000000);
                    double tempWithdrawAmount = TRANSACTION_SCANNER.doubleGet();
                    if ((bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).withdraw(tempWithdrawAmount)) == -1) {
                        System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                tempWithdrawAmount, bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else {
                        System.out.printf("You have successfully withdrawn %.2f from %s %10d.\nYour current balance is %.2f.",
                                tempWithdrawAmount, bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getType(),
                                bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                                bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }

                } else if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempWithdrawAccountNumber) == null) {
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

                if ((tempTransferToAccountNumber == -1)) {
                    System.out.println("Returning.");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                }
                if (!(bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber) == null)) {
                    System.out.println("From which account would you like to " + transactionChoice + "?");
                    Integer tempTransferFromAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();

                    if ((tempTransferFromAccountNumber == -1)) {
                        System.out.println("Returning.");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else if (!bank.getAccountHashTable().containsKey(tempTransferFromAccountNumber)) {
                        System.out.printf("Could not find desired account number %10d... please try again.", tempTransferFromAccountNumber);
                        } else if (tempTransferFromAccountNumber.equals(tempTransferToAccountNumber)) {
                        System.out.println("You may not transfer to the same account.");
                        startTransaction();
                    } else {
                        if (bank.getAccountHashTable().containsKey(tempTransferFromAccountNumber)) {
                            uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                            double transferAmount = TRANSACTION_SCANNER.doubleGet();
                            if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).withdraw(transferAmount) == -1) {
                                System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.\n",
                                        transferAmount, bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getBalance());
                                withdrew = false;
                            } else {
                                bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).withdraw(transferAmount);
                            }
                            if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getBalance() + transferAmount > Integer.MAX_VALUE) {
                                System.out.println("Current balance too large. Could not process your transfer...\nHave you considered" +
                                        "retiring?");
                                bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).deposit(transferAmount); //add funds back to old account.
                                deposit = false;
                                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                            } else {
                                bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).deposit(transferAmount);
                            }

                            if (withdrew && deposit) {
                                System.out.printf("You successfully transferred %.2f from %s %10d into account %s %10d.\nYour " +
                                                "current balances are %.2f and %.2f respectively.\n", transferAmount,
                                        bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getType(),
                                        bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getACCOUNT_NUMBER(),
                                        bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getType(),
                                        bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getACCOUNT_NUMBER(),
                                        bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber).getBalance(),
                                        bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber).getBalance());
                                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                            }
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                        } else if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferFromAccountNumber) == null) {
                            System.out.println("Account not found. Please re-enter your account number.");
                            processTransaction(transactionChoice);
                        }
                    }

                } else if (bank.requestCustomer(customerInterface.getCustomerUUID()).getAccount(tempTransferToAccountNumber) == null) {
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
            }

        }

        initiateLoginProcesses();

    }


    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }
}
