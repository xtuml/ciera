package io.ciera.runtime;

import java.util.UUID;
import java.util.function.Supplier;

import io.ciera.runtime.api.Event;

public abstract class AbstractEvent implements Event {

  private static final long serialVersionUID = 1L;

  // TODO dependencies
  private final Supplier<UUID> idAssigner = IdAssigner::random;

  private final UUID eventHandle;
  private final int eventId;
  private final Object[] parameterData;

  public AbstractEvent(final int eventId, final Object... data) {
    this.eventHandle = idAssigner.get();
    this.eventId = eventId;
    parameterData = data;
  }

  public AbstractEvent(final UUID eventHandle, final int eventId, final Object... data) {
    this.eventHandle = eventHandle;
    this.eventId = eventId;
    parameterData = data;
  }

  @Override
  public int getEventId() {
    return eventId;
  }

  @Override
  public String getName() {
    return getClass().getSimpleName();
  }

  @Override
  public Object getData(final int index) {
    if (index >= 0 && index < parameterData.length) {
      return parameterData[index];
    } else {
      throw new IndexOutOfBoundsException(index);
    }
  }

  @Override
  public String toString() {
    return String.format(
        "%s.%s[%.8s]",
        getClass().getDeclaringClass().getSimpleName(), getClass().getSimpleName(), eventHandle);
  }
}
