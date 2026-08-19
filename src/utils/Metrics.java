package utils;

public class Metrics {
    public long comparisons;
    public long loopIterations;
    public long pushCount;
    public long popCount;
    public long elapsedNanos;
    public void add(Metrics other) {
        comparisons += other.comparisons;
        loopIterations += other.loopIterations;
        pushCount += other.pushCount;
        popCount += other.popCount;
        elapsedNanos += other.elapsedNanos;
    }
    public long operations() { return comparisons + loopIterations + pushCount + popCount; }
}
