package algorithms;

import models.Token;
import utils.Metrics;
import utils.Stack;

import java.util.List;

public final class AlgorithmB {
    private AlgorithmB() {}

    public static int evaluateInfix(List<Token> tokens, Metrics metrics, boolean showStack) {
        Stack<Integer> operands = new Stack<>();
        Stack<String> operators = new Stack<>();
        int step = 0;
        for (Token t : tokens) {
            metrics.loopIterations++; step++;
            switch (t.getType()) {
                case NUMBER -> operands.push(Integer.parseInt(t.getValue()));
                case LEFT_PAREN -> operators.push("(");
                case RIGHT_PAREN -> {
                    while (!operators.isEmpty() && !operators.peek().equals("(")) applyTop(operands, operators, metrics);
                    if (operators.isEmpty()) throw new IllegalArgumentException("วงเล็บไม่สมดุล");
                    operators.pop();
                }
                case OPERATOR -> {
                    String cur = t.getValue();
                    while (!operators.isEmpty() && !operators.peek().equals("(") &&
                            ExpressionCommon.precedence(operators.peek()) >= ExpressionCommon.precedence(cur)) {
                        metrics.comparisons++;
                        applyTop(operands, operators, metrics);
                    }
                    operators.push(cur);
                }
            }
            if (showStack) System.out.printf("Step %d Token=%s Operand=%s Operator=%s%n", step, t.getValue(), operands, operators);
        }
        while (!operators.isEmpty()) applyTop(operands, operators, metrics);
        if (operands.size() != 1) throw new IllegalArgumentException("นิพจน์ไม่ถูกต้อง");
        metrics.pushCount += operands.getPushCount() + operators.getPushCount();
        metrics.popCount += operands.getPopCount() + operators.getPopCount();
        return operands.pop();
    }

    private static void applyTop(Stack<Integer> operands, Stack<String> operators, Metrics metrics) {
        if (operands.size() < 2) throw new IllegalArgumentException("Operand ไม่เพียงพอ");
        String op = operators.pop();
        int b = operands.pop(), a = operands.pop();
        metrics.comparisons++;
        operands.push(ExpressionCommon.apply(a, b, op));
    }
}
