package io.ciera.runtime.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class SimulatedClock extends SystemClock {

    private long systemTime;

    public SimulatedClock(ExecutionContext context) {
        super(context);
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
    protected void waitForNextTimer() {
        if (!activeTimers.isEmpty()) {
            long nextExpiration = activeTimers.peek().getExpiration();
            setTime(nextExpiration);
        }
    }

}
