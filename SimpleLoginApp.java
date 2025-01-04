// package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleLoginApp {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Halaman Login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JLabel messageLabel = new JLabel();

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        loginPanel.add(loginButton);
        loginPanel.add(messageLabel);

        frame.add(loginPanel);
        frame.setVisible(true);

        // Username dan Password yang diinisiasi
        String correctUsername = "user";
        String correctPassword = "pass";

        // Halaman Perhitungan
        JPanel calcPanel = new JPanel();
        calcPanel.setLayout(new GridLayout(3, 2));

        JLabel num1Label = new JLabel("Number 1:");
        JLabel num2Label = new JLabel("Number 2:");
        JTextField num1Field = new JTextField();
        JTextField num2Field = new JTextField();
        JButton calcButton = new JButton("Calculate");
        JLabel resultLabel = new JLabel("Result:");

        calcPanel.add(num1Label);
        calcPanel.add(num1Field);
        calcPanel.add(num2Label);
        calcPanel.add(num2Field);
        calcPanel.add(calcButton);
        calcPanel.add(resultLabel);

        // ActionListener untuk tombol login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (username.equals(correctUsername) && password.equals(correctPassword)) {
                    // Ganti ke halaman perhitungan
                    frame.remove(loginPanel);
                    frame.add(calcPanel);
                    frame.validate();
                    frame.repaint();
                } else {
                    messageLabel.setText("Invalid username or password.");
                }
            }
        });

        // ActionListener untuk tombol calculate
        calcButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double num1 = Double.parseDouble(num1Field.getText());
                    double num2 = Double.parseDouble(num2Field.getText());
                    double result = num1 + num2;
                    resultLabel.setText("Result: " + result);
                } catch (NumberFormatException ex) {
                    resultLabel.setText("Please enter valid numbers.");
                }
            }
        });
    }
}