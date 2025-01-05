package joki_zaydan;

import java.util.Stack;

public class ExpressionParser {
    public double parse(String expression) {
        return evaluateExpressionWithPriority(expression);
    }

    private double evaluateExpressionWithPriority(String expression) {
        String[] tokens = expression.split("(?<=[-+*/%])|(?=[-+*/%])");
        Stack<Double> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();

        for (String token : tokens) {
            if (token.matches("\\\\d+(\\\\.\\\\d+)?")) {
                numbers.push(Double.parseDouble(token));
            } else if (token.matches("[+*/%-]")) {
                while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), token)) {
                    compute(numbers, operators.pop());
