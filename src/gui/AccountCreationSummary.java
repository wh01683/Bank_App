package gui;

import bank_package.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/30/15
 */
public class AccountCreationSummary implements FormGui{
    private JPanel summaryPanel;
    private JLabel summaryTitle;
    private JLabel nameInfo;
    private JLabel emailInfo;
    private JLabel ageInfo;
    private JButton backButton;
    private JButton finishButton;
    private JLabel acctNo;
    private JLabel creditScoreInfo;
    private static FormGui prevForm;

    public AccountCreationSummary() {
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                prevForm.loadForm();
            }
        });
    }


    public void populateAccountSummary(Customer customer) {
        this.nameInfo.setText(customer.getName());
        this.ageInfo.setText(String.valueOf(customer.getAge()));
        this.emailInfo.setText(customer.getEmail());
        this.acctNo.setText(String.valueOf(customer.getUUID().hashCode()));
        this.creditScoreInfo.setText(String.valueOf(customer.getCreditScore()));
    }

    public static void setPrev(FormGui prevFormGui) {
        prevForm = prevFormGui;
    }

    public JPanel getPanel() {
        return summaryPanel;
    }

    @Override
    public void loadForm() {

    }
}
