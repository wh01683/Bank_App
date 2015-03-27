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

    public static void main(String[] args) {
        JFrame frame = new JFrame("BankDashboard");
        frame.setContentPane(new BankDashboard().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public BankDashboard() {
    }
}
