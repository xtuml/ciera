package io.ciera.runtime.time;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Queue;

import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.time.Timer;

public class WallClock extends AbstractClock {

    private final Instant STARTUP_INSTANT;
    private final long STARTUP_NANOS;
    private final long STARTUP_EPOCH_NANOS;

    private long systemTimeOffset;

    public WallClock() {
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
    public void waitForNextTimer(ExecutionContext context) throws InterruptedException {
        Queue<Timer> timers = scheduledTimersMap.get(context);
        if (!timers.isEmpty()) {
            long waitTime = timers.peek().getExpiration() - getTime();
            Thread.sleep(waitTime / 1000000l, (int) (waitTime % 1000000l));
        }
    }

}
