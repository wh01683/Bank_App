package bank_interface;


interface CustomerInterfaceState {

    public void enterUUID();

    public void enterPassword();

    public void hasAccount(boolean isRegistered);

    public void logOff();

    public void requestInformation();

    public void startTransaction();

    public void addAccount();
}