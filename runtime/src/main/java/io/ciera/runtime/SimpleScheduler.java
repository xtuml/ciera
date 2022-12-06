package io.ciera.runtime;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.ciera.runtime.api.Architecture;
import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.SystemClock;
import io.ciera.runtime.api.TaskSupplier;

public class SimpleScheduler implements Iterator<Runnable>, Executor {

  private final Architecture arch = Architecture.getInstance();
  private final SystemClock clock = arch.getClock();
  private final BlockingQueue<Runnable> externalTasks = new LinkedBlockingQueue<>();
  private final Domain[] domains;

  private final Logger logger = LoggerFactory.getLogger(getClass());
  private long t0 = -1l;

  public SimpleScheduler(final Domain... domains) {
    this.domains = domains;
  }

  @Override
  public synchronized void execute(final Runnable task) {
    externalTasks.offer(task);
    this.notifyAll();
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
                .filter(m -> m > 0l)
                .min(Comparator.naturalOrder())
                .orElse(0l);
        try {
          if (t0 > 0) {
            logger.debug("Transaction duration: {}ms", System.currentTimeMillis() - t0);
          }
          // wait for next timer or external signal
          logger.debug("Waiting for external signals ({}ms)", waitTime);
          clock.waitOn(this, waitTime);
          t0 = System.currentTimeMillis();
        } catch (InterruptedException e) {
          // do nothing
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
        .orElseGet(externalTasks::poll);
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
