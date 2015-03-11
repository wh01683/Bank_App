package bank_package;

import java.util.Hashtable;
import java.util.Random;


public class TestGenerator {

    final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    final int NUM_IN_HEX = HEX_ALPHABET.length();
    final int NUM_IN_ALPHABET = ALPHABET.length();
    Hashtable<TestGenerator, String> testList = new Hashtable<TestGenerator, String>();
    private Random r = new Random();
    private Customer _cust;
    private CreditReport _cred;
    private ChexSystems _chex;


    public TestGenerator(int amountOfTests) {
        this._cust = new Customer(nameGen(2, 50), r.nextInt(100));
        this._cred = new CreditReport(r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextDouble() * 2000000000, r.nextDouble() * 2000000000,
                r.nextInt(100));
        this._chex = new ChexSystems();

        for (int i = 0; i < amountOfTests; i++) {
            testList.put(new TestGenerator(1), this._cust.getCUSTOMER_ID());
        }
    }


    private String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }

    private String custIDGen(int length) {
        String temp = "";
        for (int i = 0; i < length; i++) {
            temp += (HEX_ALPHABET.charAt(r.nextInt(NUM_IN_HEX)));
        }

        return temp;

    }


}
