package bank_package;
/*The goal of the facade pattern is to simplify a group of subsystems
 * by making a unified interface to group all the subsystems. the implemented
 * Systems have no knowledge of the facade; they have no instance of the facade.*/

/*The purpose of this application is to demonstrate understanding and possible uses 
 * of the Facade pattern. I will create a bank themed application which will implement a 
 * checking account application class as the facade class. Subclasses will include bank account
 * information like ChexSystems report (the banking equivalent of a credit check), available balance
 * to open with, check basic customer information like age, etc*/

class Main {


    Bank randomBank = new Bank("Georgia", 5000, 5000);

    public static void main(String[] args) {
        Main test = new Main();
        int num = Integer.parseInt(args[0]);
        test.work(num);
    }

    void work(int newArgs) {

        for (int i = 0; i < newArgs; i++) {
            this.randomBank = randomBank.getRandomBank();
            randomBank.addCustomer(newArgs);
            randomBank.writeInfoToFile(System.getProperty("user.dir") + "\\Customer&AccountInformation" + i + ".txt");
            randomBank.writeAccountInfoToFile(System.getProperty("user.dir") + "\\AccountInformation" + i + ".txt");
            randomBank.writeCustomerInfoToFile(System.getProperty("user.dir") + "\\CustomerInformation" + i + ".txt");
        }
    }

}
