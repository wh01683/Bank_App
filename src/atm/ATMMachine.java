package atm;

/**
 * Created by robert on 3/15/2015.
 */
class ATMMachine {

    private final ATMState hasCard;
    private final ATMState noCard;
    private final ATMState hasCorrectPin;
    private final ATMState atmOutofMoney;
    int cashInMachine = 2000;
    boolean correctPinEntered = false;
    private ATMState atmState;

    public ATMMachine() {
        hasCard = new HasCard(this);
        noCard = new NoCard(this);
        hasCorrectPin = new HasPin(this);
        atmOutofMoney = new NoCash(this);

        atmState = noCard;

        if (cashInMachine < 0) {
            atmState = atmOutofMoney;
        }
    }

    void setATMState(ATMState newATMState) {
        atmState = newATMState;
    }

    public void setCashInMachine(int newCashInMachine) {
        cashInMachine = newCashInMachine;
    }

    public void insertCard() {
        atmState.insertCard();
    }

    public void ejectCard() {
        atmState.ejectCard();
    }

    public void requestCash(int cashToWithdraw) {
        atmState.requestCash(cashToWithdraw);
    }

    public void insertPin(int pinEntered) {
        atmState.insertPin(pinEntered);
    }

    public ATMState getYesCardState() {
        return hasCard;
    }

    public ATMState getNoCardState() {
        return noCard;
    }

    public ATMState getHasCorrectPinState() {
        return hasCorrectPin;
    }

    public ATMState getAtmOutofMoneyState() {
        return atmOutofMoney;
    }
}