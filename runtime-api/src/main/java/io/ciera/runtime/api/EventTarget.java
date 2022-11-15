package io.ciera.runtime.api;

public interface EventTarget {

  void queueEvent(Event event);

  void queueAcceleratedEvent(Event event);

  void queueDelayedEvent(Timer delayedEvent);

  void cancelDelayedEvent(Timer delayedEvent);
}
