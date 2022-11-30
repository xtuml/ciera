package io.ciera.runtime;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import io.ciera.runtime.api.Architecture;
import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.SystemClock;
import io.ciera.runtime.api.TaskSupplier;

public class SimpleScheduler implements Iterator<Runnable> {

  private final Architecture arch = Architecture.getInstance();
  private final SystemClock clock = arch.getClock();
  private final Domain[] domains;

  public SimpleScheduler(final Domain... domains) {
    this.domains = domains;
  }

  @Override
  public boolean hasNext() {
    return !arch.isShutdown();
  }

  @Override
  public Runnable next() {
    if (!arch.isShutdown()) {
      Runnable task = getTask();
      while (task == null) {
        final long waitTime =
            Stream.of(domains)
                .flatMap(Domain::getAllInstances)
                .filter(TaskSupplier.class::isInstance)
                .map(TaskSupplier.class::cast)
                .map(TaskSupplier::millisToNextTask)
                .min(Comparator.naturalOrder())
                .orElse(Long.MAX_VALUE);
        if (waitTime > 0) {
          try {
            clock.sleep(waitTime);
          } catch (InterruptedException e) {
          }
        }
        task = getTask();
      }
      return handleTask(task);
    } else {
      throw new NoSuchElementException();
    }
  }

  private Runnable getTask() {
    return Stream.of(domains)
        .flatMap(Domain::getAllInstances)
        .filter(TaskSupplier.class::isInstance)
        .map(TaskSupplier.class::cast)
        .map(TaskSupplier::getTask)
        .filter(o -> o != null)
        .findAny()
        .orElse(null);
  }

  private Runnable handleTask(final Runnable task) {
    return () -> {
      try {
        task.run();
      } catch (RuntimeException e) {
        // TODO error handling
        e.printStackTrace();
        System.exit(1);
      }
    };
  }
}
