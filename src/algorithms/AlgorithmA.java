package algorithms;

import models.Token;
import utils.Metrics;
import utils.Stack;

import java.util.List;

public final class AlgorithmA {
    private AlgorithmA() {}

    public static String infixToPostfix(List<Token> tokens, Metrics metrics, boolean showStack) {
        Stack<String> operators = new Stack<>();
        StringBuilder output = new StringBuilder();
        int step = 0;
        for (Token t : tokens) {
            metrics.loopIterations++; step++;
            switch (t.getType()) {
                case NUMBER -> output.append(t.getValue()).append(' ');
                case LEFT_PAREN -> operators.push("(");
                case RIGHT_PAREN -> {
                    while (!operators.isEmpty() && !operators.peek().equals("("))
                        output.append(operators.pop()).append(' ');
                    if (operators.isEmpty()) throw new IllegalArgumentException("วงเล็บไม่สมดุล");
                    operators.pop();
                }
                case OPERATOR -> {
                    String cur = t.getValue();
                    while (!operators.isEmpty() && !operators.peek().equals("(") &&
                            ExpressionCommon.precedence(operators.peek()) >= ExpressionCommon.precedence(cur)) {
                        metrics.comparisons++;
                        output.append(operators.pop()).append(' ');
                    }
                    operators.push(cur);
                }
            }
            if (showStack) System.out.printf("Step %d Token=%s Stack=%s Output=%s%n", step, t.getValue(), operators, output.toString().trim());
        }
        while (!operators.isEmpty()) {
            String op = operators.pop();
            if (op.equals("(")) throw new IllegalArgumentException("วงเล็บไม่สมดุล");
            output.append(op).append(' ');
        }
        metrics.pushCount += operators.getPushCount();
        metrics.popCount += operators.getPopCount();
        return output.toString().trim();
    }

    public static int evaluatePostfix(String postfix, Metrics metrics) {
        Stack<Integer> values = new Stack<>();
        if (postfix == null || postfix.trim().isEmpty()) throw new IllegalArgumentException("Postfix ว่าง");
        for (String part : postfix.trim().split("\\s+")) {
            metrics.loopIterations++;
            if (part.matches("\\d+")) values.push(Integer.parseInt(part));
            else {
                if (values.size() < 2) throw new IllegalArgumentException("Postfix ไม่ถูกต้อง");
                int b = values.pop(), a = values.pop();
                metrics.comparisons++;
                values.push(ExpressionCommon.apply(a, b, part));
            }
        }
        if (values.size() != 1) throw new IllegalArgumentException("Postfix ไม่ถูกต้อง");
        metrics.pushCount += values.getPushCount();
        metrics.popCount += values.getPopCount();
        return values.pop();
    }
}
