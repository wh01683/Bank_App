package bank_package;

/**

 * Created by robert on 3/10/2015.
 */


import java.util.*;

public interface Account {

    public void setBalance();

    public double getBalance();

    public void setInterest();

    public double getInterest();

    public String getType();

    public Customer getOwner();


}
