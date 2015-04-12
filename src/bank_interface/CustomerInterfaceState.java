package bank_interface;


interface CustomerInterfaceState {

    /**
     * enterUUID prompts the user for their UUID in the appropriate state
     * @param email email entered by the user in the gui form, used to retrieve the customer's actual password
     */
    String enterEmail(String email);

    /**
     * enterPassword prompts the user for their password and checks for correctness in the appropriate state
     * @param password password entered by the user in the gui form, verified against the customer's actual email
     *                 address on file
     */
    String enterPassword(String password);
    /**
     * logOff saves bank data, changes state to LoggedOffState and brings up first menu. Same in all states.
     * */
    void logOff();

    /**
     * startTransaction invoked when the user would like to start a transaction in the appropriate states
     * @param transactionChoice
     * @param accountFromNumber
     * @param accountToNumber
     * @param withdrawAmount
     * @param depositAmount
     */
    String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber,
                            double withdrawAmount, double depositAmount);

    /**
     * addAccount creates and adds a user specified account in the appropriate states
     * @param accountRequest
     */
    String addAccount(String accountRequest);
}
