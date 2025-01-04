import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import javax.swing.*;

// Kelas utama yang mengatur UI dan logika kalkulator
public class Kalkulator {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(KalkulatorFrame::new);
    }
}

// Kelas KalkulatorFrame untuk mengatur tampilan antarmuka pengguna
class KalkulatorFrame extends JFrame {
    private JTextField display;

    public KalkulatorFrame() {
        setTitle("Kalkulator Sederhana");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "(", ")", "C", "CE"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Listener untuk menangani tombol yang ditekan
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "C":
                    display.setText(""); // Membersihkan semua input
                    break;
                case "CE":
                    if (!display.getText().isEmpty()) {
                        display.setText(display.getText().substring(0, display.getText().length() - 1));
                    } // Menghapus karakter terakhir
                    break;
                case "=":
                    try {
                        String result = Evaluator.evaluateExpression(display.getText());
                        display.setText(result);
                    } catch (Exception ex) {
                        display.setText("Error"); // Menampilkan kesalahan jika ada
                    }
                    break;
                default:
                    display.setText(display.getText() + command); // Menambahkan input ke layar
            }
        }
    }
}

// Kelas Evaluator untuk menghitung ekspresi matematika
class Evaluator {

    // Method untuk mengevaluasi ekspresi matematika
    public static String evaluateExpression(String expression) {
        return String.valueOf(calculate(expression));
    }

    // Menghitung hasil ekspresi menggunakan Stack
    private static double calculate(String expression) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Jika karakter adalah spasi, lanjutkan ke karakter berikutnya
            if (c == ' ') continue;

            // Jika karakter adalah angka
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();

                // Menangkap angka penuh (termasuk desimal)
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--;

                values.push(Double.parseDouble(sb.toString()));
            } else if (c == '(') {
                operators.push(c); // Menyimpan tanda kurung buka
            } else if (c == ')') {
                // Memproses sampai menemukan tanda kurung buka
                while (!operators.isEmpty() && operators.peek() != '(') {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop();
            } else if (isOperator(c)) {
                // Memproses operator berdasarkan prioritas
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(c)) {
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(c);
            }
        }

        // Memproses sisa operator
        while (!operators.isEmpty()) {
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    // Mengecek apakah karakter adalah operator
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Menentukan prioritas operator
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    // Menghitung dua angka berdasarkan operator
    private static double applyOperator(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
        }
        return 0;
    }
}
