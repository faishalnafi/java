package joki_zaydan;

public class CalculatorInput {
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
