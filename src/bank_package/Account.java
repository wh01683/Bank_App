package bank_package;

/**

 * Created by robert on 3/10/2015.
 */


import java.util.*;

interface Account {

    public double getBalance();

    public void setBalance(double newBalance);

    public double getInterest();

    public void setInterest(double newInterest);

    public String getType();

    public Customer getOwner();


}
