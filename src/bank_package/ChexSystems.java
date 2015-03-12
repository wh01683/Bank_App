package bank_package;
import java.util.Random;

class ChexSystems {
    private final int CHEX_SYSTEMS_SCORE;
    private Random r = new Random();

    public ChexSystems() {
        //ChexSystems score ranges from 100 to 899
        this.CHEX_SYSTEMS_SCORE = (r.nextInt() * 899) + 100;
    }

    public int getScore() {
        return this.CHEX_SYSTEMS_SCORE;
    }

}
