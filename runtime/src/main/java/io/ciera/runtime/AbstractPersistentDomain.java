package io.ciera.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;
import java.util.function.Supplier;

import io.ciera.runtime.api.ObjectInstance;
import io.ciera.runtime.api.PersistentDomain;
import io.ciera.runtime.api.SystemClock;

public abstract class AbstractPersistentDomain extends AbstractDomain implements PersistentDomain {

  public AbstractPersistentDomain(final String name, final SystemClock clock) {
    super(name, clock);
  }

  public AbstractPersistentDomain(
      final String name,
      final SystemClock clock,
      final Supplier<Set<ObjectInstance>> objectPopulationSupplier) {
    super(name, clock, objectPopulationSupplier);
  }

  @Override
  public void persist(final ObjectOutputStream out) throws IOException {
    /*
    // get active timers
    final EventTimer[] timers =
        getContext()
            .getClock()
            .getScheduledTimers(getContext())
            .filter(EventTimer.class::isInstance)
            .filter(t -> equals(t.getDomain()))
            .toArray(EventTimer[]::new);

    // get task queue
    final DomainTask[] tasks =
        Stream.of(getContext())
            .filter(ThreadExecutionContext.class::isInstance)
            .map(ThreadExecutionContext.class::cast)
            .flatMap(ThreadExecutionContext::getTasks)
            .filter(DomainTask.class::isInstance)
            .map(DomainTask.class::cast)
            .filter(t -> equals(t.getDomain()))
            .toArray(DomainTask[]::new);

    // get object populations
    final ObjectInstance[] instances =
        getAllInstances()
            .sorted(Comparator.comparing(inst -> inst.getClass().getName()))
            .toArray(ObjectInstance[]::new);

    // persist all objects
    out.writeObject(timers);
    out.writeObject(tasks);
    out.writeObject(instances);

    /*
    getApplication().getLogger().trace("%d pending timers persisted for: %s", timers.length, this);
    getApplication().getLogger().trace("%d waiting tasks persisted for: %s", tasks.length, this);
    getApplication().getLogger().trace("%d object instances persisted for: %s", instances.length, this);
     */
  }

  @Override
  public void load(final ObjectInputStream in) throws IOException, ClassNotFoundException {
    /*
    // read all objects
    final EventTimer[] timers = (EventTimer[]) in.readObject();
    final DomainTask[] tasks = (DomainTask[]) in.readObject();
    final ObjectInstance[] instances = (ObjectInstance[]) in.readObject();

    // register timers
    Stream.of(timers).forEach(getContext().getClock()::registerTimer);

    // load tasks
    if (getContext() instanceof Executor) {
      Stream.of(tasks).map(Runnable.class::cast).forEach(getContext()::execute);
    }

    // load instance population
    Stream.of(instances).forEach(this::addInstance);

    /*
    getApplication().getLogger().trace("%d pending timers loaded for: %s", timers.length, this);
    getApplication().getLogger().trace("%d waiting tasks loaded for: %s", tasks.length, this);
    getApplication().getLogger().trace("%d object instances loaded for: %s", instances.length, this);
     */
  }
}
