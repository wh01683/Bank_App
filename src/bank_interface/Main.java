package bank_interface;
/*The goal of the facade pattern is to simplify a group of subsystems
 * by making a unified interface to group all the subsystems. the implemented
 * Systems have no knowledge of the facade; they have no instance of the facade.*/

/*The purpose of this application is to demonstrate understanding and possible uses 
 * of the Facade pattern. I will create a bank themed application which will implement a 
 * checking account application class as the facade class. Subclasses will include bank account
 * information like ChexSystems report (the banking equivalent of a credit check), available balance
 * to open with, check basic customer information like age, etc*/

import bank_package.RealBank;

class Main {

    public static void main(String[] args) {
        /*Main test = new Main();
        int num = Integer.parseInt(args[0]);
        test.work(num);*/

        RealBank newBank = new RealBank("hello", 0, 0);
        /*newBank.addRandomCustomers(500);
        DataIO dataIO = new DataIO(newBank);
        dataIO.saveAllBankDataToFile("DEFAULT");
        RealBank newrealBank;
        dataIO.readAllBankDataFromFile("DEFAULT");
        newrealBank = dataIO.getRealBank();*/

        CustomerInterface test = CustomerInterface.getInstance(newBank);
        test.START();
    }
}
