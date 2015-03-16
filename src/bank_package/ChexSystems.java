package bank_package;
import java.io.Serializable;
import java.util.Random;

/*ToDo: make a more realistic ChexSystems class that calculates ChexSystems score based on customer history (like CreditScore)*/
class ChexSystems implements Serializable{
    private final int CHEX_SYSTEMS_SCORE;

    public ChexSystems() {
        //ChexSystems score ranges from 100 to 899
        Random r = new Random();
        this.CHEX_SYSTEMS_SCORE = (r.nextInt(499) + 400);
    }

    public int getScore() {
        return this.CHEX_SYSTEMS_SCORE;
    }

}
