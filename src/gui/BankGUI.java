package gui;

import bank_interface.CustomerInterface;
import bank_package.BankProxy;
import bank_package.RealBank;

/**
 * William Trent Holliday
 * 4/8/15
 */
public class BankGUI {
    private static final RealBank realBank = new RealBank("Test Bank", 0, 0);
    private static final BankProxy bankProxy = new BankProxy(realBank);
    private static final CustomerInterface customerInterface = CustomerInterface.getInstance(realBank);

    public static void main(String[] args) {
        CreateAccount createAccount = new CreateAccount();
    }

    public static BankProxy getBankProxy(){
        return bankProxy;
    }

    public static CustomerInterface getCustomerInterface(){
        return customerInterface;
    }

}
