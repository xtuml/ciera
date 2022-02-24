package io.ciera.runtime.application.task;

import java.io.Serializable;
import java.util.Arrays;

public abstract class Task implements Runnable, Comparable<Task>, Serializable {

    private static final long serialVersionUID = 1L;

    public static final int SELF_EVENT_PRIORITY = 0x30;
    public static final int SEQUENTIAL_EVENT_PRIORITY = 0x20;
    public static final int DEFAULT_PRIORITY = 0x10;

    private long sequenceNumber;

    public Task() {
        sequenceNumber = System.currentTimeMillis();
    }

    public int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    public int compareTo(Task other) {
        return Arrays.compare(new long[] { 0xFF - getPriority(), sequenceNumber },
                new long[] { 0xFF - other.getPriority(), other.sequenceNumber });
    }

    @Override
    public String toString() {
        return String.format("%s[0x%X, %d]", getClass().getSimpleName(), getPriority(), sequenceNumber);
    }

}