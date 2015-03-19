package bank_interface;


public interface CustomerInterfaceState {

    /**
     * enterUUID prompts the user for their UUID in the appropriate state
     */
    public void enterUUID();

    /**
     * enterPassword prompts the user for their password and checks for correctness in the appropriate state
     */
    public void enterPassword();

    /**
     * hasAccount prompts the user to login or create an account in the appropriate state
     *
     * @param isRegistered true if the customer would like to register
     */
    public void hasAccount(boolean isRegistered);
/**
 * logOff saves bank data, changes state to LoggedOff and brings up first menu. Same in all states.
 * */
public void logOff();
/**
 * requestInformation prompts the user for commands upon logging in
 * */
public void requestInformation();

    /**
     * startTransaction invoked when the user would like to start a transaction in the appropriate states
     * */
    public void startTransaction();
/**
 * addAccount creates and adds a user specified account in the appropriate states
 * */
public void addAccount();
}
