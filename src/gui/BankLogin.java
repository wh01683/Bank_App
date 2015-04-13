package gui;

import bank_interface.CustomerInterface;
import bank_interface.EmailValidator;
import bank_interface.PasswordChecker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/16/15
 */
class BankLogin implements FormGui {
    private static BankGUI bankGUI = new BankGUI();
    private static CustomerInterface customerInterface = BankGUI.getCustomerInterface();
    PasswordChecker passwordChecker = new PasswordChecker();
    EmailValidator emailValidator = new EmailValidator();
    private JPanel panel1;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton createNewAccountButton;
    private JButton loginButton;

    BankLogin() {

        final JFrame frame = new JFrame("Bank Login Screen");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!(customerInterface.enterEmail(emailField.getText()) == null)) {
                    JOptionPane.showMessageDialog(null, customerInterface.enterEmail(emailField.getText()));
                    emailField.grabFocus();
                } else {
                    customerInterface.setCustomerInterfaceState(customerInterface.processPasswordState);
                    customerInterface.setCustomer(BankGUI.getBankProxy().requestCustomer(emailField.getText()));
                    if (!(customerInterface.enterPassword(String.valueOf(passwordField.getPassword())) == null)) {
                        JOptionPane.showMessageDialog(null, (customerInterface.enterPassword(String.valueOf(passwordField.getPassword()).toString())));
                        passwordField.setText("");
                        passwordField.grabFocus();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login Successful.");
                        customerInterface.setCustomerInterfaceState(customerInterface.loggedInState);
                        frame.setVisible(false);
                        BankDashboard bankDashboard = new BankDashboard(customerInterface);
                        bankDashboard.setCustomerInterface(customerInterface);
                    }
                }
            }
        });
        createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //ToDo: bring up create account pages here.
                CreateAccount createAccount = new CreateAccount();
                frame.setVisible(false);
            }
        });


    }

    @Override
    public void loadForm() {

    }

    @Override
    public boolean validateForm() {
        return false;
    }

    @Override
    public void setVisible(boolean visible) {

    }
}
