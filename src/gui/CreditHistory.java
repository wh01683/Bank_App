package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/27/15
 */
public class CreditHistory {
    private JPanel creditPage;
    private JTextField outstandingBalanceField;
    private JTextField totalCreditLimitTextField;
    private JTextField latePaymentsAmountField;
    private JButton backButton;
    private JButton finishButton;
    private JTextField recentCreditInquiriesField;
    private JTextField numberOfLatePaymentsField;
    private JTextField textField4;

    public CreditHistory(final CreateAccount prevStep) {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // Load the previous form
                prevStep.loadUserForm();
            }
        });
    }

    public JPanel getPanel() {
        return creditPage;
    }

}
