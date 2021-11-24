package io.ciera.runtime.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class WallClock extends SystemClock implements Runnable {

    private long tickDuration;

    private final Instant STARTUP_INSTANT;
    private final long STARTUP_NANOS;

    private long startupEpochNanos;
    private long systemTimeOffset;

    public WallClock(ExecutionContext context) {
        this(context, 1000000l);
    }

    public WallClock(ExecutionContext context, long tickDuration) {
        super(context);
        this.tickDuration = tickDuration;
        this.STARTUP_INSTANT = Instant.now();
        this.STARTUP_NANOS = System.nanoTime();
        this.startupEpochNanos = getEpoch().until(STARTUP_INSTANT, ChronoUnit.NANOS);
        this.systemTimeOffset = 0;
    }

    @Override
    public long getTime() {
        return startupEpochNanos + (System.nanoTime() - STARTUP_NANOS) + systemTimeOffset;
    }

    @Override
    public void setTime(long time) {
        this.systemTimeOffset += time - getTime();
    }

    @Override
    public void run() {
        while (getContext().getApplication().isRunning()) {
            long start = System.nanoTime();
            // handle all expired timers
            while (getActiveTimers().peek().isExpired(getTime())) {
                expireTimer(getActiveTimers().poll());
            }
            // sleep
            long sleepTime = tickDuration - (System.nanoTime() - start);
            try {
                Thread.sleep(sleepTime / 1000000l, (int) sleepTime % 1000000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            }
        }
    }

}
