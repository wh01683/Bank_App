package state_pattern;

/**
 * Created by robert on 3/15/2015.
 */
public class HasPin implements ATMState {

    private final ATMMachine atmMachine;

    public HasPin(ATMMachine atmMachine) {
        this.atmMachine = atmMachine;
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

        System.out.println("How much cash would you like to withdraw?");

        if (cashToWithdraw > atmMachine.cashInMachine) {
            System.out.println("Not enough cash.");
            System.out.println("Card ejected");
            atmMachine.setATMState(atmMachine.getNoCardState());
        } else {
            System.out.println(cashToWithdraw + " is provided by the machine.");
            atmMachine.setCashInMachine(atmMachine.cashInMachine - cashToWithdraw);

            System.out.println("Card Ejected.");
            atmMachine.setATMState(atmMachine.getNoCardState());

            if (atmMachine.cashInMachine <= 0) {
                atmMachine.setATMState(atmMachine.getAtmOutofMoneyState());
            }
        }


    }
}
