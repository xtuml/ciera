package io.ciera.runtime.application;

import java.util.Arrays;

public abstract class Task implements Runnable, Comparable<Task> {

    public static final int SELF_EVENT_PRIORITY = 300;
    public static final int SEQUENTIAL_EVENT_PRIORITY = 200;
    public static final int DEFAULT_PRIORITY = 100;

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
        return Arrays.compare(new int[] { getPriority(), getSequenceNumber() },
                new int[] { other.getPriority(), other.getSequenceNumber() });
    }

}