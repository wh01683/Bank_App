package bank_interface;

import acct.Account;
import acct.AccountFactory;
import bank_package.*;

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
    private static Customer cust;
    private static Bank bank;
    private static Hashtable<Integer, Customer> customerHashtable;
    private static CustomerInterface SINGLETON_INSTANCE;
    private static UUID newCustID;
    private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
    private final uScanner AGE_SCANNER = new uScanner("Please enter your age: ", 14, 99);
    private final uScanner NUM_LATE_PAYMENTS_SCANNER = new uScanner("Please enter total number of late payments you've made, if any: ", -1, 101);
    private final uScanner CREDIT_INQUIRIES_NUMBER = new uScanner("Please enter the number of recent credit inquiries: ", -1, 99);
    private final uScanner CREDIT_BALANCE_SCANNER = new uScanner("Please enter your current outstanding credit card balance.", -1, 2000000000.0);
    private final uScanner CREDIT_HISTORY_SCANNER = new uScanner("Please enter the length of your credit history in years: ", -1, 99);
    private final uScanner CREDIT_LIMIT_SCANNER = new uScanner("Please enter your total credit limit.", -1.0, 2000000000.0);
    private final uScanner UUID_SCANNER = new uScanner("Please enter the Customer ID you received when you registered.", 35, 37);
    private final uScanner PASSWORD_SCANNER = new uScanner("Please enter your password.", 4, 16);
    private final uScanner ACCOUNT_REQUESTER_SCANNER = new uScanner("What type of account would you like to add?\nCHECKING, SAVINGS, MMA, IRA, CD", -1, 10);
    private final uScanner HAVE_ACCOUNT_SCANNER = new uScanner("Do you have an account with us? YES or NO?", -1, 4);
    private final uScanner WANT_REGISTER_SCANNER = new uScanner("Would you like to register? YES or NO?", -1, 4);
    private final uScanner INFORMATION_REQUEST_SCANNER = new uScanner("What would you like to know more about?\nCHEX, CREDIT, ACCOUNTS, ALL, RETURN", 2, 9);
    private final uScanner TRANSACTION_REQUEST_SCANNER = new uScanner("What transaction would you like to process?\nDEPOSIT, WITHDRAW, TRANSFER, ACCOUNTS, RETURN", 2, 9);
    private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", 0, 200000000);




    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/

    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    private CustomerInterface(Bank newBank) {
        bank = newBank;
        customerHashtable = bank.getCustomerTable();
        newCustID = new UUID(16, 16).randomUUID();
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
                customerHashtable.put(newCustID.hashCode(), newCustomer);
                System.out.println("Congratulations, " + newCustomer.getName() + "! You have successfully registered.\nYour new Customer ID is " +
                        newCustID + ". DO NOT LOSE THIS!\nYour password is " + newCustomer.getPASSWORD() + ". You may now log in and experience everything " +
                        "we have to offer!");
            /*TESTING PURPOSES ONLY...*/
                customerHasAccount = true;
                getInstance(newBank);
            } else if (!customerHasAccount && !wantsRegister) {
            /*if the customer does NOT have an account and they do NOT want to register, the system exits because
            * they are clearly up to no good.*/
                System.out.println("You're clearly up to no good.");
                System.exit(1);
            } else {
                System.out.println("Something went wrong. Exiting.");
                System.exit(1);
            }
        }

        if (customerHasAccount) {
            /*if they do have an account, they are requested to provide their UUID*/
            newCustID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
        }
        if (!customerHashtable.containsKey(newCustID.hashCode())) {
            /*if the customerHashTable does not contain the provided customer ID, the system will display a prompt
            * and ask them again if they would like to register. If they do not, the user is prompted for their UUID again
            * They are given 5 attempts total before the system exits.*/
            System.out.println("We could not find your ID, please try again.");
            boolean wantsRegister2 = wantsToRegister();
            int uuidCounter = 1;
            while (!customerHashtable.containsKey(newCustID.hashCode()) && uuidCounter < 7) {
                if (uuidCounter == 6) {
                    System.out.println("All attempts exhausted. System exiting.");
                    System.exit(1);
                }
                if (wantsRegister2) {
                    cust = registerNewCustomer();
                    newCustID = cust.getUUID();
                    /*ToDo: Must implement observer class to update bank's customer table before proceeding from here.*/
                } else if (!wantsRegister2 && uuidCounter < 6) {
                    System.out.println(uuidCounter + " attempts remaining of 5. Please try again.");
                    newCustID = UUID.fromString(UUID_SCANNER.alphaNumericStringGet());
                }
                uuidCounter++;
            }
        }

         /*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*/
        cust = customerHashtable.get(newCustID.hashCode());
        final String realPass = cust.getPASSWORD();
         /*the user is prompted for their password, which is stored in enteredPass*/
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

        initiateLoginProcesses(loggedIn, cust);
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

    private void initiateLoginProcesses(boolean isLoggedIn, Customer loggedInCustomer) {
        final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + loggedInCustomer.getName() + "?\nINFORMATION, TRANSACTION, ADDACCOUNT, EXIT", 3, 12);

        while (isLoggedIn) {
            String processRequest = PROCESS_REQUEST_SCANNER.stringGet();
            if (processRequest.equalsIgnoreCase("INFORMATION"))
                this.showCustomerAccountInformation(loggedInCustomer);
            else if (processRequest.equalsIgnoreCase("TRANSACTION")) {
                while (!(this.processTransaction(processRequest, loggedInCustomer) == 0))
                    initiateLoginProcesses(true, loggedInCustomer);
            } else if (processRequest.equalsIgnoreCase("ADDACCOUNT"))
                this.addAccount(loggedInCustomer);
            else if (processRequest.equalsIgnoreCase("EXIT")) {
                isLoggedIn = false;
                System.out.println("Exiting..");
                System.exit(1);
            } else {
                System.out.println("Your request could not be processed, please try again.");
                initiateLoginProcesses(true, loggedInCustomer);
            }
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
        ChexSystems tempScore = new ChexSystems();
        final uScanner NEW_PASSWORD_SCANNER = new uScanner("Please enter your new custom password for your account.", 5, 20);
        String tempPassword = NEW_PASSWORD_SCANNER.alphaNumericStringGet();

        return new Customer(tempName, tempAge, tempPassword, tempCreditReport, tempScore);
    }

    /*@fillCreditReportInformation
    * This method is used when creating a new customer. This method is only called if the new customer is older than 17 years
    * old. They are requested to fill in their credit information.
    *
    * @param tempAge: age passed to the fill credit report. In reality, it is unnecessary because the user will never
    *                 see this method if their age is less than 18; the tempAge is printed purely for debugging and verification
    * @return new CreditReport: returns a new credit report for the customer, filled in with their provided information*/
    public CreditReport fillCredReportInformation(int tempAge) {
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
                + "Please enter the total amount of the late payments.", 0.0, 2000000000.0);
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
    private void addAccount(Customer loggedInCustomer) {
        Account tempAccount = ACCOUNT_FACTORY.getAccount(ACCOUNT_REQUESTER_SCANNER.stringGet(), loggedInCustomer);
        loggedInCustomer.addAccount(tempAccount);
        System.out.println("Congratulations, " + loggedInCustomer.getName() + "!" + "You successfully added a " + tempAccount.getType() + " account under your name.\n" +
                "Here is the information...\n");
        System.out.println(getAccountHeaders());
        System.out.println(tempAccount.toString());
    }

    /*@hasAccount
    *
    * This method asks the user whether they have an account or not, accepting a non-case-sensitive yes for true and
    * a non-case-sensitive no as false
    *
    * @param void: not required
    * @return boolean: returns user's answer in boolean form*/
    private boolean hasAccount() {
        String answer = this.HAVE_ACCOUNT_SCANNER.stringGet();

        if (answer.equalsIgnoreCase("YES"))
            return true;

        else if (answer.equalsIgnoreCase("NO"))
            return false;

        else return false;
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

        else return false;
    }

    private void showCustomerAccountInformation(Customer loggedInCustomer) {

        loggedInCustomer.printInformation(INFORMATION_REQUEST_SCANNER.stringGet());

    }

    private int processTransaction(String transactionChoice, Customer loggedInCustomer) {

        if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
            System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
            Integer tempAccountNumber1 = ACCOUNT_NUMBER_SCANNER.intGet();
            if ((tempAccountNumber1 == -1)) {
                System.out.println("Returning.\n");
                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
            }
            Account temp = loggedInCustomer.getAccount(tempAccountNumber1);
            if (!(temp == null)) {
                uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 0.0, 200000000.0);
                temp.deposit(TRANSACTION_SCANNER.doubleGet());
                return 1;
            } else if (temp == null) {
                System.out.println("Account not found. Please re-enter your account number.");
                processTransaction(transactionChoice, loggedInCustomer);
            }

        } else if (transactionChoice.equalsIgnoreCase("WITHDRAW")) {
            System.out.println("You have chosen " + transactionChoice + ". From which account would you like to " + transactionChoice + "?");
            Integer tempAccountNumber2 = ACCOUNT_NUMBER_SCANNER.intGet();
            if ((tempAccountNumber2 == -1)) {
                System.out.println("Returning.");
                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
            }
            Account temp = loggedInCustomer.getAccount(tempAccountNumber2);
            if (!(temp == null)) {
                uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 0.0, 200000000.0);
                temp.withdraw(TRANSACTION_SCANNER.doubleGet());
                return 1;
            } else if (temp == null) {
                System.out.println("Account not found. Please re-enter your account number.");
                processTransaction(transactionChoice, loggedInCustomer);
            }

        } else if (transactionChoice.equalsIgnoreCase("TRANSFER")) {
            System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
            Integer tempAccountNumber3 = ACCOUNT_NUMBER_SCANNER.intGet();

            if ((tempAccountNumber3 == -1)) {
                System.out.println("Returning.");
                processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
            }
            Account transferTo = loggedInCustomer.getAccount(tempAccountNumber3);
            if (!(transferTo == null)) {
                System.out.println("From which account would you like to " + transactionChoice + "?");
                Integer tempAccountNumber4 = ACCOUNT_NUMBER_SCANNER.intGet();

                if ((tempAccountNumber4 == -1)) {
                    System.out.println("Returning.");
                    processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
                }

                Account transferFrom = loggedInCustomer.getAccount(tempAccountNumber4);
                if (!(transferFrom == null)) {
                    uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 0.0, 200000000.0);
                    double transferAmount = TRANSACTION_SCANNER.doubleGet();
                    transferFrom.withdraw(transferAmount);
                    transferTo.deposit(transferAmount);
                    return 1;
                } else if (transferFrom == null) {
                    System.out.println("Account not found. Please re-enter your account number.");
                    processTransaction(transactionChoice, loggedInCustomer);
                }

            } else if (transferTo == null) {
                System.out.println("Account not found. Please re-enter your account number.");
                processTransaction(transactionChoice, loggedInCustomer);

            }


        } else if (transactionChoice.equalsIgnoreCase("ACCOUNTS")) {
            loggedInCustomer.printInformation("ACCOUNTS");
        } else if (transactionChoice.equalsIgnoreCase("RETURN")) {
            System.out.println("Returning to previous menu.");
            return 0;
        } else {
            System.out.println("Request could not be processed. Please try again.");
            processTransaction(TRANSACTION_REQUEST_SCANNER.stringGet(), loggedInCustomer);
            return 1;
        }

        return 0;

    }

    public String getAccountHeaders() {
        return String.format("%-10s %-10s %-20s %-20s %-36s %-4s %-6s %-4s", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
    }
}
