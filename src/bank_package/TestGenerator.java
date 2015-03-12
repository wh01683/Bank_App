package bank_package;

import java.beans.XMLEncoder;
import java.io.*;
import java.util.Hashtable;
import java.util.Random;
import java.util.UUID;


class TestGenerator {

    private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String HEX_ALPHABET = "abcdefABCDEF0123456789";
    final int NUM_IN_HEX = HEX_ALPHABET.length();
    private final int NUM_IN_ALPHABET = ALPHABET.length();
    File test = new File("tests.xml");
    private Hashtable<TestGenerator, UUID> testList;
    private XMLEncoder x = new XMLEncoder(getStreams("C:\\Users\\robert\\Desktop\\tests.xml"));
    private Random r = new Random();
    private Customer _cust;
    private CreditReport _cred;
    private ChexSystems _chex;

    public TestGenerator(int amountOfTests) {
        this._cust = new Customer(nameGen(2, 50), r.nextInt(100));
        this._cred = new CreditReport(r.nextInt(100), r.nextInt(100), r.nextInt(100), r.nextDouble() * 2000000000, r.nextDouble() * 2000000000,
                r.nextInt(100));
        this._cred.setLatePaymentAmount(r.nextDouble() * 2000000000);
        this._chex = new ChexSystems();

        testList = new Hashtable<TestGenerator, UUID>(amountOfTests);
        for (int i = 0; i < amountOfTests; i++) {
            testList.put(new TestGenerator(1), this._cust.getUUID());
        }
    }

    public void printTests() {
        this.x.writeObject(this.testList);
        this.x.close();
    }

    private String nameGen(int minLength, int maxLength) {
        String temp = "";
        for (int i = minLength; i <= maxLength; i++) {
            temp += ALPHABET.charAt(r.nextInt(NUM_IN_ALPHABET));
        }
        return temp;
    }

/*	private String custIDGen(int length){
        String temp = "";
		for(int i = 0; i < length; i++){
			temp+=(HEX_ALPHABET.charAt(r.nextInt(NUM_IN_HEX)));
		}
		
		return temp;
		
	}
*/


    private FileOutputStream getStreams(String fileName) {

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(fileName);
            return fos;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }


}
