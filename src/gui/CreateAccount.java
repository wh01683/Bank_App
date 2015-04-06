package gui;

import bank_package.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class CreateAccount extends JFrame implements FormGui {
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
    private static AccountCreationSummary accountSummary;


    public CreateAccount() {
        this.setTitle("Create Your account");
        loadForm();

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (validateForm()) {
                    creditHistory.setCustomer(createCustomer());
                    createAccount.setContentPane(creditHistory.getPanel());
                    createAccount.setVisible(true);
                }
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
    }

    private boolean validateForm(){
        if (this.nameField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Name is a required field.");
            this.nameField.grabFocus();
            return false;
        }
        if (this.ageField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Age is a required field.");
            this.ageField.grabFocus();
            return false;
        }
        if (this.emailField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Email is a required field.");
            this.emailField.grabFocus();
            return false;
        }
        String firstPassword = String.valueOf(this.passwordField.getPassword());
        String confirmPassword = String.valueOf(this.passwordConfirmField.getPassword());

        if(firstPassword.equals("") || confirmPassword.equals("")){
            JOptionPane.showMessageDialog(null, "Must complete both password fields.");
            this.passwordField.setText("");
            this.passwordConfirmField.setText("");
            this.passwordField.grabFocus();
            return false;
        }

        if(!firstPassword.equals(confirmPassword)){
            JOptionPane.showMessageDialog(null, "Passwords do not match.");
            this.passwordField.setText("");
            this.passwordConfirmField.setText("");
            this.passwordField.grabFocus();
            return false;
        }

        return true;

    }

    public Customer createCustomer(){
        String name = this.nameField.getText();
        String email = this.emailField.getText();
        int age = Integer.parseInt(this.ageField.getText());
        String password = String.valueOf(this.passwordConfirmField.getPassword());

        return new Customer(name, email, age, password);
    }

    /**
     * Method to display the form to fill out the username and password information.
     * Used by CreditHistory to go back a step in the registration process.
     */
    @Override
    public void loadForm() {
        this.setContentPane(startPage);
        this.setSize(375, 390);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        createAccount = new CreateAccount();
        accountSummary = new AccountCreationSummary();
        creditHistory = new CreditHistory(createAccount, accountSummary);

        accountSummary.setPrev(creditHistory);
    }

}
