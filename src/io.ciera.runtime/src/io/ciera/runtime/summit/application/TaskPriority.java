package io.ciera.runtime.summit.application;

public final class TaskPriority {

    public static final int HALT_EXECUTION_PRIORITY = 6;
    public static final int RECEIVED_MESSAGE_PRIORITY = 5;
    public static final int RECEIVED_RETURN_MESSAGE_PRIORITY = 4;
    public static final int GENERATED_EVENT_TO_SELF_PRIORITY = 3;
    public static final int GENERATED_EVENT_PRIORITY = 2;
    public static final int POPPED_TIMER_PRIORITY = 1;
    public static final int GENERIC_EXECUTION_PRIORITY = 0;

    private TaskPriority() {
    }

}
