package bank_interface;


class Main {

    public static void main(String[] args) {

        DataIO dataIO = new DataIO(); /*declare new data input/output object. this helps us retrieve the bank'
        information form storage*/

        dataIO.readAllBankDataFromFile(); /*invoke the "readAllBankDataFromFile()" method in the dataIO object
        this will populate the RealBank object INSIDE the dataIO object.*/

        CustomerInterface test = CustomerInterface.getInstance(dataIO.getRealBank()); /*get the RealBank object from the
        dataIO object and pass it to the new CustomerInterface instance using the singleton instantiator*/

        test.START(); /*call the START() method of the CustomerInterface class. this will start the chain of interactions*/
    }
}
