package io.ciera.runtime.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.domain.PersistentDomain;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.exceptions.InstancePopulationException;
import io.ciera.runtime.api.time.Timer;
import io.ciera.runtime.api.types.UniqueId;
import io.ciera.runtime.application.BaseApplication;
import io.ciera.runtime.application.ThreadExecutionContext;
import io.ciera.runtime.application.task.DomainTask;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * out-bound (required) interface messages and the instance population for every
 * action within it.
 */
public abstract class AbstractDomain implements PersistentDomain {

    private final String name;
    private final Map<Class<?>, Set<ObjectInstance>> instancePopulation;

    public AbstractDomain(String name) {
        this.name = name;
        this.instancePopulation = new HashMap<>();
    }

    @Override
    public ExecutionContext getContext() {
        return BaseApplication.getInstance().defaultContext();
    }

    @Override
    public <T extends ObjectInstance> T createInstance(Class<T> type) {
        return createInstance(type, null);
    }

    @Override
    public <T extends ObjectInstance> T createInstance(Class<T> type, Consumer<T> instanceInitializer) {
        try {
            Constructor<T> constructor = type.getConstructor(getClass());
            T instance = constructor.newInstance(this);
            if (instanceInitializer != null) {
                instanceInitializer.accept(instance);
            }
            addInstance(instance);
            return instance;
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InstancePopulationException("Could not create instance of type '" + type.getSimpleName() + "'", e,
                    this);
        }
    }

    @Override
    public void addInstance(ObjectInstance instance) {
        Class<?> object = instance.getClass();
        Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
        if (objectPopulation == null) {
            objectPopulation = new LinkedHashSet<>();
            instancePopulation.put(object, objectPopulation);
        }
        boolean success = objectPopulation.add(instance);
        if (!success) {
            throw new InstancePopulationException("Instance is not unique within the population", this, instance);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where) {
        Set<T> objectPopulation = (Set<T>) instancePopulation.get(object);
        return objectPopulation != null ? objectPopulation.stream().filter(where).findAny().orElse(null) : null;
    }

    @Override
    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId) {
        return getInstance(object, o -> o.getInstanceId().equals(instanceId));
    }

    @Override
    public <T extends ObjectInstance> T getInstance(Class<T> object) {
        return getInstance(object, o -> true);
    }

    @Override
    public <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object) {
        return Optional.ofNullable(instancePopulation.get(object)).orElse(Set.of()).stream().map(object::cast);
    }

    @Override
    public Stream<ObjectInstance> getAllInstances() {
        return instancePopulation.values().stream().flatMap(Set::stream);
    }

    @Override
    public void deleteInstance(ObjectInstance instance) {
        Class<?> object = instance.getClass();
        Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
        if (!(objectPopulation != null ? objectPopulation.remove(instance) : false)) {
            throw new InstancePopulationException("Instance does not exist within the population", this, instance);
        }
    }

    @Override
    public <T extends ObjectInstance> int getUniqueInteger(Class<T> object, Function<T, Integer> keyMapper) {
        final Set<Integer> existingKeys = getAllInstances(object).map(keyMapper).collect(Collectors.toSet());
        return IntStream.iterate(1, x -> x + 1).filter(i -> !existingKeys.contains(i)).findAny().getAsInt();
    }

    @Override
    public EventTarget getEventTarget(UniqueId targetId) {
        if (targetId == null) {
            return this;
        } else {
            return instancePopulation.values().stream().flatMap(Set::stream)
                    .filter(inst -> inst.getInstanceId().equals(targetId)).findAny().orElse(null);
        }
    }

    @Override
    public MessageTarget getMessageTarget(UniqueId targetId) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public void consumeEvent(Event event) {
        Class<?> cls = event.getClass().getDeclaringClass();
        if (cls != null) {
            try {
                EventTarget target = (EventTarget) cls.getMethod("getInstance", getClass()).invoke(null, this);
                target.consumeEvent(event);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                throw new EventTargetException("Failed to initialize state machine to handle event", e, this, event);
            }
        } else {
            throw new EventTargetException("Could not find state machine to handle event", this, event);
        }
    }

    @Override
    public void persist(ObjectOutputStream out) throws IOException {
        // get active timers
        Timer[] timers = getClock().getScheduledTimers(getContext()).filter(t -> this.equals(t.getDomain()))
                .toArray(Timer[]::new);

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

        getApplication().getLogger().trace("%d pending timers persisted for: %s", timers.length, this);
        for (Object o : timers) {
            getApplication().getLogger().trace("  %s", o);
        }
        getApplication().getLogger().trace("%d waiting tasks persisted for: %s", tasks.length, this);
        for (Object o : tasks) {
            getApplication().getLogger().trace("  %s", o);
        }
        getApplication().getLogger().trace("%d object instances persisted for: %s", instances.length, this);
        for (Object o : instances) {
            getApplication().getLogger().trace("  %s", o);
        }
    }

    @Override
    public void load(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // read all objects
        Timer[] timers = (Timer[]) in.readObject();
        DomainTask[] tasks = (DomainTask[]) in.readObject();
        ObjectInstance[] instances = (ObjectInstance[]) in.readObject();

        // register timers
        Stream.of(timers).forEach(getClock()::registerTimer);

        // load tasks
        if (getContext() instanceof Executor) {
            Stream.of(tasks).map(Runnable.class::cast).forEach(getContext()::execute);
        }

        // load instance population
        Stream.of(instances).forEach(this::addInstance);

        getApplication().getLogger().trace("%d pending timers loaded for: %s", timers.length, this);
        for (Object o : timers) {
            getApplication().getLogger().trace("  %s", o);
        }
        getApplication().getLogger().trace("%d waiting tasks loaded for: %s", tasks.length, this);
        for (Object o : tasks) {
            getApplication().getLogger().trace("  %s", o);
        }
        getApplication().getLogger().trace("%d object instances loaded for: %s", instances.length, this);
        for (Object o : instances) {
            getApplication().getLogger().trace("  %s", o);
        }
    }

    @Override
    public String toString() {
        return String.format("Domain[%s]", name);
    }
}
