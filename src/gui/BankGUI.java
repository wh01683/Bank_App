package gui;

import bank_interface.CustomerInterface;
import bank_interface.DataIO;
import bank_package.BankProxy;
import bank_package.RealBank;

/**
 * William Trent Holliday
 * 4/8/15
 */
public class BankGUI {
    private static final DataIO dataIo = new DataIO();
    private static final RealBank realBank = dataIo.getRealBank();
    private static final BankProxy bankProxy = new BankProxy(realBank);
    private static final CustomerInterface customerInterface = CustomerInterface.getInstance(realBank);

    public static void main(String[] args) {
//        CreateAccount createAccount = new CreateAccount();
//        createAccount.setContentPane(createAccount.getContentPane());

        BankLogin bankLogin = new BankLogin();

    }

    public static BankProxy getBankProxy(){
        return bankProxy;
    }

    public static CustomerInterface getCustomerInterface(){
        return customerInterface;
    }


}
