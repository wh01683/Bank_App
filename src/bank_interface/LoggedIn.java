package bank_interface;

import acct.AccountFactory;
import bank_package.Bank;
import bank_package.uScanner;

import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by robert on 3/15/2015.
 */
public class LoggedIn implements CustomerInterfaceState {

    private final uScanner ACCOUNT_REQUESTER_SCANNER = new uScanner("What type of account would you like to add?\nCHECKING, SAVINGS, MMA, IRA, CD", -1, 10);
    private final uScanner INFORMATION_REQUEST_SCANNER = new uScanner("What would you like to know more about?\nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final uScanner TRANSACTION_REQUEST_SCANNER = new uScanner("What transaction would you like to process?\nDEPOSIT, WITHDRAW, TRANSFER, ACCOUNTS, RETURN", 2, 9);
    private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", -1, 200000000);
    private final uScanner REQUEST_SCANNER = new uScanner("\nWhat would you like to know more about?. \nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);

    CustomerInterface customerInterface;
    Bank bank;
    UUID customerUUID;
    DataIO dataIO;
    AccountFactory accountFactory = new AccountFactory();

    public LoggedIn(CustomerInterface newCustomerInterface, Bank newBank) {
        this.bank = newBank;
        this.customerInterface = newCustomerInterface;
        dataIO = new DataIO(newBank);
        this.customerUUID = this.customerInterface.getCustomerUUID();
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
    }

    @Override
    public void addAccount() {

        String accountRequest = ACCOUNT_REQUESTER_SCANNER.stringGet();

        if (accountFactory.getAccount(ACCOUNT_REQUESTER_SCANNER.stringGet(), bank.requestCustomer(this.customerUUID)) == null) {
            System.out.println("Account type invalid. Please try again.");
            addAccount();
        } else if (!(accountFactory.getAccount(ACCOUNT_REQUESTER_SCANNER.stringGet(), bank.requestCustomer(this.customerUUID)) == null)) {
            this.bank.addAccount(accountFactory.getAccount(accountRequest, bank.requestCustomer(this.customerUUID)));
            bank.requestCustomer(this.customerUUID).addAccount(accountFactory.getAccount(accountRequest, bank.requestCustomer(this.customerUUID)));
            System.out.println("Congratulations, " + bank.requestCustomer(this.customerUUID).getName() + "!" + "You successfully added a " + accountFactory.getAccount(accountRequest,
                    bank.requestCustomer(this.customerUUID)).getType() + " account under your name.\nHere is the information...\n");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(getAccountHeaders());
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(accountFactory.getAccount(accountRequest, bank.requestCustomer(this.customerUUID)).toString());
            System.out.println("\n");
            uScanner yesNo = new uScanner("Would you like to add more accounts?\nYES to create additional accounts, NO to return.", 1, 4);
            if (yesNo.stringGet().equalsIgnoreCase("YES"))
                addAccount();
            else
                initiateLoginProcesses();
        } else
            System.out.println("Something bad happened. Exiting.");
    }

    private void initiateLoginProcesses() {
        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + bank.requestCustomer(this.customerUUID).getName() + "?\nINFORMATION, TRANSACTION, MKACCT, RMACCT, RMYOURSELF EXIT", 3, 12);
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
            if (this.bank.removeAccount(rmAccountNumber)) {
                System.out.printf("Account %10d was successfully removed.", rmAccountNumber);
                initiateLoginProcesses();
            } else {
                System.out.println("Account not found, try again.");
                this.bank.removeAccount(ACCOUNT_NUMBER_SCANNER.intGet());

            }
        } else if (processRequest.equalsIgnoreCase("RMYOURSELF")) {
            if (this.bank.removeCustomer(bank.requestCustomer(this.customerUUID))) {
                System.out.printf("%s has been successfully removed from our database along with all of your accounts.\n" +
                        "If this was an error, please type UNDO now.", bank.requestCustomer(this.customerUUID).getName());
                uScanner UNDO_SCANNER = new uScanner("Stay with us? UNDO", 4, 4);
                if (UNDO_SCANNER.stringGet().equalsIgnoreCase("UNDO")) {
                    this.bank.addCustomer(bank.requestCustomer(this.customerUUID));
                } else {
                    System.out.println("We will miss you, Goodbye.");
                }
                System.exit(0);
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
            System.out.println("Your ChexSystems score is currently " + bank.requestCustomer(this.customerUUID).getChexSystemsScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("CREDIT")) {
            System.out.println("Your Credit Score is currently " + bank.requestCustomer(this.customerUUID).getCreditScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ACCOUNTS")) {
            dataIO.printAccountInformation(bank.requestCustomer(this.customerUUID).getAccountHashtable());
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ALL")) {
            dataIO.printAllCustomerPrivateInformation(bank.requestCustomer(this.customerUUID).getUUID().hashCode(), bank.requestCustomer(this.customerUUID).getAccountHashtable());
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

        Hashtable customerAccountHashtable = bank.requestCustomer(this.customerUUID).getAccountHashtable();

        if (!customerAccountHashtable.isEmpty()) {
        /*DEPOSIT SECTION*/
            if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
                System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                Integer tempWithdrawAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if ((tempWithdrawAccountNumber == -1)) {
                    System.out.println("Returning.\n\n\n");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                }
                if (!(bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                    double tempDepositAmount = TRANSACTION_SCANNER.doubleGet();
                    if (bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getBalance() + tempDepositAmount > Integer.MAX_VALUE) {
                        System.out.println("Current balance too large. Could not process your deposit...\nHave you considered" +
                                "retiring?");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }
                    bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).deposit(tempDepositAmount);
                    System.out.printf("Successfully deposited %.2f into %s account number %10d\nYour current balance is: %10.2f",
                            tempDepositAmount, bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getType(),
                            bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                            bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getBalance());
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                } else if (bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber) == null) {
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
                if (!(bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber) == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 1, 200000000);
                    double tempWithdrawAmount = TRANSACTION_SCANNER.doubleGet();
                    if ((bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).withdraw(tempWithdrawAmount)) == -1) {
                        System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                tempWithdrawAmount, bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else {
                        System.out.printf("You have successfully withdrawn %.2f from %s %10d.\nYour current balance is %.2f.",
                                tempWithdrawAmount, this.bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getType(),
                                bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getACCOUNT_NUMBER(),
                                bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber).getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    }

                } else if (bank.requestCustomer(this.customerUUID).getAccount(tempWithdrawAccountNumber) == null) {
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
                if (!(bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber) == null)) {
                    System.out.println("From which account would you like to " + transactionChoice + "?");
                    Integer tempTransferFromAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();

                    if ((tempTransferFromAccountNumber == -1)) {
                        System.out.println("Returning.");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else {
                        System.out.printf("Could not find desired account number %10d.. please try again.", tempTransferFromAccountNumber);
                    }
                    if (!(bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber) == null)) {
                        uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                        double transferAmount = TRANSACTION_SCANNER.doubleGet();
                        if (bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).withdraw(transferAmount) == -1) {
                            System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                    transferAmount, this.bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).getBalance());
                            withdrew = false;
                        } else {
                            bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).withdraw(transferAmount);
                        }
                        if (bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber).getBalance() + transferAmount > Integer.MAX_VALUE) {
                            System.out.println("Current balance too large. Could not process your transfer...\nHave you considered" +
                                    "retiring?");
                            bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).deposit(transferAmount); //add funds back to old account.
                            deposit = false;
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                        } else {
                            bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber).deposit(transferAmount);
                        }

                        if (withdrew && deposit) {
                            System.out.printf("You successfully transferred %.2f from %s %10d into account %s %10d.\nYour" +
                                            "current balances are %.2f and %.2f respectively.", transferAmount,
                                    bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).getType(),
                                    bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).getACCOUNT_NUMBER(),
                                    bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber).getType(),
                                    bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber).getACCOUNT_NUMBER(),
                                    bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber).getBalance(),
                                    bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber).getBalance());
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                        }
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet());
                    } else if (bank.requestCustomer(this.customerUUID).getAccount(tempTransferFromAccountNumber) == null) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(transactionChoice);
                    }

                } else if (bank.requestCustomer(this.customerUUID).getAccount(tempTransferToAccountNumber) == null) {
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
