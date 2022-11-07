package io.ciera.runtime;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.function.Supplier;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.DynamicObjectInstance;
import io.ciera.runtime.api.Event;

public abstract class AbstractDynamicObjectInstance extends AbstractObjectInstance
    implements DynamicObjectInstance {

  private static final long serialVersionUID = 1L;

  private Enum<?> currentState;
  private Queue<Event> pendingEvents;

  public AbstractDynamicObjectInstance() {
    currentState = null;
    pendingEvents = new LinkedList<>();
  }

  public AbstractDynamicObjectInstance(final UUID instanceId) {
    super(instanceId);
  }

  @Override
  public void initialize(final Domain domain, final Enum<?> initialState) {
    initialize(domain);
    currentState = initialState;
  }

  @Override
  public Enum<?> getCurrentState() {
    return currentState;
  }

  @Override
  public void consumeEvent(final Event event) {
    pendingEvents.add(event);
    handleNextEvent();
  }

  private void handleNextEvent() {
    final Event event = pendingEvents.poll();
    if (event != null) {
      getDomain()
          .getRuntime()
          .submit(
              () -> {
                final Supplier<Enum<?>> transition = getTransition(currentState, event);
                if (transition != null) {
                  // TODO logging error handling
                  try {
                    currentState = transition.get();
                  } catch (RuntimeException e) {
                    e.printStackTrace();
                    System.exit(1);
                  }
                } else {
                  // TODO event ignored
                  System.out.println("Event ignored: " + event);
                }
                handleNextEvent();
              });
    }
  }
}
