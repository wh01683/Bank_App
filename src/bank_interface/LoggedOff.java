package bank_interface;


import utility.uScanner;

public class LoggedOff implements CustomerInterfaceState {

    /*login or register prompt.*/
    private static final uScanner logInOrRegister = new uScanner("----LOGIN--------------REGISTER-------------EXIT----", 4, 8);
    private final CustomerInterface customerInterface;

    /**
     * LoggedOff creates a LoggedOff state used by the customer interface class (default start state)
     *
     * @param newCustomerInterface customer interface instance passed through customer interface constructor. used to
     *                             set and update states of the customer interface
     */
    public LoggedOff(CustomerInterface newCustomerInterface) {
        this.customerInterface = newCustomerInterface;
    }

    /**
     * enterUUID not allowed in this state
     */
    @Override
    public void enterUUID() {
        System.out.println("No.");
        customerInterface.hasAccount(false);
    }
/**
 * enterPassword not allowed in this state
 * */
@Override
    public void enterPassword() {
        System.out.println("You must enter your UUID first.");
        customerInterface.hasAccount(false);
    }

 /**hasAccount: this is the entry point to the program they simply type whether they want to login or register,
  *           and they are redirected to the appropriate state and the appropriate handling method based on what they choose
  *
  *@param isRegistered: default false.
  *
  **/
 @Override
    public void hasAccount(boolean isRegistered) {

            String loginOrRegister = logInOrRegister.stringGet();

        if (loginOrRegister.equalsIgnoreCase("REGISTER")) {
                customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
            customerInterface.hasAccount(true);
        } else if (loginOrRegister.equalsIgnoreCase("LOGIN")) {
                customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
                customerInterface.enterUUID();
        } else if (loginOrRegister.equalsIgnoreCase("EXIT")) {
            System.out.printf("Exiting..");
            System.exit(0);
        } else {
            System.out.printf("Request could not be processed.");
            hasAccount(false);
        }


    }
/**
 * logOff not allowed in this state, user is already logged off
 * */
@Override
    public void logOff() {
        System.out.println("You are already logged off.");
        customerInterface.hasAccount(false);
    }
/**
 * requestInformation not allowed in this state
 * */
@Override
    public void requestInformation() {
        System.out.println("You must log in first.");
        customerInterface.hasAccount(false);
    }
/**
 * startTransaction not allowed in this state
 * */
@Override
    public void startTransaction() {
        System.out.println("You must log in first.");
        customerInterface.hasAccount(false);
    }
/**
 * addAccount not allowed in this state
 * */
@Override
    public void addAccount() {
        System.out.println("You must log in first.");
        customerInterface.hasAccount(false);
    }


}
