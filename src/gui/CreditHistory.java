package gui;

import bank_package.CreditReport;
import bank_package.Customer;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * William Trent Holliday
 * 3/27/15
 */
public class CreditHistory implements FormGui {
    private static CreateAccount createAccount;
    private JPanel creditPage;
    private JTextField outstandingBalanceField;
    private final Border originalBorder = outstandingBalanceField.getBorder();
    private JTextField totalCreditLimitTextField;
    private JTextField latePaymentsAmountField;
    private JButton backButton;
    private JButton creditNextButton;
    private JTextField recentCreditInquiriesField;
    private JTextField numberOfLatePaymentsField;
    private JTextField lenCredHistoryField;
    private Customer customer;

    public CreditHistory(CreateAccount prevStep, final AccountCreationSummary nextStep) {
        createAccount = prevStep;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Load the previous form
                createAccount.loadForm();
            }
        });
        creditNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (validateForm()) {
                    Customer customer = addCreditReportCustomer();
                    nextStep.populateAccountSummary(customer);
                    createAccount.setContentPane(nextStep.getPanel());
                    createAccount.pack();
                    createAccount.setVisible(true);
                }
            }
        });
    }

    /**
     * Create the credit report for the customer. This is called after validation of the user input, so
     * we do not do any error checking.
     *
     * @return the customer object that we added the credit report too
     */
    private Customer addCreditReportCustomer(){
        int age = customer.getAge();

        int latePaymentsOnRecord = Integer.parseInt(this.numberOfLatePaymentsField.getText());
        double amountOfLatePayments = Double.parseDouble(this.latePaymentsAmountField.getText());
        int recentCredInquiries = Integer.parseInt(this.recentCreditInquiriesField.getText());
        double credLimit = Double.parseDouble(this.totalCreditLimitTextField.getText());
        double accountBalance = Double.parseDouble(this.outstandingBalanceField.getText());
        int lenCredHistory = Integer.parseInt(this.lenCredHistoryField.getText());

        CreditReport creditReport =  new CreditReport(
                                            age, latePaymentsOnRecord,
                                            amountOfLatePayments, recentCredInquiries,
                                            credLimit, accountBalance, lenCredHistory
                                        );
        customer.setCreditReport(creditReport);
        return customer;
    }

    public JPanel getPanel() {
        return creditPage;
    }

    @Override
    public void loadForm() {
        createAccount.setContentPane(getPanel());
        createAccount.pack();
    }

    @Override
    public boolean validateForm() {
        Method  doubleParseMethod = null,
                integerParseMethod = null;
        try {
            doubleParseMethod = Double.class.getMethod("parseDouble", String.class);
            integerParseMethod = Integer.class.getMethod("parseInt", String.class);
        }catch (NoSuchMethodException ex){
            System.out.println("Invalid parse method specified.");
            ex.printStackTrace();
        }
        // Validates every field, if any are not valid the whole form will be invalid.
        return  FormValidation.validateField(doubleParseMethod, this.totalCreditLimitTextField) &&
                FormValidation.validateField(integerParseMethod, this.outstandingBalanceField) &&
                FormValidation.validateField(integerParseMethod, this.recentCreditInquiriesField) &&
                FormValidation.validateField(integerParseMethod, this.numberOfLatePaymentsField) &&
                FormValidation.validateField(doubleParseMethod, this.latePaymentsAmountField) &&
                FormValidation.validateField(integerParseMethod, this.lenCredHistoryField);
    }

    @Override
    public void setVisible(boolean visible) {

    }

    /**
     * sets the current customer to the new passed customer
     *
     * @param customer new customer object
     */
    public void setCustomer(Customer customer){
        this.customer = customer;
    }

}
