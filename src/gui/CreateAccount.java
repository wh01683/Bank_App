package gui;

import bank_interface.EmailValidator;
import bank_interface.PasswordChecker;
import bank_package.BankProxy;
import bank_package.Customer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class CreateAccount extends JFrame implements FormGui {
    private static BankProxy bankProxy = BankGUI.getBankProxy();
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField emailField;
    private JButton continueButton;
    private JButton cancelButton;
    private JPanel startPage;
    private JTextField firstNameField;
    private CreditHistory creditHistory;
    private AccountCreationSummary accountSummary;


    public CreateAccount() {
        FormValidation.storeDefaultBorder(this.firstNameField.getBorder());
        accountSummary = new AccountCreationSummary();
        creditHistory = new CreditHistory(this, accountSummary);
        AccountCreationSummary.setPrev(creditHistory);

        this.setTitle("Create Your account");
        loadForm();

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (validateForm()) {
                    creditHistory.setCustomer(createCustomer());
                    getCreateAccount().setContentPane(creditHistory.getPanel());
                    getCreateAccount().setVisible(true);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getCreateAccount().setVisible(false);
                getCreateAccount().dispose();
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean validateForm() {
        if (bankProxy.hasCustomer(this.emailField.getText())) {
            JOptionPane.showMessageDialog(null, "Email already in system.");
            this.emailField.grabFocus();
            return false;
        }
        boolean isValid = true;

        try {
            Method passwordCheckMethod = PasswordChecker.class.getMethod("strengthCheck", String.class);
            Method emailValidatorMethod = EmailValidator.class.getMethod("validate", String.class);
            Method integerParseMethod = Integer.class.getMethod("parseInt", String.class);

            isValid = FormValidation.validateField(null, this.firstNameField) &&
                      FormValidation.validateField(integerParseMethod, this.ageField) &&
                      FormValidation.validateField(null, this.emailField) &&
                      FormValidation.validateField(passwordCheckMethod, this.passwordField, "Password not strong enough.") &&
                      FormValidation.validateField(emailValidatorMethod, this.emailField);

            if (!isValid){
                return isValid;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (Integer.parseInt(this.ageField.getText()) > 200) {
            JOptionPane.showMessageDialog(null, "I know you aren't over 200 years old you piece of shit.\nI know you're lying. Stop it.");
            this.ageField.grabFocus();
            return false;
        } else if (Integer.parseInt(this.ageField.getText()) < 15) {
            JOptionPane.showMessageDialog(null, "You must be at least 15 years old to open an account here.");
            ageField.grabFocus();
            return false;
        } else if (Integer.parseInt(this.ageField.getText()) > 14 && Integer.parseInt(this.ageField.getText()) < 18) {
            JOptionPane.showMessageDialog(null, "You may create a bank account, but you will not be able to fill out credit information.");
            ageField.grabFocus();
            return true;
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

        return isValid;

    }

    private CreateAccount getCreateAccount(){
        return this;
    }

    public Customer createCustomer(){
        String name = this.lastNameField.getText();
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
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
