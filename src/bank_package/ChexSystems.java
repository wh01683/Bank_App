package bank_package;
import java.io.Serializable;
import java.util.Random;

/*ToDo: make a more realistic ChexSystems class that calculates ChexSystems score based on customer history (like CreditScore)*/
public class ChexSystems implements Serializable {
    private final int CHEX_SYSTEMS_SCORE;

    /**
     * ChexSystems creates a ChexReport with a randomly generated integer score ranging from 499 to 899
     */
    public ChexSystems() {
        //ChexSystems score ranges from 100 to 899
        Random r = new Random();
        this.CHEX_SYSTEMS_SCORE = (r.nextInt(499) + 400);
    }

    /**
     * getScore gets the score associated with this ChexSystems
     *
     * @return returns the integer score
     */
    public int getScore() {
        return this.CHEX_SYSTEMS_SCORE;
    }

}
