package bank_interface;


public class LoggedOff implements CustomerInterfaceState {


    CustomerInterface customerInterface;

    public LoggedOff(CustomerInterface newCustomerInterface) {
        this.customerInterface = newCustomerInterface;
    }

    @Override
    public void enterUUID() {

    }

    @Override
    public void enterPassword() {
        System.out.println("You must log in first.");
    }

    @Override
    public void hasAccount(boolean isRegistered) {
        System.out.println("You must log in first.");
    }

    @Override
    public void logOff() {
        System.out.println("You are already logged off.");
    }

    @Override
    public void requestInformation() {
        System.out.println("You must log in first.");
    }

    @Override
    public void startTransaction() {
        System.out.println("You must log in first.");
    }

    @Override
    public void addAccount() {
        System.out.println("You must log in first.");
    }
}
