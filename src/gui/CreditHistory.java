package gui;

import bank_package.CreditReport;
import bank_package.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/27/15
 */
public class CreditHistory implements FormGui {
    private JPanel creditPage;
    private JTextField outstandingBalanceField;
    private JTextField totalCreditLimitTextField;
    private JTextField latePaymentsAmountField;
    private JButton backButton;
    private JButton creditNextButton;
    private JTextField recentCreditInquiriesField;
    private JTextField numberOfLatePaymentsField;
    private JTextField lenCredHistoryField;
    private static CreateAccount createAccount;
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
                Customer customer = addCreditReportCustomer();
                nextStep.populateAccountSummary(customer);
                createAccount.setContentPane(nextStep.getPanel());
                createAccount.setVisible(true);
            }
        });
    }

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
        createAccount.setVisible(true);
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

}
