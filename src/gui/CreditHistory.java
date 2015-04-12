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
        return  validateField(doubleParseMethod, this.totalCreditLimitTextField) &&
                validateField(integerParseMethod, this.outstandingBalanceField) &&
                validateField(integerParseMethod, this.recentCreditInquiriesField) &&
                validateField(integerParseMethod, this.numberOfLatePaymentsField) &&
                validateField(doubleParseMethod, this.latePaymentsAmountField) &&
                validateField(integerParseMethod, this.lenCredHistoryField);
    }

    /**
     * Validate the specified field using the give method. So for example to validate that a JTextField
     * called `field` contains an integer value you would call the validateField method like so:
     *
     *  try{
     *      validateField(Integer.class.getMethod("parseInt", String.class), field);
     *     } catch( NoSuchMethodException ex){}
     *
     * @param parseMethod the method to use to convert the field value to the correct data type.
     * @param field the field to validate.
     * @return boolean true, if the field is valid, false otherwise.
     */
    private boolean validateField(Method parseMethod, JTextComponent field) {
        try {
            // User did not enter anything into the field.
            if (field.getText().equals("")) {
                setFieldError(field);
                JOptionPane.showMessageDialog(null, "This field is required.");
                return false;
            }
            // Call the correct method to try and convert the input field string into the correct data type
            parseMethod.invoke(null, field.getText());
            // Remove the red error border
            field.setBorder(originalBorder);
            return true;
        } catch (IllegalAccessException e) {
            System.out.println("Error calling method: " + parseMethod);
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            setFieldError(field);
            JOptionPane.showMessageDialog(null, "Enter a valid value.");
            return false;
        }
    }

    /**
     * Adds a border to the specified component to indicate that it has erred.
     * @param component the component to add the border to
     */
    private void setFieldError(JComponent component){
        component.grabFocus();
        component.setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.RAISED, Color.RED, Color.red));
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
