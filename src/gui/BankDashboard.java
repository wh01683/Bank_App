package gui;

import acct.Account;
import bank_interface.CustomerInterface;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class BankDashboard {
    private static CustomerInterface customerInterface;
    private static Hashtable<Integer, Integer> rowPositionToAccountNumberArray;
    private JPanel panel1;
    private JLabel userName;
    private JTable accountsTable;
    private JButton createNewAccountButton;
    private JButton logoutButton;
    private JButton closeButton;
    private JTextField openingBalanceField;
    private JComboBox accountSelection;
    private JButton startTransactionButton;
    private JComboBox transactionSelection;
    private JTextField transactionAmountField;

    public BankDashboard(CustomerInterface newCustomerInterface) {
        customerInterface = newCustomerInterface;
        final JFrame frame = new JFrame("BankDashboard");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.userName.setText(customerInterface.getCustomer().getName());
        DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();
        String[] columnHeaders = {"TYPE", "ACCOUNT NUMBER", "BALANCE", "OWNER"};
        for (String s : columnHeaders) {
            model.addColumn(s);
        }
        model.addRow(columnHeaders);
        rowPositionToAccountNumberArray = new Hashtable<Integer, Integer>(customerInterface.getCustomer().getAccountHashtable().size());
        populateAccountTable();

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

        this.startTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startTransaction();
            }
        });

        this.createNewAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    JOptionPane.showMessageDialog(null, customerInterface.addAccount(accountSelection.getSelectedItem().toString(),
                            Double.parseDouble(openingBalanceField.getText())));
                    populateAccountTable();

                } catch (NullPointerException n) {
                    JOptionPane.showMessageDialog(null, "You are not eligible for a " + accountSelection.getSelectedItem() + " account at this time.");
                    accountSelection.setSelectedIndex(0); //reset drop down selection to default
                    openingBalanceField.setText(""); //reset opening balance field to blank

                } catch (NumberFormatException o) {
                    JOptionPane.showMessageDialog(null, "Must enter an opening balance value.");
                    openingBalanceField.grabFocus();
                }
            }
        });

    }

    public CustomerInterface getCustomerInterface() {
        return customerInterface;
    }

    public void setCustomerInterface(CustomerInterface newCustomerInterface) {
        customerInterface = newCustomerInterface;
    }

    public void populateAccountTable() {

        Hashtable<Integer, Account> customerAccounts = customerInterface.getCustomer().getAccountHashtable();
        String[][] tableContents = new String[customerAccounts.size()][4];
        clearTable();


        Enumeration<Integer> keys = customerAccounts.keys();

        int i = 0;
        while (keys.hasMoreElements()) {
            Integer key = keys.nextElement();
            tableContents[i][0] = customerAccounts.get(key).getType();
            tableContents[i][1] = customerAccounts.get(key).getACCOUNT_NUMBER() + "";
            tableContents[i][2] = customerAccounts.get(key).getBalance() + "";
            tableContents[i][3] = customerInterface.getCustomer().getName();
            rowPositionToAccountNumberArray.put(i, customerAccounts.get(key).getACCOUNT_NUMBER());
            i++;
        }
        DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();


        for (int k = 0; k < tableContents.length; k++) {
            model.addRow(tableContents[k]);
        }

        accountsTable.setModel(model);

    }

    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) accountsTable.getModel();
        model.setRowCount(1);
        accountsTable.setModel(model);
    }

    public void startTransaction() {
        String transactionChoice = (String) transactionSelection.getSelectedItem();
        Double transactionAmount = Double.parseDouble(transactionAmountField.getText());
        Integer toAccountNumber = rowPositionToAccountNumberArray.get(accountsTable.getSelectedRow() - 1);
        Integer fromAccountNumber = rowPositionToAccountNumberArray.get(accountsTable.getSelectedRow() - 1);

        JOptionPane.showMessageDialog(null, customerInterface.startTransaction(transactionChoice, fromAccountNumber, toAccountNumber, transactionAmount, transactionAmount));

        populateAccountTable();

    }
}

