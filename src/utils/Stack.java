package utils;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {
    private final List<T> data = new ArrayList<>();
    private long pushCount = 0;
    private long popCount = 0;

    public void push(T value) { data.add(value); pushCount++; }
    public T pop() {
        if (data.isEmpty()) throw new IllegalStateException("Stack is empty");
        popCount++;
        return data.remove(data.size() - 1);
    }
    public T peek() {
        if (data.isEmpty()) throw new IllegalStateException("Stack is empty");
        return data.get(data.size() - 1);
    }
    public boolean isEmpty() { return data.isEmpty(); }
    public int size() { return data.size(); }
    public long getPushCount() { return pushCount; }
    public long getPopCount() { return popCount; }
    public List<T> snapshot() { return new ArrayList<>(data); }
    @Override public String toString() { return data.toString(); }
}
