package bank_interface;


import utility.uScanner;

class LoggedOff implements CustomerInterfaceState {

    /*login or register prompt.*/
    private static final uScanner logInOrRegister = new uScanner("----LOGIN---------------------------------REGISTER----", 4, 8);
    private final CustomerInterface customerInterface;

    public LoggedOff(CustomerInterface newCustomerInterface) {
        this.customerInterface = newCustomerInterface;
    }

    @Override
    public void enterUUID() {
        System.out.println("No.");
        customerInterface.hasAccount(false);
    }

    @Override
    public void enterPassword() {
        System.out.println("You must enter your UUID first.");
        customerInterface.hasAccount(false);
    }

    /*@func hasAccount: this is the entry point to the program they simply type whether they want to login or register,
     and they are redirected to the appropriate state and the appropriate handling method based on what they choose

     @param isRegistered: default false.
     @return : void*/
    @Override
    public void hasAccount(boolean isRegistered) {


            String loginOrRegister = logInOrRegister.stringGet();

        if (loginOrRegister.equalsIgnoreCase("REGISTER")) {
                customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
            customerInterface.hasAccount(true);
        } else if (loginOrRegister.equalsIgnoreCase("LOGIN")) {
                customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
                customerInterface.enterUUID();
            }

    }

    @Override
    public void logOff() {
        System.out.println("You are already logged off.");
        customerInterface.hasAccount(false);
    }

    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
        customerInterface.hasAccount(false);
    }

    @Override
    public void startTransaction() {
        System.out.println("You must log in first.");
        customerInterface.hasAccount(false);
    }

    @Override
    public void addAccount() {
        System.out.println("You must log in first.");
        customerInterface.hasAccount(false);
    }


}
