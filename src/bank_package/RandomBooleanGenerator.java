package bank_package;
import java.util.Random;

class RandomBooleanGenerator {

    private Random r = new Random();

    public boolean getRandomBoolean() {
        float temp = r.nextFloat();
        return (temp < .50);
    }

}
