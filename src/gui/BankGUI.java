package gui;

import bank_package.BankProxy;
import bank_package.RealBank;

/**
 * William Trent Holliday
 * 4/8/15
 */
public class BankGUI {
    private static final BankProxy bankProxy = new BankProxy(new RealBank("Test Bank", 0, 0));

    public static void main(String[] args) {
        CreateAccount createAccount = new CreateAccount();
    }

    public static BankProxy getBankProxy(){
        return bankProxy;
    }

}
