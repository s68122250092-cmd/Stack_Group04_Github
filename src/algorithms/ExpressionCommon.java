package algorithms;

import models.Token;
import utils.Metrics;
import utils.Stack;

import java.util.ArrayList;
import java.util.List;

public final class ExpressionCommon {
    private ExpressionCommon() {}

    public static List<Token> tokenize(String expression) {
        if (expression == null || expression.trim().isEmpty())
            throw new IllegalArgumentException("นิพจน์ว่าง");
        List<Token> tokens = new ArrayList<>();
        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }
            if (Character.isDigit(c)) {
                int start = i;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) i++;
                tokens.add(new Token(Token.Type.NUMBER, expression.substring(start, i)));
                continue;
            }
            if (c == '+' || c == '-' || c == '*' || c == '/')
                tokens.add(new Token(Token.Type.OPERATOR, String.valueOf(c)));
            else if (c == '(') tokens.add(new Token(Token.Type.LEFT_PAREN, "("));
            else if (c == ')') tokens.add(new Token(Token.Type.RIGHT_PAREN, ")"));
            else throw new IllegalArgumentException("พบอักขระที่ไม่รองรับ: " + c);
            i++;
        }
        validateTokens(tokens);
        return tokens;
    }

    public static void validateTokens(List<Token> tokens) {
        boolean expectOperand = true;
        int balance = 0;
        for (Token t : tokens) {
            switch (t.getType()) {
                case NUMBER -> {
                    if (!expectOperand) throw new IllegalArgumentException("Operand ติดกันโดยไม่มี Operator");
                    expectOperand = false;
                }
                case LEFT_PAREN -> {
                    if (!expectOperand) throw new IllegalArgumentException("ไม่มี Operator ก่อน '('");
                    balance++; expectOperand = true;
                }
                case RIGHT_PAREN -> {
                    if (expectOperand) throw new IllegalArgumentException("')' อยู่ผิดตำแหน่ง");
                    if (--balance < 0) throw new IllegalArgumentException("วงเล็บปิดเกิน");
                    expectOperand = false;
                }
                case OPERATOR -> {
                    if (expectOperand) throw new IllegalArgumentException("Operator อยู่ผิดตำแหน่ง");
                    expectOperand = true;
                }
            }
        }
        if (balance != 0) throw new IllegalArgumentException("วงเล็บไม่สมดุล");
        if (expectOperand) throw new IllegalArgumentException("นิพจน์จบด้วย Operator หรือไม่มี Operand");
    }

    public static int precedence(String op) {
        return (op.equals("+") || op.equals("-")) ? 1 : (op.equals("*") || op.equals("/")) ? 2 : -1;
    }

    public static int apply(int a, int b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> { if (b == 0) throw new ArithmeticException("หารด้วยศูนย์ไม่ได้"); yield a / b; }
            default -> throw new IllegalArgumentException("Operator ไม่รองรับ: " + op);
        };
    }

    public static String tokensToString(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        for (Token t : tokens) { if (sb.length() > 0) sb.append(' '); sb.append(t.getValue()); }
        return sb.toString();
    }
}
