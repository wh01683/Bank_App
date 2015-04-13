package gui;

import javax.swing.*;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class BankDashboard {
    private JPanel panel1;
    private JLabel userName;
    private JTable accountsTable;
    private JButton createNewAccountButton;
    private JButton logoutButton;
    private JButton closeButton;

    public BankDashboard() {
        JFrame frame = new JFrame("BankDashboard");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
