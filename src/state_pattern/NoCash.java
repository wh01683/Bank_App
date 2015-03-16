package state_pattern;

/**
 * Created by robert on 3/15/2015.
 */
public class NoCash implements ATMState {

    private final ATMMachine atmMachine;

    public NoCash(ATMMachine newAtmMachine) {

        atmMachine = newAtmMachine;

    }

    @Override
    public void insertCard() {

        System.out.println("You already entered your card.");

    }

    @Override
    public void ejectCard() {
        System.out.println("Ejecting..");
        atmMachine.setATMState(atmMachine.getNoCardState());
    }

    @Override
    public void insertPin(int pin) {

        System.out.println("You already entered your pin.");

    }

    @Override
    public void requestCash(int cashToWithdraw) {

        System.out.println("No cash available.");
    }
}
