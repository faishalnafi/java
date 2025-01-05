// File: CalculatorUI.java
package joki_zaydan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorUI {
    private final JFrame frame;
    private final JTextField inputField;
    private final JTextArea historyArea;
    private final CalculatorInput inputHandler;

    public CalculatorUI() {
        frame = new JFrame("Kalkulator");
        inputField = new JTextField();
        historyArea = new JTextArea(5, 20);
        inputHandler = new CalculatorInput();

        initUI();
    }

    private void initUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "C", "DEL", "%"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        inputField.setFont(new Font("Arial", Font.PLAIN, 20));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        historyArea.setEditable(false);

        panel.add(inputField, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(new JScrollPane(historyArea), BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("C")) {
                inputHandler.clear();
                inputField.setText("");
                historyArea.setText("");
            } else if (command.equals("DEL")) {
                inputHandler.deleteLast();
                inputField.setText(inputHandler.getCurrentInput());
            } else if (command.equals("=")) {
                String result = CalculatorLogic.evaluateExpression(inputHandler.getCurrentInput());
                historyArea.append(inputHandler.getCurrentInput() + " = " + result + "\n");
                inputHandler.clear();
                inputField.setText(result);
            } else {
                inputHandler.append(command);
                inputField.setText(inputHandler.getCurrentInput());
            }
        }
    }
}