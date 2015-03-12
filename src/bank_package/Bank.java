package bank_package;

class Bank {

    private String name = "Sea Island Bank - No Shoes, No Problem!";
    private int numberCustomers;
    private int numberAccounts;

    public Bank(String name, int numberAccounts, int numberCustomers) {
        this.name = name;
        this.numberAccounts = numberAccounts;
        this.numberCustomers = numberCustomers;
    }

    public int getNumberAccounts() {
        return this.numberAccounts;
    }

    public int getNumberCustomers() {
        return this.numberCustomers;
    }

}
