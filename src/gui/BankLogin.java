package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bank_interface.CustomerInterface;
import bank_interface.DataIO;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class BankLogin {
    private JPanel panel1;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createNewAccountButton;
    private JButton loginButton;

    public BankLogin() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    public static void main(String[] args) {
//        DataIO dataIO = new DataIO(); /*declare new data input/output object. this helps us retrieve the bank'
//        information form storage*/
//
//        //below is used to instantiate a new bank and register if you do not have a bankData file yet
//
//        /*RealBank tempBank = new RealBank("name", 1, 1);
//        dataIO.saveAllBankDataToFile(tempBank);*/
//
//        dataIO.readAllBankDataFromFile(); /*invoke the "readAllBankDataFromFile()" method in the dataIO object
//        this will populate the RealBank object INSIDE the dataIO object.*/
//
//        CustomerInterface userSession = CustomerInterface.getInstance(dataIO.getRealBank()); /*get the RealBank object from the
//        dataIO object and pass it to the new CustomerInterface instance using the singleton instantiator*/
//
//        userSession.START(); /*call the START() method of the CustomerInterface class. this will start the chain of interactions*/


        JFrame frame = new JFrame("bank_gui");
        frame.setContentPane(new BankLogin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
