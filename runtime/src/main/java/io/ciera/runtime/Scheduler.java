package io.ciera.runtime;

import java.util.Comparator;
import java.util.stream.Stream;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.TaskSupplier;

public class Scheduler {

  private final Domain[] domains;

  public Scheduler(final Domain... domains) {
    this.domains = domains;
  }

  public Runnable nextTask() throws InterruptedException {
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
      Thread.sleep(waitTime);
      task = getTask();
    }
    return wrapTask(task);
  }

  private Runnable wrapTask(final Runnable task) {
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
}
