
// Class untuk UI dengan Java Swing
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Kelas utama untuk memulai aplikasi
public class CalculatorApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CalculatorUI::new);
    }
}

// Kelas untuk membuat User Interface (UI)
class CalculatorUI {
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

        // Tambahkan tombol angka dan operasi ke panel
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

        // Input dan history
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

    // Listener untuk tombol
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

// Kelas untuk menangkap input dari pengguna
class CalculatorInput {
    private final StringBuilder currentInput;

    public CalculatorInput() {
        currentInput = new StringBuilder();
    }

    public void append(String value) {
        currentInput.append(value);
    }

    public void deleteLast() {
        if (currentInput.length() > 0) {
            currentInput.deleteCharAt(currentInput.length() - 1);
        }
    }

    public void clear() {
        currentInput.setLength(0);
    }

    public String getCurrentInput() {
        return currentInput.toString();
    }
}

// Kelas untuk melakukan perhitungan
class CalculatorLogic {
    public static String evaluateExpression(String expression) {
        try {
            return Double.toString(new CalculatorOperations().evaluate(expression));
        } catch (Exception e) {
            return "Error";
        }
    }
}

// Kelas untuk menjalankan operasi matematika
class CalculatorOperations {
    // Metode untuk evaluasi ekspresi dengan prioritas operator
    public double evaluate(String expression) {
        return new ExpressionParser().parse(expression);
    }
}

// Kelas untuk parsing ekspresi (relation tambahan)
class ExpressionParser {
    public double parse(String expression) {
        return evaluateExpressionWithPriority(expression);
    }

    // Logika mendahulukan operasi *, /, %, lalu +, -
    private double evaluateExpressionWithPriority(String expression) {
        String[] tokens = expression.split("(?<=[-+*/%])|(?=[-+*/%])");
        java.util.Stack<Double> numbers = new java.util.Stack<>();
        java.util.Stack<String> operators = new java.util.Stack<>();

        for (String token : tokens) {
            if (token.matches("\\d+(\\.\\d+)?")) {
                numbers.push(Double.parseDouble(token));
            } else if (token.matches("[+*/%-]")) {
                while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), token)) {
                    compute(numbers, operators.pop());
                }
                operators.push(token);
            }
        }

        while (!operators.isEmpty()) {
            compute(numbers, operators.pop());
        }

        return numbers.pop();
    }

    private boolean hasHigherPrecedence(String op1, String op2) {
        if ((op1.equals("*") || op1.equals("/") || op1.equals("%")) && (op2.equals("+") || op2.equals("-"))) {
            return true;
        }
        return false;
    }

    private void compute(java.util.Stack<Double> numbers, String operator) {
        double b = numbers.pop();
        double a = numbers.pop();
        switch (operator) {
            case "+":
                numbers.push(a + b);
                break;
            case "-":
                numbers.push(a - b);
                break;
            case "*":
                numbers.push(a * b);
                break;
            case "/":
                numbers.push(a / b);
                break;
            case "%":
                numbers.push(a % b);
                break;
        }
    }
}
