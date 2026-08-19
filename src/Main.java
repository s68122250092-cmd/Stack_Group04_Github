import algorithms.AlgorithmA;
import algorithms.AlgorithmB;
import algorithms.ExpressionCommon;
import experiments.PerformanceTest;
import models.Token;
import utils.Metrics;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Expression Processor ===");
            System.out.println("1. Algorithm A: Infix -> Postfix -> Evaluate");
            System.out.println("2. Algorithm B: Evaluate Infix Directly");
            System.out.println("3. เปรียบเทียบ Algorithm A และ B");
            System.out.println("4. แสดงสถานะ Stack");
            System.out.println("5. Performance Test");
            System.out.println("0. ออกจากโปรแกรม");
            System.out.print("เลือกเมนู: ");
            String choice = sc.nextLine().trim();
            try {
                if (choice.equals("0")) break;
                if (choice.equals("5")) { PerformanceTest.run(); continue; }
                if (choice.equals("4")) { showStack(); continue; }
                System.out.print("กรอกนิพจน์: ");
                String expression = sc.nextLine();
                List<Token> tokens = ExpressionCommon.tokenize(expression);
                if (choice.equals("1")) runA(tokens);
                else if (choice.equals("2")) runB(tokens);
                else if (choice.equals("3")) compare(tokens);
                else System.out.println("กรุณาเลือกเมนู 0-5");
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        }
        sc.close();
    }

    private static void runA(List<Token> tokens) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        String postfix = AlgorithmA.infixToPostfix(tokens, m, false);
        int result = AlgorithmA.evaluatePostfix(postfix, m);
        m.elapsedNanos = System.nanoTime() - start;
        System.out.println("Tokens: " + ExpressionCommon.tokensToString(tokens));
        System.out.println("Postfix: " + postfix);
        System.out.println("Result: " + result);
        printMetrics(m);
    }

    private static void runB(List<Token> tokens) {
        Metrics m = new Metrics();
        long start = System.nanoTime();
        int result = AlgorithmB.evaluateInfix(tokens, m, false);
        m.elapsedNanos = System.nanoTime() - start;
        System.out.println("Tokens: " + ExpressionCommon.tokensToString(tokens));
        System.out.println("Result: " + result);
        printMetrics(m);
    }

    private static void compare(List<Token> tokens) {
        Metrics a = new Metrics(), b = new Metrics();
        long sa = System.nanoTime();
        String postfix = AlgorithmA.infixToPostfix(tokens, a, false);
        int ra = AlgorithmA.evaluatePostfix(postfix, a);
        a.elapsedNanos = System.nanoTime() - sa;
        long sb = System.nanoTime();
        int rb = AlgorithmB.evaluateInfix(tokens, b, false);
        b.elapsedNanos = System.nanoTime() - sb;
        System.out.println("A Postfix: " + postfix);
        System.out.println("A Result: " + ra); printMetrics(a);
        System.out.println("B Result: " + rb); printMetrics(b);
        System.out.println("Results equal: " + (ra == rb));
    }

    private static void showStack() {
        String expression = "3 + 4 * 2 / (1 - 5)";
        System.out.println("Expression: " + expression);
        List<Token> tokens = ExpressionCommon.tokenize(expression);
        Metrics m = new Metrics();
        String postfix = AlgorithmA.infixToPostfix(tokens, m, true);
        System.out.println("Postfix: " + postfix);
        System.out.println("Result: " + AlgorithmA.evaluatePostfix(postfix, m));
    }

    private static void printMetrics(Metrics m) {
        System.out.println("Comparisons: " + m.comparisons);
        System.out.println("Loop iterations: " + m.loopIterations);
        System.out.println("Push: " + m.pushCount);
        System.out.println("Pop: " + m.popCount);
        System.out.println("Operations (approx.): " + m.operations());
        System.out.println("Time (ns): " + m.elapsedNanos);
    }
}
