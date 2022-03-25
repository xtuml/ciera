package io.ciera.runtime.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.domain.PersistentDomain;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.application.ThreadExecutionContext;
import io.ciera.runtime.application.task.DomainTask;
import io.ciera.runtime.time.EventTimer;

public abstract class AbstractPersistentDomain extends AbstractDomain implements PersistentDomain {

    public AbstractPersistentDomain(String name) {
        super(name);
    }

    public AbstractPersistentDomain(String name, Supplier<Set<ObjectInstance>> objectPopulationSupplier) {
        super(name, objectPopulationSupplier);
    }

    @Override
    public void persist(ObjectOutputStream out) throws IOException {
        // get active timers
        Timer[] timers = getContext().getClock().getScheduledTimers(getContext()).filter(EventTimer.class::isInstance)
                .filter(t -> this.equals(t.getDomain())).toArray(Timer[]::new);

        // get task queue
        DomainTask[] tasks = Stream.of(getContext()).filter(ThreadExecutionContext.class::isInstance)
                .map(ThreadExecutionContext.class::cast).flatMap(ThreadExecutionContext::getTasks)
                .filter(DomainTask.class::isInstance).map(DomainTask.class::cast)
                .filter(t -> this.equals(t.getDomain())).toArray(DomainTask[]::new);

        // get object populations
        ObjectInstance[] instances = getAllInstances().sorted(Comparator.comparing(inst -> inst.getClass().getName()))
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
    public void load(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // read all objects
        Timer[] timers = (Timer[]) in.readObject();
        DomainTask[] tasks = (DomainTask[]) in.readObject();
        ObjectInstance[] instances = (ObjectInstance[]) in.readObject();

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
