package gui;

import bank_interface.CustomerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class BankDashboard {
    private static CustomerInterface customerInterface;
    private JPanel panel1;
    private JLabel userName;
    private JTable accountsTable;
    private JButton createNewAccountButton;
    private JButton logoutButton;
    private JButton closeButton;
    private JTextField openingBalanceField;
    private JComboBox accountSelection;

    public BankDashboard(CustomerInterface newCustomerInterface) {
        customerInterface = newCustomerInterface;
        final JFrame frame = new JFrame("BankDashboard");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.userName.setText(customerInterface.getCustomer().getName());

        this.logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerInterface.logOff();
                frame.setVisible(false);
                JOptionPane.showMessageDialog(null, "You have successfully logged off.");
                BankLogin bankLogin = new BankLogin();
            }
        });

        this.closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customerInterface.logOff();
                frame.setVisible(false);
                System.exit(0);
            }
        });

        this.createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, customerInterface.addAccount(accountSelection.getSelectedItem().toString(),
                        Double.parseDouble(openingBalanceField.getText())));
            }
        });

    }

    public CustomerInterface getCustomerInterface() {
        return customerInterface;
    }

    public void setCustomerInterface(CustomerInterface newCustomerInterface) {
        customerInterface = newCustomerInterface;
    }
}
