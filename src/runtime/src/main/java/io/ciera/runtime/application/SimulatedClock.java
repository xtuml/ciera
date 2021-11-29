package io.ciera.runtime.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Queue;

public class SimulatedClock extends SystemClock {

    private long systemTime;

    public SimulatedClock() {
        systemTime = getEpoch().until(Instant.now(), ChronoUnit.NANOS);
    }

    @Override
    public long getTime() {
        return systemTime;
    }

    @Override
    public void setTime(long time) {
        this.systemTime = time;
    }

    @Override
    protected void waitForNextTimer(ExecutionContext context) {
        Queue<Timer> contextTimers = activeTimers.get(context);
        if (!contextTimers.isEmpty()) {
            long nextExpiration = contextTimers.peek().getExpiration();
            setTime(nextExpiration);
        }
    }

}
