package bank_package;
import java.util.Random;
public class RandomBooleanGenerator {

    private Random r = new Random();

    public boolean getRandomBoolean() {
        float temp = r.nextFloat();
        if (temp < .50) return false;
        else
            return true;
    }

}
