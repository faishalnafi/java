package joki_zaydan;

import java.util.Stack;

public class ExpressionParser {
    public double parse(String expression) {
        return evaluateExpressionWithPriority(expression);
    }

    // Logika mendahulukan operasi *, /, %, lalu +, -
    private double evaluateExpressionWithPriority(String expression) {
        String[] tokens = expression.split("(?<=[-+*/%])|(?=[-+*/%])");
        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

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

    }

    private boolean hasHigherPrecedence(String op1, String op2) {
        return (op1.equals("*") || op1.equals("/") || op1.equals("%")) && (op2.equals("+") || op2.equals("-"));
    }

    private void compute(Stack<Double> numbers, String operator) {
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
