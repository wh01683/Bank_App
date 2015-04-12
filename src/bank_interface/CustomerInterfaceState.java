package bank_interface;


interface CustomerInterfaceState {

    /**
     * processes the user's ENTERED email
     * @param email email entered by the user in the gui form, used to retrieve the customer's actual password
     * @return returns feedback to the user based on the outcome of the email entering process
     */
    String enterEmail(String email);

    /**
     * processes the customer's ENTERED password
     * @param password password entered by the user in the gui form, verified against the customer's actual email
     *                 address on file
     * @return returns feedback to the user based on the outcome of the password process
     */
    String enterPassword(String password);
    /**
     * saves bank data, changes state to LoggedOffState and brings up first menu. Same in all states.
     * */
    void logOff();

    /**
     * processes a transaction for the customer
     *
     * @param transactionChoice String version of the user's transaction choice (transfer, withdraw, deposit)
     * @param accountFromNumber Account number of the account to take money FROM
     * @param accountToNumber   Account number of the accoun tot put money IN
     * @param withdrawAmount    Amount of money to withdraw. For transfers, this will equal deposit
     * @param depositAmount     Amount of money to deposit. For transfers, this will equal withdraw
     * @return returns feedback to the user based on the outcome of the transaction process
     */
    String startTransaction(String transactionChoice, Integer accountFromNumber, Integer accountToNumber,
                            double withdrawAmount, double depositAmount);

    /**
     * creates and adds a user specified account in the appropriate states
     * @param accountRequest String representation of the account type requested by the customer
     * @param openingBalance opening balance passed to the account factory
     * @return returns feedback to the user based on the outcome of the add account process
     */
    String addAccount(String accountRequest, double openingBalance);

    /**
     * removes the account associated with the account number provided
     *
     * @param accountNumber account number of the account to be removed
     * @return returns feedback based on the outcome of the account removal process
     */
    String removeAccount(Integer accountNumber);
}
