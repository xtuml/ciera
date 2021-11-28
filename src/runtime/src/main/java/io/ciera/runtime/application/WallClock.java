package io.ciera.runtime.application;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class WallClock extends SystemClock implements Runnable {

    private final long tickDuration;

    private final Instant STARTUP_INSTANT;
    private final long STARTUP_NANOS;
    private final long STARTUP_EPOCH_NANOS;

    private long systemTimeOffset;

    public WallClock(ExecutionContext context) {
        this(context, 1000000l);
    }

    public WallClock(ExecutionContext context, long tickDuration) {
        super(context);
        this.tickDuration = tickDuration;
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
    public void run() {
        while (getContext().getApplication().isRunning()) {
            long start = System.nanoTime();
            // handle all expired timers
            synchronized (this) {
                while (!getActiveTimers().isEmpty() && getActiveTimers().peek().isExpired(getTime())) {
                    expireTimer(getActiveTimers().poll());
                }
            }
            // sleep
            long sleepTime = Math.max(tickDuration - (System.nanoTime() - start), 0);
            try {
                Thread.sleep(sleepTime / 1000000l, (int) (sleepTime % 1000000l));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            }
        }
    }

}
