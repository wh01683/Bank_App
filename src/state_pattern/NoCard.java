package state_pattern;

/**
 * Created by robert on 3/15/2015.
 */
public class NoCard implements ATMState {

    private final ATMMachine atmMachine;

    public NoCard(ATMMachine newATMMachine) {

        atmMachine = newATMMachine;
    }

    @Override
    public void insertCard() {
        System.out.println("Please enter card.");
        atmMachine.setATMState(atmMachine.getYesCardState());

    }

    @Override
    public void ejectCard() {
        System.out.println("Please enter card.");
        atmMachine.setATMState(atmMachine.getNoCardState());
    }

    @Override
    public void insertPin(int pin) {

        System.out.println("Must first enter card.");
    }

    @Override
    public void requestCash(int cashToWithdraw) {

        System.out.println("Must have card and pin");

    }
}
