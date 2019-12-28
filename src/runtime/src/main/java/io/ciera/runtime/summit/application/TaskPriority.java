package io.ciera.runtime.summit.application;

public final class TaskPriority {

    // Internal events
    public static final int GENERATED_EVENT_TO_SELF_PRIORITY  = 0x01;
    public static final int GENERATED_EVENT_PRIORITY          = 0x02;

    // External events
    public static final int RECEIVED_MESSAGE_PRIORITY         = 0x10;
    public static final int RECEIVED_RETURN_MESSAGE_PRIORITY  = 0x11;
    public static final int TIMER_EXPIRED_PRIORITY            = 0x12;
    public static final int HALT_EXECUTION_PRIORITY           = 0x13;
    public static final int GENERIC_EXECUTION_PRIORITY        = 0x14;

    private TaskPriority() {
    }

}
