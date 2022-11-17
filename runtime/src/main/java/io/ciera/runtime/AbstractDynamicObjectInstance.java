package io.ciera.runtime;

import java.time.Instant;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.DynamicObjectInstance;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.Timer;

public abstract class AbstractDynamicObjectInstance extends AbstractObjectInstance
    implements DynamicObjectInstance {

  private static final long serialVersionUID = 1L;

  private final Supplier<Instant> clock = arch.getClock();
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final Queue<Event> pendingAcceleratedEvents = new LinkedList<>();
  private final Queue<Event> pendingEvents = new LinkedList<>();
  private final Queue<Timer> delayedEvents = new PriorityQueue<>();

  private Enum<?> currentState = null;
  private boolean inTransition = false;

  public AbstractDynamicObjectInstance() {}

  public AbstractDynamicObjectInstance(final UUID instanceId) {
    super(instanceId);
  }

  @Override
  public void initialize(final Domain domain, final Enum<?> initialState) {
    initialize(domain);
    this.currentState = initialState;
  }

  @Override
  public Enum<?> getCurrentState() {
    return currentState;
  }

  @Override
  public void queueEvent(final Event event) {
    pendingEvents.add(event);
  }

  @Override
  public void queueAcceleratedEvent(final Event event) {
    pendingAcceleratedEvents.add(event);
  }

  @Override
  public void queueDelayedEvent(final Timer delayedEvent) {
    delayedEvents.add(delayedEvent);
  }

  @Override
  public void cancelDelayedEvent(final Timer delayedEvent) {
    delayedEvents.remove(delayedEvent);
  }

  private void handleEvent(final Event event) {
    logger.info("TXN: {}, {}", currentState, event);
    final Supplier<Enum<?>> transition = getTransition(currentState, event);
    if (transition != null) {
      currentState = transition.get();
      logger.info("TXN: Transition complete: {}", currentState);
    } else {
      logger.info("TXN: Event ignored.");
    }
  }

  @Override
  public Runnable getTask() {
    while (!delayedEvents.isEmpty()) {
      if (clock.get().compareTo(delayedEvents.peek().getScheduledExpirationTime()) >= 0) {
        final Timer timer = delayedEvents.poll();
        // long clockError = getTime() - timer.expiration; TODO report if greater than
        // some threshhold
        timer.expireNow();
      } else {
        break; // reached the first timer that still has time left
      }
    }
    if (!inTransition && !pendingEvents.isEmpty()) {
      inTransition = true;
      final Event nextEvent = pendingEvents.poll();
      return () -> {
        try {
          handleEvent(nextEvent);
          while (!pendingAcceleratedEvents.isEmpty()) {
            handleEvent(pendingAcceleratedEvents.poll());
          }
        } catch (RuntimeException e) {
          throw e; // rethrow any runtime exceptions
        } finally {
          inTransition = false;
        }
      };
    } else {
      return null;
    }
  }

  @Override
  public long millisToNextTask() {
    return !delayedEvents.isEmpty()
        ? Long.max(delayedEvents.peek().remainingTime().toMillis(), 0)
        : Long.MAX_VALUE;
  }
}
