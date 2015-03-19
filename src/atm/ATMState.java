package atm;

/**
 * Created by robert on 3/15/2015.
 */
public interface ATMState {

    void insertCard();

    void ejectCard();

    void insertPin(int pin);

    void requestCash(int cashToWithdraw);

}
