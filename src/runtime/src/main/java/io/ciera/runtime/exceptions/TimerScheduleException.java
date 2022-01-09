package io.ciera.runtime.exceptions;

import io.ciera.runtime.application.Timer;
import io.ciera.runtime.types.Duration;

public class TimerScheduleException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Timer timer;
    private final Duration delay;

    public TimerScheduleException(String message, Timer timer, Duration delay) {
        super(message);
        this.timer = timer;
        this.delay = delay;
    }

    public Timer getTimer() {
        return timer;
    }

    public Duration getDelay() {
        return delay;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + ": [timer=" + timer + ", delay=" + delay + "]";
    }

}