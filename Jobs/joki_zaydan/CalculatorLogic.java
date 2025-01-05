package joki_zaydan;

public class CalculatorLogic {
    public static String evaluateExpression(String expression) {
        try {
            return Double.toString(new CalculatorOperations().evaluate(expression));
        } catch (Exception e) {
            return "Error";
        }
    }
}
