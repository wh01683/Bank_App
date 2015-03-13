package bank_package;

import java.util.Random;
import java.util.Vector;

public class RandomGenerator {

    private static int currentIndex;
    private Random r = new Random();
    private Vector<Integer> acctNumberList = new Vector<Integer>(500);

    public boolean getRandomBoolean() {
        float temp = r.nextFloat();
        return (temp < .50);
    }

    public Integer acctGen() {
        /*Returns random account number.*/
        int temp = 100000000;
        Integer tempAccountNumber = temp += r.nextInt(99999999);

        while (this.acctNumberList.contains(tempAccountNumber))
            tempAccountNumber = temp += r.nextInt(99999999);

        this.acctNumberList.add(currentIndex, tempAccountNumber);
        return tempAccountNumber;
    }

}
