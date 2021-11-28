package io.ciera.runtime.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class WallClock extends SystemClock {

    private final Instant STARTUP_INSTANT;
    private final long STARTUP_NANOS;
    private final long STARTUP_EPOCH_NANOS;

    private long systemTimeOffset;

    public WallClock(ExecutionContext context) {
        super(context);
        this.STARTUP_INSTANT = Instant.now();
        this.STARTUP_NANOS = System.nanoTime();
        this.STARTUP_EPOCH_NANOS = getEpoch().until(STARTUP_INSTANT, ChronoUnit.NANOS);
        this.systemTimeOffset = 0;
    }

    @Override
    public long getTime() {
        return STARTUP_EPOCH_NANOS + (System.nanoTime() - STARTUP_NANOS) + systemTimeOffset;
    }

    @Override
    public void setTime(long time) {
        this.systemTimeOffset += time - getTime();
    }

    @Override
    protected void waitForNextTimer() throws InterruptedException {
        if (!activeTimers.isEmpty()) {
            long waitTime = activeTimers.peek().getTimer().getExpiration() - getTime();
            Thread.sleep(waitTime / 1000000l, (int) (waitTime % 1000000l));
        }
    }

}
