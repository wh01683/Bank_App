package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * William Trent Holliday
 * 3/16/15
 */
public class CreateAccount {
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JButton backButton;
    private JButton finishButton;
    private JPanel secondPanel;
    private JButton continueButton;
    private JButton cancelButton;
    private JPanel firstPanel;

    public CreateAccount() {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CreateAccount");
        frame.setContentPane(new CreateAccount().firstPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        /*continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setContentPane(secondPanel);
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setContentPane(firstPanel);
            }
        });*/
    }
}
