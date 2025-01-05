package joki_zaydan;

public class CalculatorOperations {
    public double evaluate(String expression) {
        return new ExpressionParser().parse(expression);
    }
}
