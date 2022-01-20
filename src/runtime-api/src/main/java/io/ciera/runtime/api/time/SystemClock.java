package io.ciera.runtime.api.time;

import java.time.Instant;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;

public interface SystemClock {

    public long getTime();

    public void setTime(long time);

    public Instant getEpoch();

    public void setEpoch(Instant epoch);

    public void checkTimers(ExecutionContext context);

    public boolean hasScheduledTimers(ExecutionContext context);

    public void waitForNextTimer(ExecutionContext context) throws InterruptedException;

    public boolean scheduleTimer(ExecutionContext context, Timer timer, Event event, EventTarget target, long delay);

    public boolean cancelTimer(ExecutionContext context, Timer timer);

}
