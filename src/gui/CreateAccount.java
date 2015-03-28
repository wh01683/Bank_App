package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class CreateAccount extends JFrame {
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private JTextField nameField;
    private JTextField ageField;
    private JTextField emailField;
    private JButton continueButton;
    private JButton cancelButton;
    private JPanel startPage;
    private static CreateAccount createAccount;
    private static CreditHistory creditHistory;

    public CreateAccount() {
        this.setTitle("Create Your account");
        loadUserForm();

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createAccount.setContentPane(creditHistory.getPanel());
                createAccount.setVisible(true);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                createAccount.setVisible(false);
                createAccount.dispose();
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(350, 400);
        this.setVisible(true);
    }

    /**
     * Method to display the form to fill out the username and password information.
     * Used by CreditHistory to go back a step in the registration process.
     */
    public void loadUserForm() {
        this.setContentPane(startPage);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        createAccount = new CreateAccount();
        creditHistory = new CreditHistory(createAccount);
    }

}
