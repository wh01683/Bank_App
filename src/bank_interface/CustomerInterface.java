package bank_interface;

import acct.Account;
import acct.AccountFactory;
import bank_package.Bank;
import bank_package.CreditReport;
import bank_package.Customer;
import bank_package.uScanner;

import java.util.Hashtable;
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
    private static final AccountFactory ACCOUNT_FACTORY = new AccountFactory();
    private static Customer CUSTOMER;
    private static Bank BANK;
    private static DataIO dataIO = new DataIO(BANK);
    private static CustomerInterface SINGLETON_INSTANCE;
    /*
    * SCANNERS
    * */
    private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
    private final uScanner AGE_SCANNER = new uScanner("Please enter your age: ", 14, 99);
    private final uScanner NUM_LATE_PAYMENTS_SCANNER = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private final uScanner CREDIT_INQUIRIES_NUMBER = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private final uScanner CREDIT_BALANCE_SCANNER = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000);
    private final uScanner CREDIT_HISTORY_SCANNER = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private final uScanner CREDIT_LIMIT_SCANNER = new uScanner("Please enter your total credit limit.", -1, 2000000000);
    private final uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 35, 37);
    private final uScanner PASSWORD_SCANNER;
    private final uScanner ACCOUNT_REQUESTER_SCANNER = new uScanner("What type of account would you like to add?\nCHECKING, SAVINGS, MMA, IRA, CD", -1, 10);
    private final uScanner HAVE_ACCOUNT_SCANNER = new uScanner("Do you have an account with us? YES, NO, RETURN, EXIT", 2, 6);
    private final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES, NO, RETURN, EXIT", 2, 6);
    private final uScanner INFORMATION_REQUEST_SCANNER = new uScanner("What would you like to know more about?\nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final uScanner TRANSACTION_REQUEST_SCANNER = new uScanner("What transaction would you like to process?\nDEPOSIT, WITHDRAW, TRANSFER, ACCOUNTS, RETURN", 2, 9);
    private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", 0, 200000000);
    private final uScanner REQUEST_SCANNER = new uScanner("\nWhat would you like to know more about?. \nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);

    /*
    * SCANNERS END
    * */

    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/

    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    private CustomerInterface(Bank newBank) {
        BANK = newBank;
        dataIO = new DataIO(BANK);
        UUID newCustID = new UUID(16, 16).randomUUID();
        String enteredPass;
        boolean customerHasAccount = hasAccount();

        if (!customerHasAccount) {

            boolean wantsRegister = wantsToRegister();
         /*if the customer does NOT have an account and the customer WANTS to register, the new customer will be registered
         * the current newCustID of the instance will be set to the newly registered customer's UUID and the new customer is
         * added to the instance's customerHashTable*/
            if (wantsRegister) {
                Customer newCustomer = registerNewCustomer();
                newCustID = newCustomer.getUUID();
                System.out.println("Congratulations, " + newCustomer.getName() + "! You have successfully registered.\nYour new Customer ID is " +
                        newCustID + ". DO NOT LOSE THIS!\nYour password is " + newCustomer.getPASSWORD() + ". You may now log in and experience everything " +
                        "we have to offer!");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            /*TESTING PURPOSES ONLY...*/
                getInstance(BANK); //brings user back to the beginning to allow a normal login attempt
            } else {
            /*if the customer does NOT have an account and they do NOT want to register, the system exits because
            * they are clearly up to no good.*/
                System.out.println("You're clearly up to no good.");
                System.exit(1);
            }
        }

        /*if they do have an account, they are requested to provide their UUID*/
        newCustID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
        if (BANK.requestCustomer(newCustID).equals(null)) {
            /*if the customerHashTable does not contain the provided customer ID, the system will display a prompt
            * and ask them again if they would like to register. If they do not, the user is prompted for their UUID again
            * They are given 5 attempts total before the system exits.*/
            System.out.println("We could not find your ID, please try again.");
            boolean wantsRegister2 = wantsToRegister();
            int uuidCounter = 1;
            while (BANK.requestCustomer(newCustID).equals(null) && uuidCounter < 7) {
                if (uuidCounter == 6) {
                    System.out.println("All attempts exhausted. System exiting.");

                    System.exit(1);
                }
                if (wantsRegister2) {
                    CUSTOMER = registerNewCustomer();
                    newCustID = CUSTOMER.getUUID();
                    /*ToDo: Must implement observer class to update bank's customer table before proceeding from here.*/
                } else if (uuidCounter < 6) {
                    System.out.println(uuidCounter + " attempts remaining of 5. Please try again.");
                    newCustID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
                }
                uuidCounter++;
            }
        }

         /*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*/
        CUSTOMER = BANK.requestCustomer(newCustID);
        final String realPass = CUSTOMER.getPASSWORD();
         /*the user is prompted for their password, which is stored in enteredPass*/
        PASSWORD_SCANNER = new uScanner("Please enter your password.", 4, 16);
        enteredPass = PASSWORD_SCANNER.alphaNumericStringGet();
        int attempts = 1;
        /*they will be prompted for their password as long as their REAL PASSWORD and their ENTERED PASSWORD do not match
        * and as long as their attempts do not exceed 4.*/
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

        boolean loggedIn = enteredPass.equals(realPass);
        System.out.println("Congratulations! Your input password " + enteredPass + " matches your real password" +
                " on file, " + realPass + "\nYou may now access your bank account information!");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        initiateLoginProcesses();
    }


    /*@getInstance
    * this Singleton class utilizes a private constructor and a public method, "getInstance". This method will return
    * the CURRENT INSTANCE of the customer interface class if it does not equal null. else, will return new CustomerInterface
    * constructed with the bank passed through params
    *
    * @param Bank thisBank: needed for construction of the new instance
    * @return new CustomerInterface: returns new instance of CustomerInterface if their is no current instance in existence*/
    public static CustomerInterface getInstance(Bank thisBank) {
        if (!(SINGLETON_INSTANCE == null))
            return SINGLETON_INSTANCE;
        else
            return new CustomerInterface(thisBank);

    }

    /*@initiateLoginProcesses
    *
    * This method is called after the customer is successfully logged in. This is the initial recursive call, the main
    * "menu" if you will. All other methods flow from here, and the "return" request sends the user here.
    *
    * @param not necessary
     * @return void: no return needed.*/
    private void initiateLoginProcesses() {
        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + CUSTOMER.getName() + "?\nINFORMATION, TRANSACTION, ADDACCOUNT, EXIT", 3, 12);
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            /*essentially an infinite loop only ending when the customer exits, runs in to an exit-worthy error, or
            * trips "isLoggedIn" to false.*/
            String processRequest = PROCESS_REQUEST_SCANNER.stringGet();
            if (processRequest.equalsIgnoreCase("INFORMATION")){
                this.printInformation(INFORMATION_REQUEST_SCANNER.stringGet());
                initiateLoginProcesses();
            }
            else if (processRequest.equalsIgnoreCase("TRANSACTION")) {
                this.processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), CUSTOMER);
                initiateLoginProcesses();
            } else if (processRequest.equalsIgnoreCase("ADDACCOUNT")){
                this.addAccount();
                initiateLoginProcesses();
            }
            else if (processRequest.equalsIgnoreCase("EXIT")) {
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

    public void printInformation(String request) {

        if (request.equalsIgnoreCase("CHEX"))
            System.out.println("Your ChexSystems score is currently " + CUSTOMER.getChexSystemsScore() + ".");
        else if (request.equalsIgnoreCase("CREDIT"))
            System.out.println("Your Credit Score is currently " + CUSTOMER.getCreditScore() + ".");
        else if (request.equalsIgnoreCase("ACCOUNTS")) {
            dataIO.printAccountInformation(CUSTOMER.getAccountHashtable());

        } else if (request.equalsIgnoreCase("ALL"))
            dataIO.printAllCustomerPrivateInformation(CUSTOMER.getUUID().hashCode(), CUSTOMER.getAccountHashtable());
        else if (request.equalsIgnoreCase("RETURN"))
            System.out.println("Returning to previous menu.");
        else {
            System.out.println("Could not process your request: " + request + " Please try again");
            printInformation(REQUEST_SCANNER.stringGet());
        }
    }

    /*@registerNewCustomer
    *
    * Method used to get information from a new user without an UUID.
    * This method is called ONLY IF the user has no UUID AND would like to register
    *
    * @param void: does not require parameters, inquires all the information from inside the method.
    * @return Customer: returns newly created customer using input values to construct the customer.*/
    private Customer registerNewCustomer() {
        String tempName = NAME_SCANNER.stringGet();
        int tempAge = AGE_SCANNER.intGet();
        CreditReport tempCreditReport;
        if (tempAge < 18) /*if the user is younger than 18, they do not have a credit report so a default credit report
        is created for the user with all values initiated at 0*/
            tempCreditReport = new CreditReport(0);
        else
            tempCreditReport = fillCredReportInformation(tempAge);
        final uScanner NEW_PASSWORD_SCANNER = new uScanner("Please enter your new custom password for your account.", 5, 20);
        String tempPassword = NEW_PASSWORD_SCANNER.alphaNumericStringGet();

        Customer newCustomer= new Customer(tempName, tempAge, tempPassword, tempCreditReport);
        BANK.addCustomer(newCustomer);
        return newCustomer;
    }

    /*@fillCreditReportInformation
    * This method is used when creating a new customer. This method is only called if the new customer is older than 17 years
    * old. They are requested to fill in their credit information.
    *
    * @param tempAge: age passed to the fill credit report. In reality, it is unnecessary because the user will never
    *                 see this method if their age is less than 18; the tempAge is printed purely for debugging and verification
    * @return new CreditReport: returns a new credit report for the customer, filled in with their provided information*/
    CreditReport fillCredReportInformation(int tempAge) {
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
    }

    /*@getLatePaymentAmounts
    * This method is called by the fillCreditReportInformation method IFF the user had more than 0 late payments on record
    *
    * @param newNumberOfLatePayments: number of latePayments on customer's record
    * @return latePay.doubleGet(): returns a new double value with the total value of late payments on record*/
    private double getLatePaymentAmounts(int newNumberOfLatePayments) {
        uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                + "Please enter the total amount of the late payments.", 0, 2000000000);
        return latePay.doubleGet();
    }

    /*@addAccount
    *
    * This method is used by the customer to add various accounts to their customer ID. The accounts created are added to the
    * Bank's accountHashTable and also the customer's private accountHashTable
    *
    * @param void: not necessary. if the user gets to this point, their information is already saved in the instance so
    *              can access it without passing new parameters
    * @return void: adds the new bankAccount directly to the customer's account*/
    private void addAccount() {

        Account tempAccount = ACCOUNT_FACTORY.getAccount(ACCOUNT_REQUESTER_SCANNER.stringGet(), CUSTOMER);
        if (tempAccount == null) {
            System.out.println("Account type invalid. Please try again.");
            addAccount();
        } else if (!(tempAccount == null)) {
            CUSTOMER.addAccount(tempAccount);
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

    /*@hasAccount
    *
    * This method asks the user whether they have an account or not, accepting a non-case-sensitive yes for true and
    * a non-case-sensitive no as false
    *
    * @param void: not required
    * @return boolean: returns user's answer in boolean form. will ALWAYS return true or false, the method calls itself
    * recursively until the user can enter a valid response.*/
    private boolean hasAccount() {
        String answer = this.HAVE_ACCOUNT_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES"))
            return true;

        else if (answer.equalsIgnoreCase("NO"))
            return false;

        else if (answer.equalsIgnoreCase("RETURN")) {
            getInstance(BANK);
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");

        return hasAccount();

    }

    /*@hasAccount
       *
       * This method asks the user whether they would like to register or not, accepting a non-case-sensitive
       * yes for true and a non-case-sensitive no as false
       *
       * @param void: not required
       * @return boolean: returns user's answer in boolean form*/
    private boolean wantsToRegister() {
        String answer = this.WANT_REGISTER_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES"))
            return true;
        else if (answer.equalsIgnoreCase("NO"))
            return false;
        else if (answer.equalsIgnoreCase("RETURN")) {
            getInstance(BANK);
        } else if (answer.equalsIgnoreCase("EXIT")) {
            System.out.println("Exiting.");
            System.exit(0);
        } else
            System.out.println("Incorrect response.");
        return wantsToRegister();

    }

    private void processTransaction(String transactionChoice, Customer loggedInCustomer) {

        Hashtable<Integer, Account> customerAccountHashtable = loggedInCustomer.getAccountHashtable();

        if (!customerAccountHashtable.isEmpty()) {
        /*DEPOSIT SECTION*/
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

        /*WITHDRAW SECTION*/

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


        /*TRANSFER SELECTION*/
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
    }
}
