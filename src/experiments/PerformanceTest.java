package experiments;

import algorithms.AlgorithmA;
import algorithms.AlgorithmB;
import algorithms.ExpressionCommon;
import models.Token;
import utils.Metrics;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class PerformanceTest {
    public static void run() {
        int[] sizes = {100, 1000, 10000, 50000};
        int rounds = 5;
        File resultsDir = new File("results");
        resultsDir.mkdirs();
        File out = new File(resultsDir, "performance_results.csv");
        try (PrintWriter pw = new PrintWriter(new FileWriter(out))) {
            pw.println("n,round,algorithm,time_ns,operations,comparisons,push,pop,loops,result");
            for (int n : sizes) {
                String expression = generateExpression(n);
                // Warm-up
                for (int i = 0; i < 3; i++) {
                    List<Token> t = ExpressionCommon.tokenize(expression);
                    AlgorithmA.evaluatePostfix(AlgorithmA.infixToPostfix(t, new Metrics(), false), new Metrics());
                    AlgorithmB.evaluateInfix(t, new Metrics(), false);
                }
                for (int r = 1; r <= rounds; r++) {
                    List<Token> tA = ExpressionCommon.tokenize(expression);
                    Metrics a = new Metrics();
                    long sa = System.nanoTime();
                    String postfix = AlgorithmA.infixToPostfix(tA, a, false);
                    int ra = AlgorithmA.evaluatePostfix(postfix, a);
                    a.elapsedNanos = System.nanoTime() - sa;
                    pw.printf("%d,%d,A,%d,%d,%d,%d,%d,%d,%d%n", n, r, a.elapsedNanos, a.operations(), a.comparisons, a.pushCount, a.popCount, a.loopIterations, ra);

                    List<Token> tB = ExpressionCommon.tokenize(expression);
                    Metrics b = new Metrics();
                    long sb = System.nanoTime();
                    int rb = AlgorithmB.evaluateInfix(tB, b, false);
                    b.elapsedNanos = System.nanoTime() - sb;
                    pw.printf("%d,%d,B,%d,%d,%d,%d,%d,%d,%d%n", n, r, b.elapsedNanos, b.operations(), b.comparisons, b.pushCount, b.popCount, b.loopIterations, rb);
                }
            }
            System.out.println("Saved: " + out.getPath());
        } catch (Exception e) {
            throw new RuntimeException("เขียนผลทดลองไม่ได้: " + e.getMessage(), e);
        }
    }

    private static String generateExpression(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) { if (i > 0) sb.append(" + "); sb.append('1'); }
        return sb.toString();
    }
}
