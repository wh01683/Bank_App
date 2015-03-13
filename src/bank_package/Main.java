package bank_package;
/*The goal of the facade pattern is to simplify a group of subsystems
 * by making a unified interface to group all the subsystems. the implemented
 * Systems have no knowledge of the facade; they have no instance of the facade.*/

/*The purpose of this application is to demonstrate understanding and possible uses 
 * of the Facade pattern. I will create a bank themed application which will implement a 
 * checking account application class as the facade class. Subclasses will include bank account
 * information like ChexSystems report (the banking equivalent of a credit check), available balance
 * to open with, check basic customer information like age, etc*/

import java.util.ArrayList;

class Main {

    private static ArrayList<String> argumentList;
    public static void main(String[] args) {
        Main test = new Main();

        test.work();

    }

    void work() {

        Bank testBank = new Bank("Fred", 200000, 50000, true);
        testBank.addCustomer(50000);
        testBank.printCustomerInfoToFile();

    }

}
