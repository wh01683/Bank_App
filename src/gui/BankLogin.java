package gui;

import javax.swing.*;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class BankLogin {
    private JPanel panel1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JButton createNewAccountButton;
    private JButton loginButton;

    public BankLogin() {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("bank_gui");
        frame.setContentPane(new BankLogin().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
