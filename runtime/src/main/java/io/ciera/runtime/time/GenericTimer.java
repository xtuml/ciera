package io.ciera.runtime.time;

import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.task.GenericTask;
import io.ciera.runtime.application.task.Task;

public class GenericTimer extends AbstractTimer implements Timer {

  private static final long serialVersionUID = 1L;

  private final transient Runnable command;

  public GenericTimer(final ExecutionContext context, final Runnable command) {
    this(UniqueId.random(), context, command, Duration.ZERO);
  }

  public GenericTimer(
      final ExecutionContext context, final Runnable command, final Duration period) {
    this(UniqueId.random(), context, command, period);
  }

  public GenericTimer(
      final UniqueId timerHandle,
      final ExecutionContext context,
      final Runnable command,
      final Duration period) {
    super(timerHandle, context, period);
    this.command = command;
  }

  @Override
  protected Task getAction() {
    if (command != null) {
      return new GenericTask(command);
    } else {
      return null;
    }
  }

  @Override
  public Domain getDomain() {
    return null;
  }
}
