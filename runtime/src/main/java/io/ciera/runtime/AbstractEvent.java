package io.ciera.runtime;

import java.util.UUID;

import io.ciera.runtime.api.Architecture;
import io.ciera.runtime.api.Event;

public abstract class AbstractEvent implements Event {

  private static final long serialVersionUID = 1L;

  private final Architecture arch = Architecture.getInstance();

  private final UUID eventHandle;
  private final int eventId;
  private final Object[] data;

  public AbstractEvent(final int eventId, final Object... data) {
    this.eventHandle = arch.getIdAssigner().get();
    this.eventId = eventId;
    this.data = data;
  }

  public AbstractEvent(final UUID eventHandle, final int eventId, final Object... data) {
    this.eventHandle = eventHandle;
    this.eventId = eventId;
    this.data = data;
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
    if (index >= 0 && index < data.length) {
      return data[index];
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
