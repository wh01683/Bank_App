package bank_package;

import java.util.Random;

public class Customer {


    private final String CUSTOMER_ID;
    Random r = new Random();
    private String name;
    private int age;
    private ChexSystems _score = new ChexSystems();
    private int ChexSystemsScore = 0; //intialize to 0 indicating no prior history

    public Customer(String newName, int newAge) {
        this.CUSTOMER_ID = custIDGen(10);
        this.age = newAge;
        this.name = newName;
        if (newAge > 17) //if age is 18 or greater, generates random score for the customer
            this.ChexSystemsScore = _score.getScore();
    }

    public String getCUSTOMER_ID() {
        return this.CUSTOMER_ID;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }

    public int getChexSystemsScore() {
        return this.ChexSystemsScore;
    }

    private String custIDGen(int length) {
        String temp = "";
        final String HEX_ALPHABET = "abcdefABCDEF0123456789";
        final int NUM_IN_HEX = HEX_ALPHABET.length();
        for (int i = 0; i < length; i++) {
            temp += (HEX_ALPHABET.charAt(r.nextInt(NUM_IN_HEX)));
        }

        return temp;
    }

}
