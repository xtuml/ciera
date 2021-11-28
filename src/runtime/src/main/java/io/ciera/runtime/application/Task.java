package io.ciera.runtime.application;

import java.util.Arrays;

public abstract class Task implements Runnable, Comparable<Task>, Named {

    public static final int SELF_EVENT_PRIORITY = 0x30;
    public static final int SEQUENTIAL_EVENT_PRIORITY = 0x20;
    public static final int DEFAULT_PRIORITY = 0x10;

    private int sequenceNumber;
    private ExecutionContext context;

    public Task(ExecutionContext context) {
        this.sequenceNumber = context.nextSequenceNumber();
        this.context = context;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public ExecutionContext getContext() {
        return context;
    }

    public int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    public int compareTo(Task other) {
        return Arrays.compare(new int[] { 0xFF - getPriority(), getSequenceNumber() },
                new int[] { 0xFF - other.getPriority(), other.getSequenceNumber() });
    }

    @Override
    public String getName() {
        return String.format("%s[0x%X, %d]", getClass().getSimpleName(), getPriority(), getSequenceNumber());
    }

    @Override
    public String toString() {
        return getName();
    }

}