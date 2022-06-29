package io.ciera.runtime.time;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;

public abstract class AbstractClock implements SystemClock {

  protected final Map<ExecutionContext, Queue<Timer>> scheduledTimersMap;
  private final Set<Timer> allScheduledTimers;

  private Instant epoch;

  public AbstractClock() {
    scheduledTimersMap = new ConcurrentHashMap<>();
    allScheduledTimers = Collections.synchronizedSet(new HashSet<>());
    epoch = Instant.EPOCH;
  }

  @Override
  public abstract long getTime();

  @Override
  public abstract void setTime(long time);

  @Override
  public Instant getEpoch() {
    return epoch;
  }

  @Override
  public void setEpoch(final Instant epoch) {
    this.epoch = epoch;
  }

  @Override
  public void checkTimers(final ExecutionContext context) {
    final Queue<Timer> timers = scheduledTimersMap.get(context);
    while (timers != null && !timers.isEmpty() && getTime() >= timers.peek().getExpiration()) {
      final Timer timer = timers.poll();
      // long clockError = getTime() - timer.expiration; TODO report if greater than
      // some threshhold
      allScheduledTimers.remove(timer);
      timer.fire();
    }
  }

  @Override
  public Stream<Timer> getScheduledTimers(final ExecutionContext context) {
    final Queue<Timer> timers = scheduledTimersMap.get(context);
    if (timers != null) {
      return timers.stream();
    } else {
      return Stream.of();
    }
  }

  @Override
  public boolean hasScheduledTimers(final ExecutionContext context) {
    final Queue<Timer> timers = scheduledTimersMap.get(context);
    return timers != null && !timers.isEmpty();
  }

  @Override
  public boolean registerTimer(final Timer timer) {
    if (allScheduledTimers.add(timer)) {
      Queue<Timer> timers = scheduledTimersMap.get(timer.getContext());
      if (timers == null) {
        timers = new PriorityQueue<>();
        scheduledTimersMap.put(timer.getContext(), timers);
      }
      timers.add(timer);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean unregisterTimer(final Timer timer) {
    if (allScheduledTimers.remove(timer)) {
      final Queue<Timer> timers = scheduledTimersMap.get(timer.getContext());
      if (timers != null) {
        timers.remove(timer);
      }
      return true;
    } else {
      return false;
    }
  }
}
