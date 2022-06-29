package io.ciera.runtime.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.Logger;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.PersistentDomain;
import io.ciera.runtime.api.time.SystemClock;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.Duration;
import io.ciera.runtime.api.types.TimeStamp;
import io.ciera.runtime.application.task.GeneratedEvent;
import io.ciera.runtime.application.task.GeneratedEventToSelf;
import io.ciera.runtime.application.task.GenericTask;
import io.ciera.runtime.application.task.Task;
import io.ciera.runtime.time.AbstractTimer;
import io.ciera.runtime.time.EventTimer;
import io.ciera.runtime.time.GenericTimer;

public class ThreadExecutionContext implements ExecutionContext, Runnable {

  private final String name;
  private final ExecutionMode executionMode;
  private final ModelIntegrityMode modelIntegrityMode;
  private final Queue<Task> tasks;
  private Task currentTask;

  public ThreadExecutionContext(final String name) {
    this(name, ExecutionMode.INTERLEAVED, ModelIntegrityMode.STRICT);
  }

  public ThreadExecutionContext(
      final String name,
      final ExecutionMode executionMode,
      final ModelIntegrityMode modelIntegrityMode) {
    this.name = name;
    this.executionMode = executionMode;
    this.modelIntegrityMode = modelIntegrityMode;
    tasks = new PriorityBlockingQueue<>();
    currentTask = null;
  }

  @Override
  public String getName() {
    return name;
  }

  public Stream<Runnable> getTasks() {
    return tasks.stream().map(Runnable.class::cast);
  }

  @Override
  public <E extends Event> void generateEvent(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    generateEvent(eventBuilder, target, false, data);
  }

  @Override
  public <E extends Event> void generateEventToSelf(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    generateEvent(eventBuilder, target, true, data);
  }

  private <E extends Event> void generateEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final boolean toSelf,
      final Object... data) {
    final Event event = eventBuilder.apply(data);
    if (toSelf) {
      target.getContext().execute(new GeneratedEventToSelf(event, target));
    } else {
      target.getContext().execute(new GeneratedEvent(event, target, getExecutionMode()));
    }
  }

  @Override
  public <E extends Event> Timer scheduleEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final Duration delay,
      final Object... eventData) {
    final Event event = eventBuilder.apply(eventData);
    final Timer timer = new EventTimer(this, event, target);
    timer.schedule(delay.getValue());
    return timer;
  }

  @Override
  public <E extends Event> Timer scheduleEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final TimeStamp expiration,
      final Object... eventData) {
    final Duration delay = expiration.minus(TimeStamp.now(getClock()));
    return scheduleEvent(eventBuilder, target, delay, eventData);
  }

  @Override
  public Timer scheduleAction(final Duration delay, final Runnable action) {
    final Timer timer = new GenericTimer(this, action);
    timer.schedule(delay.getValue());
    return timer;
  }

  @Override
  public Timer scheduleAction(final TimeStamp expiration, final Runnable action) {
    final Duration delay = expiration.minus(TimeStamp.now(getClock()));
    return scheduleAction(delay, action);
  }

  @Override
  public <E extends Event> Timer scheduleRecurringEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final Duration delay,
      final Duration period,
      final Object... eventData) {
    final Event event = eventBuilder.apply(eventData);
    final AbstractTimer timer =
        new EventTimer(this, event, target, period != null ? period : delay);
    timer.schedule(delay.getValue());
    return timer;
  }

  @Override
  public <E extends Event> Timer scheduleRecurringEvent(
      final Function<Object[], E> eventBuilder,
      final EventTarget target,
      final TimeStamp expiration,
      final Duration period,
      final Object... eventData) {
    final Duration delay = expiration.minus(TimeStamp.now(getClock()));
    return scheduleRecurringEvent(eventBuilder, target, delay, period, eventData);
  }

  @Override
  public Timer scheduleRecurringAction(
      final Duration delay, final Duration period, final Runnable action) {
    final Timer timer = new GenericTimer(this, action, period);
    timer.schedule(delay.getValue());
    return timer;
  }

  @Override
  public Timer scheduleRecurringAction(
      final TimeStamp expiration, final Duration period, final Runnable action) {
    final Duration delay = expiration.minus(TimeStamp.now(getClock()));
    return scheduleRecurringAction(delay, period, action);
  }

  @Override
  public void halt() {
    execute(() -> getApplication().stop());
  }

  @Override
  @Deprecated
  public void delay(final Duration delay) {
    getLogger().trace("DEL: delaying for %s", delay);
    try {
      Thread.sleep(delay.getValue() / 1000000L, (int) delay.getValue() % 1000000);
    } catch (final InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private synchronized void addTask(final Task newTask) {
    newTask.setParent(currentTask);
    if (tasks.offer(newTask)) {
      notify();
    } else {
      getLogger().error("Could not add task to queue");
    }
  }

  @Override
  public void execute(final Runnable command) {
    if (command != null) {
      final Task t = command instanceof Task ? (Task) command : new GenericTask(command);
      addTask(t);
    }
  }

  private void commitTransaction(final String storeName) {
    // persist the instance population
    persistPopulation(storeName);

    // check integrity if in strict mode
    if (modelIntegrityMode == ModelIntegrityMode.STRICT) {
      checkIntegrity();
    }
  }

  private void checkIntegrity() {
    // TODO
  }

  private void loadPopulation(final String storeName) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(storeName))) {
      getLogger().trace("Loading instance population...");
      for (final PersistentDomain domain :
          getApplication().getDomains().stream()
              .filter(d -> equals(d.getContext()))
              .filter(PersistentDomain.class::isInstance)
              .map(PersistentDomain.class::cast)
              .sorted(Comparator.comparing(Domain::getName))
              .collect(Collectors.toList())) {
        domain.load(in);
      }
    } catch (final FileNotFoundException e) {
      // ignore if the file doesn't exist
    } catch (IOException | ClassNotFoundException e) {
      getLogger().error("Failed to load instance population", e);
      getApplication().getExceptionHandler().handleError(new RuntimeException(e));
    }
  }

  private void persistPopulation(final String storeName) {
    // persist instance population
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storeName))) {
      getLogger().trace("Dumping instance population...");
      for (final PersistentDomain domain :
          getApplication().getDomains().stream()
              .filter(d -> equals(d.getContext()))
              .filter(PersistentDomain.class::isInstance)
              .map(PersistentDomain.class::cast)
              .sorted(Comparator.comparing(Domain::getName))
              .collect(Collectors.toList())) {
        domain.persist(out);
      }
    } catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  @Override
  public void run() {
    // load instance population
    final String storeName = System.getProperty("io.ciera.runtime.objectStore");
    if (storeName != null && System.getProperty("io.ciera.runtime.coldStart") == null) {
      loadPopulation(storeName);
    }

    // main loop
    while (getApplication().isRunning()) {

      // check to see if any expired timers need to be added to the task queue
      getClock().checkTimers(this);

      // get the next task (if there are any)
      currentTask = tasks.poll();

      if (currentTask != null) {
        try {
          // execute the task
          currentTask.run();

          // Commit the transaction
          // A transaction is committed after every task (excluding generic execution) in
          // interleaved mode.
          // In sequential mode, the transaction is complete when the queue is empty or
          // the next task is a primary task.
          if (executionMode == ExecutionMode.INTERLEAVED
                  && !(currentTask instanceof GenericTask)
                  && storeName != null
              || executionMode == ExecutionMode.SEQUENTIAL
                  && (tasks.isEmpty() || tasks.peek().getParent() == null)
                  && storeName != null) {
            commitTransaction(storeName);
          }

          // check integrity if the task queue is empty and in relaxed model integrity
          // mode
          if (modelIntegrityMode == ModelIntegrityMode.RELAXED && tasks.isEmpty()) {
            checkIntegrity();
          }
        } catch (final RuntimeException e) {
          getApplication().getExceptionHandler().handleError(e);
        } finally {
          currentTask = null;
        }
      } else {
        // wait for something interesting to happen
        try {
          if (getClock().hasScheduledTimers(this)) {
            // wait for the next timer to expire
            getClock().waitForNextTimer(this);
          } else {
            if (System.getProperty("io.ciera.runtime.haltWhenIdle") != null) {
              getApplication().stop();
            } else {
              // wait indefinitely for an external signal or for a
              // timer to be scheduled in this context by another thread
              synchronized (this) {
                wait();
              }
            }
          }
        } catch (final InterruptedException e) {
          // woken up by external signal or new timer
        }
      }
    }
  }

  public Thread start() {
    final Thread t = new Thread(this, name);
    t.start();
    return t;
  }

  @Override
  public ExecutionMode getExecutionMode() {
    return executionMode;
  }

  @Override
  public ModelIntegrityMode getModelIntegrityMode() {
    return modelIntegrityMode;
  }

  @Override
  public SystemClock getClock() {
    return getApplication().getClock();
  }

  private Logger getLogger() {
    return getApplication().getLogger();
  }

  @Override
  public Application getApplication() {
    return Application.getInstance();
  }

  @Override
  public String toString() {
    return name;
  }
}
