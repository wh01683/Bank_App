package bank_interface;

import acct.Account;
import acct.AccountFactory;
import bank_package.Bank;
import bank_package.Customer;
import bank_package.RandomGenerator;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by robert on 3/14/15.
 */
public class RandomCustomerInterface {


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


    private static final AccountFactory ACCOUNT_FACTORY = new AccountFactory();
    private static Customer CUSTOMER;
    private static Hashtable<Integer, Customer> customerHashtable;
    private static Hashtable<Integer, Account> accountHashtable;
    private static RandomCustomerInterface SINGLETON_INSTANCE;

    private static Integer exitFailsafe = 0;
    private static UUID CUSTOMER_ID;
    private static Bank bank;
    private static DataIO dataIO;
    private RandomGenerator r = new RandomGenerator();
        /*private final uScanner NAME_SCANNER = new uScanner("Please enter your name: ", 2, 50);
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
        private final uScanner ACCOUNT_NUMBER_SCANNER = new uScanner("Please enter your ACCOUNT NUMBER, or -1 to RETURN", 0, 200000000);*/




    /*private constructor... creates new customer interface using the current bank's information (passed through param)
    * and the customer's unique ID also passed through param.*/

    /*Private Constructor creates a new singleton CustomerInterface for the customer to access information with. Only one instance*/
    private RandomCustomerInterface(Bank newBank) {
        exitFailsafe++;
        System.out.println("Exit Fail Safe = " + exitFailsafe);

        if (exitFailsafe == 1000) {
            System.out.println("Failsafe = " + exitFailsafe + " logging out.");
            System.exit(0);
        }

        bank = newBank;
        customerHashtable = bank.getCustomerTable();
        accountHashtable = bank.getCustomerTable();
        bank.updateAccountTable();
        dataIO = new DataIO(bank);

        String enteredPass;

        Enumeration<Integer> customerKeys = customerHashtable.keys();

        while (customerKeys.hasMoreElements()) {

            CUSTOMER = customerHashtable.get(customerKeys.nextElement());
            CUSTOMER_ID = CUSTOMER.getUUID();


         /*if their key is found in the customerHashTable, the instance's customer is set to the customer of that location
         * their password ON RECORD is set to a final String "realPass"*/
            final String realPass = CUSTOMER.getPASSWORD();
         /*the user is prompted for their password, which is stored in enteredPass*/
            enteredPass = CUSTOMER.getPASSWORD();
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
                    enteredPass = CUSTOMER.getPASSWORD();
                }
            }

            boolean loggedIn = enteredPass.equals(realPass);
            System.out.println("Congratulations! Your input password " + enteredPass + " matches your real password" +
                    " on file, " + realPass + "\nYou may now access your bank account information!");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
            initiateLoginProcesses(true, CUSTOMER);

        }

        System.out.println("Ran out of customers.. exiting.");
        System.exit(1);
    }


        /*@getInstance
        * this Singleton class utilizes a private constructor and a public method, "getInstance". This method will return
        * the CURRENT INSTANCE of the customer interface class if it does not equal null. else, will return new CustomerInterface
        * constructed with the bank passed through params
        *
        * @param Bank thisBank: needed for construction of the new instance
        * @return new CustomerInterface: returns new instance of CustomerInterface if their is no current instance in existence*/
        public static RandomCustomerInterface getInstance(Bank thisBank) {
            if (!(SINGLETON_INSTANCE == null)) {
                CUSTOMER = null;
                dataIO = new DataIO(thisBank);
                return SINGLETON_INSTANCE;
            }
            else
                return new RandomCustomerInterface(thisBank);

        }

    private void initiateLoginProcesses(boolean isLoggedIn, Customer CUSTOMER) {
            /*final uScanner PROCESS_REQUEST_SCANNER = new uScanner("What would you like to do, " + CUSTOMER.getName() + "?\nINFORMATION, TRANSACTION, ADDACCOUNT, EXIT", 3, 12);
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
*/
            while (isLoggedIn) {
                String processRequest = r.processGen();
                if (processRequest.equalsIgnoreCase("INFORMATION")) {
                    this.showCustomerAccountInformation(CUSTOMER);
                    initiateLoginProcesses(true, CUSTOMER);
                }
                else if (processRequest.equalsIgnoreCase("TRANSACTION")) {
                    if (this.processTransaction(r.transactionGen(), CUSTOMER) == 0)
                        initiateLoginProcesses(true, CUSTOMER);
                } else if (processRequest.equalsIgnoreCase("ADDACCOUNT")) {
                    this.addAccount(CUSTOMER);
                    initiateLoginProcesses(true, CUSTOMER);
                }
                else if (processRequest.equalsIgnoreCase("EXIT")) {
                    isLoggedIn = false;
                    System.out.println("Swiggity Swooty, I'm Coming For That Booty!");
                    Bank newBank = new Bank("whatever", 50, 50);
                    newBank.addRandomCustomers(50);
                    newBank.updateAccountTable();
                    RandomCustomerInterface newOne = RandomCustomerInterface.getInstance(newBank);

                } else {
                    System.out.println("Your request could not be processed, please try again.");
                    initiateLoginProcesses(true, CUSTOMER);
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
        /*private Customer registerNewCustomer() {
            String tempName = NAME_SCANNER.stringGet();
            int tempAge = AGE_SCANNER.intGet();
            CreditReport tempCreditReport;
            if (tempAge < 18) *//*if the user is younger than 18, they do not have a credit report so a default credit report
        is created for the user with all values initiated at 0*//*
                tempCreditReport = new CreditReport(0);
            else
                tempCreditReport = fillCredReportInformation(tempAge);
            ChexSystems tempScore = new ChexSystems();
            final uScanner NEW_PASSWORD_SCANNER = new uScanner("Please enter your new custom password for your account.", 5, 20);
            String tempPassword = NEW_PASSWORD_SCANNER.alphaNumericStringGet();

            return new Customer(tempName, tempAge, tempPassword, tempCreditReport);
        }*/

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
        * @return latePay.doubleGet(): returns a new double value with the total value of late payments on record*/
        /*private double getLatePaymentAmounts(int newNumberOfLatePayments) {
            uScanner latePay = new uScanner("You indicated you have " + newNumberOfLatePayments + " late payments on record.\n"
                    + "Please enter the total amount of the late payments.", 0.0, 2000000000.0);
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
        private void addAccount(Customer CUSTOMER) {
            Account tempAccount = ACCOUNT_FACTORY.getRandomAccount(CUSTOMER);

            if (tempAccount == null) {
                System.out.println("Account type invalid. Please try again.");
                addAccount(CUSTOMER);
            } else if (!(tempAccount == null)) {
                CUSTOMER.addAccount(tempAccount);
                System.out.println("Congratulations, " + CUSTOMER.getName() + "!" + "You successfully added a " + tempAccount.getType() + " account under your name.\n" +
                        "Here is the information...\n");
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(getAccountHeaders());
                System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(tempAccount.toString());
                System.out.println("\n");
                bank.updateAccountTable();
                //uScanner yesNo = new uScanner("Would you like to add more accounts?\nYES to create additional accounts, NO to return.", 1, 4);
                if (r.getRandomBoolean())
                    addAccount(CUSTOMER);
                else
                    initiateLoginProcesses(true, CUSTOMER);
            } else
                System.out.println("Something bad happened. Exiting.");


        }

        /*@hasAccount
        *
        * This method asks the user whether they have an account or not, accepting a non-case-sensitive yes for true and
        * a non-case-sensitive no as false
        *
        * @param void: not required
        * @return boolean: returns user's answer in boolean form*/
        /*private boolean hasAccount() {
            //String answer = this.HAVE_ACCOUNT_SCANNER.stringGet();

            if (r.getRandomBoolean())
                return true;

            else if (r.getRandomBoolean())
                return false;

            else {
                System.out.println("Incorrect response.");
                return hasAccount();
            }
        }*/

        /*@hasAccount
           *
           * This method asks the user whether they would like to register or not, accepting a non-case-sensitive
           * yes for true and a non-case-sensitive no as false
           *
           * @param void: not required
           * @return boolean: returns user's answer in boolean form*/
        /*private boolean wantsToRegister() {
           // String answer = this.WANT_REGISTER_SCANNER.stringGet();

            if (r.getRandomBoolean())
                return true;
            else if (r.getRandomBoolean())
                return false;

            else {
                System.out.println("Incorrect response.");
                return wantsToRegister();
            }
        }*/

        private void showCustomerAccountInformation(Customer loggedInCustomer) {

            printInformation(r.informationGen());
            initiateLoginProcesses(true, loggedInCustomer);

        }

    public void printInformation(String request) {

        if (request.equalsIgnoreCase("CHEX")) {
            System.out.println("Your ChexSystems score is currently " + CUSTOMER.getChexSystemsScore() + ".");
            printInformation(r.informationGen());
        } else if (request.equalsIgnoreCase("CREDIT")) {
            System.out.println("Your Credit Score is currently " + CUSTOMER.getCreditScore() + ".");
            printInformation(r.informationGen());
        } else if (request.equalsIgnoreCase("ACCOUNTS")) {
            if (!(CUSTOMER.getAccountHashtable().equals(null)))
                dataIO.printAccountInformation(CUSTOMER.getAccountHashtable());
            else
                printInformation(r.informationGen());
        } else if (request.equalsIgnoreCase("ALL")) {
            dataIO.printAllCustomerPrivateInformation(CUSTOMER.getUUID().hashCode(), CUSTOMER.getAccountHashtable());
            printInformation(r.informationGen());
        } else if (request.equalsIgnoreCase("RETURN")) {
            System.out.println("Returning to previous menu.");
            initiateLoginProcesses(true, CUSTOMER);
        } else {
            System.out.println("Could not process your request: " + request + " Please try again");
            printInformation(r.informationGen());
        }
    }
        private int processTransaction(String transactionChoice, Customer loggedInCustomer) {
        /*DEPOSIT SECTION*/

            Enumeration<Integer> accountKeys = loggedInCustomer.getAccountHashtable().keys();

            while (accountKeys.hasMoreElements()) {

                if (transactionChoice.equalsIgnoreCase("DEPOSIT")) {
                    System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                    Integer tempAccountNumber1 = accountKeys.nextElement();
                    if ((tempAccountNumber1 == -1)) {
                        System.out.println("Returning.\n\n\n");
                        processTransaction(r.transactionGen(), loggedInCustomer);
                    }
                    Account temp = loggedInCustomer.getAccount(tempAccountNumber1);
                    if (!(temp == null)) {
                        // uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 0.0, 200000000.0);
                        temp.deposit(r.getDubs(-500.0, 20000.0));
                        return 1;
                    } else if (temp == null) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(transactionChoice, loggedInCustomer);
                    }

                }

        /*WITHDRAW SECTION*/

                else if (transactionChoice.equalsIgnoreCase("WITHDRAW")) {
                    System.out.println("You have chosen " + transactionChoice + ". From which account would you like to " + transactionChoice + "?");
                    Integer tempAccountNumber2 = accountKeys.nextElement();
                    if ((tempAccountNumber2 == -1)) {
                        System.out.println("Returning.");
                        processTransaction(r.transactionGen(), loggedInCustomer);
                    }
                    Account temp = loggedInCustomer.getAccount(tempAccountNumber2);
                    if (!(temp == null)) {
                        //uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice + "?", 0.0, 200000000.0);
                        if ((temp.withdraw(r.getDubs(0.0, 4000.0)) == -1)) {
                            System.out.println("Insufficient funds. " + temp.getBalance() + " available.");
                            return 0;
                        } else {
                            return 1;
                        }

                    } else if (temp == null) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(transactionChoice, loggedInCustomer);
                    }

                }


        /*TRANSFER SELECTION*/
                else if (transactionChoice.equalsIgnoreCase("TRANSFER")) {
                    System.out.println("You have chosen " + transactionChoice + ". To which account would you like to " + transactionChoice + "?");
                    Integer tempAccountNumber3 = accountKeys.nextElement();

                    if ((tempAccountNumber3 == -1)) {
                        System.out.println("Returning.");
                        processTransaction(r.transactionGen(), loggedInCustomer);
                    }
                    Account transferTo = loggedInCustomer.getAccount(tempAccountNumber3);
                    if (!(transferTo == null)) {
                        if(accountKeys.hasMoreElements()){
                            System.out.println("From which account would you like to " + transactionChoice + "?");
                            Integer tempAccountNumber4 = accountKeys.nextElement();
                            if ((tempAccountNumber4 == -1)) {
                                System.out.println("Returning.");
                                processTransaction(r.transactionGen(), loggedInCustomer);
                            }
                            else{
                                Account transferFrom = loggedInCustomer.getAccount(tempAccountNumber4);
                                if (!(transferFrom == null)) {
                                    //uScanner TRANSACTION_SCANNER = new uScanner("How much would you like to " + transactionChoice, 0.0, 200000000.0);
                                    double transferAmount = r.getDubs(0.0, 20000.0);
                                    transferFrom.withdraw(transferAmount);
                                    transferTo.deposit(transferAmount);
                                    return 1;
                                } else if (transferFrom == null) {
                                    System.out.println("Account not found. Please re-enter your account number.");
                                    processTransaction(r.transactionGen(), loggedInCustomer);
                                }

                            }

                        }
                        else{
                            System.out.println("You do not have another account to transfer from.");
                            processTransaction(r.transactionGen(), loggedInCustomer);
                        }

                    } else if (transferTo == null) {
                        System.out.println("Account not found. Please re-enter your account number.");
                        processTransaction(r.transactionGen(), loggedInCustomer);

                    }


                } else if (transactionChoice.equalsIgnoreCase("ACCOUNTS")) {
                    printInformation("ACCOUNTS");
                    initiateLoginProcesses(true, loggedInCustomer);
                } else if (transactionChoice.equalsIgnoreCase("RETURN")) {
                    System.out.println("Returning to previous menu.");
                    initiateLoginProcesses(true, loggedInCustomer);
                } else {
                    System.out.println("Request could not be processed. Please try again.");
                    processTransaction(r.transactionGen(), loggedInCustomer);
                    return 1;
                }
            }
            initiateLoginProcesses(true, loggedInCustomer);
            return 0;
        }

        String getAccountHeaders() {
            return String.format("||%-10s||%-10s||%-20s||%-20s||%-36s||%-4s||%-6s||%-4s||", "TYPE", "ACCT#", "BALANCE", "CUSTOMER NAME",
                    "CUSTOMER UUID", "CHEX", "ODRAFT", "MIN BAL");
        }
    }

