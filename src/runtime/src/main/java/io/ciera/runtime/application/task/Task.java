package io.ciera.runtime.application.task;

import java.util.Arrays;

import io.ciera.runtime.application.ThreadExecutionContext;

public abstract class Task implements Runnable, Comparable<Task> {

    public static final int SELF_EVENT_PRIORITY = 0x30;
    public static final int SEQUENTIAL_EVENT_PRIORITY = 0x20;
    public static final int DEFAULT_PRIORITY = 0x10;

    private int sequenceNumber;

    public Task() {
        this.sequenceNumber = ThreadExecutionContext.nextSequenceNumber();
    }

    public int getSequenceNumber() {
        return sequenceNumber;
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
    public String toString() {
        return String.format("%s[0x%X, %d]", getClass().getSimpleName(), getPriority(), getSequenceNumber());
    }

}