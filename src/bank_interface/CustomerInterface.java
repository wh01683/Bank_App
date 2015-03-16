package bank_interface;

import bank_package.Bank;

import java.util.UUID;

/*This "CustomerInterface" class will be the class which primarily interacts with the customer. When the customer
* logs in to the bank system, they will be prompted for their CUSTOMER ID. This is their primary login name. The
* interface will prompt the user to create a new account if they do not have a CUSTOMER ID, or if they supplied a
* CUSTOMER ID which is not found in the hash table. Upon finding the correct customer associated with their CUSTOMER ID,
* the customer interface will prompt the user for their PASSWORD_SCANNER. Their PASSWORD_SCANNER is submitted for verification, comparing
* their input with the PASSWORD_SCANNER string of the CUSTOMER object associated with that CUSTOMER ID. Upon verification, the
* customer may then access the information of all accounts associated with that customer, balances, interest rates, etc.
* They may also apply for additional accounts (CD, IRA, Savings, etc) through this interface. They may also deposit/
* withdraw money from their accounts and transfer money between accounts.
*
*/
public class CustomerInterface {
    static boolean CustomerHasAccount = false;
    private static Bank BANK;
    private static CustomerInterface SINGLETON_INSTANCE;
    private static UUID CustomerUUID;
    CustomerInterfaceState loggedOff;
    CustomerInterfaceState loggedIn;
    CustomerInterfaceState hasAccount;
    CustomerInterfaceState hasNoAccount;
    CustomerInterfaceState hasCorrectUUID;
    CustomerInterfaceState hasIncorrectUUID;


    /*
    * SCANNERS
    * */

    /*private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
    private final uScanner AGE_SCANNER = new uScanner("Please enter your age: ", 14, 99);
    private final uScanner NUM_LATE_PAYMENTS_SCANNER = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private final uScanner CREDIT_INQUIRIES_NUMBER = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private final uScanner CREDIT_BALANCE_SCANNER = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000);
    private final uScanner CREDIT_HISTORY_SCANNER = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private final uScanner CREDIT_LIMIT_SCANNER = new uScanner("Please enter your total credit limit.", -1, 2000000000);

*/
    /*
    * SCANNERS END
    * */

    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/

    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    CustomerInterfaceState currentCustomerInterfaceState;

    private CustomerInterface(Bank newBank) {
        BANK = newBank;
        //uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 35, 37);
        CustomerUUID = UUID.randomUUID();


        loggedOff = new LoggedOff(this);
        hasAccount = new HasAccount(this, newBank);
        hasNoAccount = new HasNoAccount(this, newBank);
        hasCorrectUUID = new HasCorrectUUID(this, newBank);
        hasIncorrectUUID = new HasIncorrectUUID(this, newBank);
        loggedIn = new LoggedIn(this, newBank);

        currentCustomerInterfaceState = hasNoAccount;

        if (CustomerHasAccount) {
            currentCustomerInterfaceState = hasAccount;
        }
    }

    public static CustomerInterface getInstance(Bank thisBank) {
        if (!(SINGLETON_INSTANCE == null)) {
            return SINGLETON_INSTANCE;
        } else
            return new CustomerInterface(thisBank);
    }

    public void START() {

        this.hasAccount(CustomerHasAccount);
        this.enterUUID();
        this.enterPassword();

        while (!(this.equals(loggedOff))) {
            this.loggedIn.requestInformation();
        }


    }

    public UUID getCustomerUUID() {
        return CustomerUUID;
    }

    public void setCustomerUUID(UUID newCustomerUUID) {
        CustomerUUID = newCustomerUUID;
    }

    public void setCustomerInterfaceState(CustomerInterfaceState newCustomerInterfaceState) {
        this.currentCustomerInterfaceState = newCustomerInterfaceState;
    }

    public void setCustomerHasAccount(boolean newHasAccount) {
        CustomerHasAccount = true;

    }

    public void enterUUID() {
        currentCustomerInterfaceState.enterUUID();
    }

    public void enterPassword() {
        currentCustomerInterfaceState.enterPassword();
    }

    public void hasAccount(boolean hasAccount) {
        currentCustomerInterfaceState.hasAccount(hasAccount);
    }

    public void logOff() {
        currentCustomerInterfaceState.logOff();
    }

    public void requestInformation() {

    }

    public void startTransaction() {

    }

    public void addAccount() {

    }

        /*if they do have an account, they are requested to provide their UUID*//*


         *//*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*//*
        CUSTOMER = BANK.requestCustomer(newCustomerID);
        final String realPass = CUSTOMER.getPASSWORD();
         *//*the user is prompted for their password, which is stored in enteredPass*//*
        uScanner PASSWORD_SCANNER = new uScanner("Please enter your password.", 4, 20);
        enteredPass = PASSWORD_SCANNER.alphaNumericStringGet();
        int attempts = 1;
        *//*they will be prompted for their password as long as their REAL PASSWORD and their ENTERED PASSWORD do not match
        * and as long as their attempts do not exceed 4.*//*
        while (!enteredPass.equals(realPass) && attempts < 6) {
            if (attempts == 5) {
                System.out.println("Maximum attempts reached. Exiting.");
                System.exit(1);
            } else {
                System.out.println("Invalid password. Try Again. " + attempts + " of 4 attempts exhausted.");
                attempts++;
                enteredPass = PASSWORD_SCANNER.alphaNumericStringGet();
            }
        }

        if(enteredPass.equals(realPass)){
            System.out.println("Congratulations! Your input password " + enteredPass + " matches your real password" +
                    " on file, " + realPass + "\nYou may now access your bank account information!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            initiateLoginProcesses();
        }

        else{
            System.out.println("Invalid password, you are in a place you should not be. Exiting.");
            System.exit(1);
        }*/


    /*@getInstance
        * this Singleton class utilizes a private constructor and a public method, "getInstance". This method will return
        * the CURRENT INSTANCE of the customer interface class if it does not equal null. else, will return new CustomerInterface
        * constructed with the bank passed through params
        *
        * @param Bank thisBank: needed for construction of the new instance
        * @return new CustomerInterface: returns new instance of CustomerInterface if their is no current instance in existence*/





    /*@initiateLoginProcesses
    *
    * This method is called after the customer is successfully logged in. This is the initial recursive call, the main
    * "menu" if you will. All other methods flow from here, and the "return" request sends the user here.
    *
    * @param not necessary
     * @return void: no return needed.*/
    /*private void initiateLoginProcesses() {
        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + CUSTOMER.getName() + "?\nINFORMATION, TRANSACTION, MKACCT, RMACCT, RMYOURSELF EXIT", 3, 12);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            *//*essentially an infinite loop only ending when the customer exits, runs in to an exit-worthy error, or
            * trips "isLoggedIn" to false.*//*
            String processRequest = PROCESS_REQUEST_SCANNER.stringGet();
            if (processRequest.equalsIgnoreCase("INFORMATION")){
                this.printInformation(INFORMATION_REQUEST_SCANNER.stringGet());
                initiateLoginProcesses();
            }
            else if (processRequest.equalsIgnoreCase("TRANSACTION")) {
                this.processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), CUSTOMER);
                initiateLoginProcesses();
            } else if (processRequest.equalsIgnoreCase("MKACCT")){
                this.addAccount();
                initiateLoginProcesses();
            }else if (processRequest.equalsIgnoreCase("RMACCT")){
                Integer rmAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if(this.BANK.removeAccount(rmAccountNumber)){
                    System.out.printf("Account %10d was successfully removed.", rmAccountNumber);
                    initiateLoginProcesses();
                }
                else{
                    System.out.println("Account not found, try again.");
                    this.BANK.removeAccount(ACCOUNT_NUMBER_SCANNER.intGet());

                }
            } else if (processRequest.equalsIgnoreCase("RMYOURSELF")){
                if(this.BANK.removeCustomer(CUSTOMER)){
                    System.out.printf("%s has been successfully removed from our database along with all of your accounts.\n" +
                            "If this was an error, please type UNDO now.", CUSTOMER.getName());
                    uScanner UNDO_SCANNER = new uScanner("", 4, 4);
                    if(UNDO_SCANNER.stringGet().equalsIgnoreCase("UNDO")){
                        this.BANK.addCustomer(CUSTOMER);
                    }
                    else{
                        System.out.println("We will miss you, Goodbye.");
                    }
                    System.exit(0);
                }
            }
            else if (processRequest.equalsIgnoreCase("EXIT")) {
                System.out.println("Swiggity Swooty, I'm Coming For That Booty!"); *//*a unit exit message, telling me
                the user chose "exit". this is useful when testing random customers.*//*
                System.exit(0);
            } else {
                *//*if the user enters an invalid or unknown process, the method is called recursively and they're allowed
                * to try the process again.*//*
                System.out.println("Your request could not be processed, please try again.");
                initiateLoginProcesses();
            }
    }

    void printInformation(String request) {

        if (request.equalsIgnoreCase("CHEX")){
            System.out.println("Your ChexSystems score is currently " + CUSTOMER.getChexSystemsScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        }
       else if (request.equalsIgnoreCase("CREDIT")){
            System.out.println("Your Credit Score is currently " + CUSTOMER.getCreditScore() + ".");
            printInformation(REQUEST_SCANNER.stringGet());
        }
        else if (request.equalsIgnoreCase("ACCOUNTS")) {
            dataIO.printAccountInformation(CUSTOMER.getAccountHashtable());
            printInformation(REQUEST_SCANNER.stringGet());
        } else if (request.equalsIgnoreCase("ALL")){
            dataIO.printAllCustomerPrivateInformation(CUSTOMER.getUUID().hashCode(), CUSTOMER.getAccountHashtable());
            printInformation(REQUEST_SCANNER.stringGet());
        }
        else if (request.equalsIgnoreCase("RETURN")){
            System.out.println("Returning to previous menu.");
            initiateLoginProcesses();
        }
        else {
            System.out.println("Could not process your request: " + request + " Please try again");
            printInformation(REQUEST_SCANNER.stringGet());
        }
    }*/

    /*@registerNewCustomer
    *
    * Method used to get information from a new user without an UUID.
    * This method is called ONLY IF the user has no UUID AND would like to register
    *
    * @param void: does not require parameters, inquires all the information from inside the method.
    * @return Customer: returns newly created customer using input values to construct the customer.*/


    /*@fillCreditReportInformation
    * This method is used when creating a new customer. This method is only called if the new customer is older than 17 years
    * old. They are requested to fill in their credit information.
    *
    * @param tempAge: age passed to the fill credit report. In reality, it is unnecessary because the user will never
    *                 see this method if their age is less than 18; the tempAge is printed purely for debugging and verification
    * @return new CreditReport: returns a new credit report for the customer, filled in with their provided information*/
    /*CreditReport fillCredReportInformation(int tempAge) {
        double amountOfLatePayments = 0;

        System.out.println("Since you are " + tempAge + " years old, you must provide some credit information.");
        double credLimit = CREDIT_LIMIT_SCANNER.doubleGet();
        double accountBalance = CREDIT_BALANCE_SCANNER.doubleGet();
        int lenCredHistory = CREDIT_HISTORY_SCANNER.intGet();
        int latePaymentsOnRecord = NUM_LATE_PAYMENTS_SCANNER.intGet();
        if (latePaymentsOnRecord > 0)
            amountOfLatePayments = getLatePaymentAmounts(latePaymentsOnRecord);
        int recentCredInquiries = CREDIT_INQUIRIES_NUMBER.intGet();

        return new CreditReport(tempAge, latePaymentsOnRecord, amountOfLatePayments, recentCredInquiries, credLimit,
                accountBalance, lenCredHistory);
    }*/

    /*@getLatePaymentAmounts
    * This method is called by the fillCreditReportInformation method IFF the user had more than 0 late payments on record
    *
    * @param newNumberOfLatePayments: number of latePayments on customer's record
    * @return latePay.doubleGet(): returns a new double value with the total value of late payments on record*//*
    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0, 2000000000);
        return latePay.doubleGet();
    }*/

    /*@addAccount
    *
    * This method is used by the customer to add various accounts to their customer ID. The accounts created are added to the
    * Bank's accountHashTable and also the customer's private accountHashTable
    *
    * @param void: not necessary. if the user gets to this point, their information is already saved in the instance so
    *              can access it without passing new parameters
    * @return void: adds the new bankAccount directly to the customer's account*/
    /*private void addAccount() {

        Account tempAccount = ACCOUNT_FACTORY.getAccount(ACCOUNT_REQUESTER_SCANNER.stringGet(), CUSTOMER);
        if (tempAccount == null) {
            System.out.println("Account type invalid. Please try again.");
            addAccount();
        } else if (!(tempAccount == null)) {
            CUSTOMER.addAccount(tempAccount);
            BANK.addAccount(tempAccount);
            System.out.println("Congratulations, " + CUSTOMER.getName() + "!" + "You successfully added a " + tempAccount.getType() + " account under your name.\n" +
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
        } else
            System.out.println("Something bad happened. Exiting.");
    }



    private void processTransaction(String transactionChoice, Customer loggedInCustomer) {

        Hashtable<Integer, Account> customerAccountHashtable = loggedInCustomer.getAccountHashtable();

        if (!customerAccountHashtable.isEmpty()) {
        *//*DEPOSIT SECTION*//*
            if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
                System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                Integer tempDepositAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if ((tempDepositAccountNumber == -1)) {
                    System.out.println("Returning.\n\n\n");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                }
                Account temp = loggedInCustomer.getAccount(tempDepositAccountNumber);
                if (!(temp == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                    double tempDepositAmount = TRANSACTION_SCANNER.doubleGet();
                    if (temp.getBalance() + tempDepositAmount > Integer.MAX_VALUE) {
                        System.out.println("Current balance too large. Could not process your deposit...\nHave you considered" +
                                "retiring?");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                    }
                    temp.deposit(tempDepositAmount);
                    System.out.printf("Successfully deposited %.2f into %s account number %10d\nYour current balance is: %10.2f",
                            tempDepositAmount, temp.getType(), temp.getACCOUNT_NUMBER(), temp.getBalance());
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                } else if (temp == null) {
                    System.out.println("Account not found. Please re-enter your account number.");
                    processTransaction(transactionChoice, loggedInCustomer);
                }

            }

        *//*WITHDRAW SECTION*//*

            else if (transactionChoice.equalsIgnoreCase("WITHDRAW")) {
                System.out.println("You have chosen " + transactionChoice + ". From which account would you like to " + transactionChoice + "?");
                Integer tempWithdrawAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                if ((tempWithdrawAccountNumber == -1)) {
                    System.out.println("Returning.");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                }
                Account temp = loggedInCustomer.getAccount(tempWithdrawAccountNumber);
                if (!(temp == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 1, 200000000);
                    double tempWithdrawAmount = TRANSACTION_SCANNER.doubleGet();
                    if ((temp.withdraw(tempWithdrawAmount)) == -1) {
                        System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                tempWithdrawAmount, temp.getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                    } else {
                        System.out.printf("You have successfully withdrawn %.2f from %s %10d.\nYour current balance is %.2f.",
                                tempWithdrawAmount, temp.getType(), temp.getACCOUNT_NUMBER(), temp.getBalance());
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                    }

                } else if (temp == null) {
                    System.out.printf("Account %10d not found. Please re-enter your account number.", tempWithdrawAccountNumber);
                    processTransaction(transactionChoice, loggedInCustomer);
                }

            }


        *//*TRANSFER SELECTION*//*
            else if (transactionChoice.equalsIgnoreCase("TRANSFER")) {
                System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                Integer tempTransferToAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();
                boolean deposit = true;
                boolean withdrew = true;

                if ((tempTransferToAccountNumber == -1)) {
                    System.out.println("Returning.");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                }
                Account transferTo = loggedInCustomer.getAccount(tempTransferToAccountNumber);
                if (!(transferTo == null)) {
                    System.out.println("From which account would you like to " + transactionChoice + "?");
                    Integer tempTransferFromAccountNumber = ACCOUNT_NUMBER_SCANNER.intGet();

                    if ((tempTransferFromAccountNumber == -1)) {
                        System.out.println("Returning.");
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                    }
                    Account transferFrom = null;
                    if (loggedInCustomer.getAccountHashtable().containsKey(tempTransferFromAccountNumber)) {
                        transferFrom = loggedInCustomer.getAccount(tempTransferFromAccountNumber);
                    } else {
                        System.out.printf("Could not find desired account number %10d.. please try again.", tempTransferFromAccountNumber);
                    }
                    if (!(transferFrom == null)) {
                        uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 1, 200000000);
                        double transferAmount = TRANSACTION_SCANNER.doubleGet();
                        if (transferFrom.withdraw(transferAmount) == -1) {
                            System.out.printf("Insufficient funds, could not withdraw %.2f. You have %.2f funds available.",
                                    transferAmount, transferFrom.getBalance());
                            withdrew = false;
                        } else {
                            transferFrom.withdraw(transferAmount);
                        }
                        if (transferTo.getBalance() + transferAmount > Integer.MAX_VALUE) {
                            System.out.println("Current balance too large. Could not process your transfer...\nHave you considered" +
                                    "retiring?");
                            transferFrom.deposit(transferAmount); //add funds back to old account.
                            deposit = false;
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                        } else {
                            transferTo.deposit(transferAmount);
                        }

                        if (withdrew && deposit) {
                            System.out.printf("You successfully transferred %.2f from %s %10d into account %s %10d.\nYour" +
                                            "current balances are %.2f and %.2f respectively.", transferAmount, transferFrom.getType(), transferFrom.getACCOUNT_NUMBER(),
                                    transferTo.getType(), transferTo.getACCOUNT_NUMBER(), transferFrom.getBalance(), transferTo.getBalance());
                            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                        }
                        processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                    } else if (transferFrom == null) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(transactionChoice, loggedInCustomer);
                    }

                } else if (transferTo == null) {
                    System.out.println("Account not found. Please re-enter your account number.");
                    processTransaction(transactionChoice, loggedInCustomer);
                }
            } else if (transactionChoice.equalsIgnoreCase("ACCOUNTS")) {
                printInformation("ACCOUNTS");
            } else if (transactionChoice.equalsIgnoreCase("RETURN")) {
                System.out.println("Returning to previous menu.");
                initiateLoginProcesses();
            } else {
                System.out.println("Request could not be processed. Please try again.");
                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
            }

        }

        initiateLoginProcesses();

    }


    String getAccountHeaders() {
        return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }*/

    /*State Pattern Methods*/


}
