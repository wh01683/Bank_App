package atm;

/**
 * Created by robert on 3/15/2015.
 */
public class HasCard implements ATMState {

    private final ATMMachine atmMachine;

    public HasCard(ATMMachine newATMMachine) {

        atmMachine = newATMMachine;
    }

    @Override
    public void insertCard() {

        System.out.println("You can't enter more than one card.");

    }

    @Override
    public void ejectCard() {
        atmMachine.setATMState(atmMachine.getNoCardState());
    }

    @Override
    public void insertPin(int pin) {

        if (pin == 1234) {
            System.out.println("Accepted. Card Ejected.");
            atmMachine.correctPinEntered = true;
            atmMachine.setATMState(atmMachine.getHasCorrectPinState());
        } else {
            System.out.println("Incorrect pin");
            atmMachine.correctPinEntered = false;
            System.out.println("Card Ejected");
            atmMachine.setATMState(atmMachine.getNoCardState());

        }

    }

    @Override
    public void requestCash(int cashToWithdraw) {

        System.out.println("Must enter pin first.");

    }
}
