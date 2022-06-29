package io.ciera.runtime.application;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractEvent implements Event {

  private static final long serialVersionUID = 1L;

  private final UniqueId eventHandle;
  private final int eventId;
  private final Object[] parameterData;

  public AbstractEvent(final int eventId, final Object... data) {
    this(UniqueId.random(), eventId, data);
  }

  public AbstractEvent(final UniqueId eventHandle, final int eventId, final Object... data) {
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
  public UniqueId getEventHandle() {
    return eventHandle;
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
  public boolean equals(final Object o) {
    return o instanceof Event && eventHandle.equals(((Event) o).getEventHandle());
  }

  @Override
  public int hashCode() {
    return eventHandle.hashCode();
  }

  @Override
  public String toString() {
    return String.format(
        "%s.%s[%.8s]",
        getClass().getDeclaringClass().getSimpleName(), getClass().getSimpleName(), eventHandle);
  }
}
