package bank_interface;


import bank_package.BankProxy;
import utility.uScanner;

class LoggedOff implements CustomerInterfaceState {

    private static final uScanner logInOrRegister = new uScanner("Hello. do you have an account?\nYES, NO", 2, 3);
    private static BankProxy bankProxy;
    private final CustomerInterface customerInterface;

    public LoggedOff(CustomerInterface newCustomerInterface, BankProxy newBankProxy) {
        this.customerInterface = newCustomerInterface;
        bankProxy = newBankProxy;
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

    @Override
    public void hasAccount(boolean isRegistered) {

        if (!isRegistered) {
            customerInterface.START();
        } else {
            String loginOrRegister = logInOrRegister.stringGet();

            if (loginOrRegister.equalsIgnoreCase("NO")) {
                customerInterface.setCustomerInterfaceState(customerInterface.hasNoAccount);
                customerInterface.hasAccount(false);
            } else if (loginOrRegister.equalsIgnoreCase("YES")) {
                customerInterface.setCustomerInterfaceState(customerInterface.hasAccount);
                customerInterface.enterUUID();
            }
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
