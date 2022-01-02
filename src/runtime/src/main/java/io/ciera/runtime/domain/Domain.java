package io.ciera.runtime.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.Application;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.Named;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.UniqueId;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * out-bound (required) interface messages and the instance population for every
 * action within it.
 */
public abstract class Domain implements ActionHome, InstancePopulation, Named {

    private final String name;
    private final Application application;
    private ExecutionContext context;

    private final Map<Class<?>, Set<ObjectInstance>> instancePopulation;

    public Domain(String name, Application application) {
        this.name = name;
        this.application = application;
        this.context = null;
        this.instancePopulation = new HashMap<>();
    }

    @Override
    public String getName() {
        return name;
    }

    public Application getApplication() {
        return application;
    }

    /**
     * Execute application level initialization functions.
     */
    public abstract void initialize();

    @Override
    public Domain getDomain() {
        return this;
    }

    @Override
    public ExecutionContext getContext() {
        return context != null ? context : getApplication().defaultContext();
    }

    // TODO should this be in an interface somewhere???
    public void attachTo(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public Logger getLogger() {
        return application.getLogger();
    }

    @Override
    public String toString() {
        return String.format("Domain[%s]", name);
    }

    @Override
    public <T extends ObjectInstance> T createInstance(Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor(getClass());
            T instance = constructor.newInstance(this);
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
            objectPopulation = new TreeSet<>();
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
    @SuppressWarnings("unchecked")
    public <T extends ObjectInstance> Set<T> getAllInstances(Class<T> object) {
        Set<T> objectPopulation = (Set<T>) instancePopulation.get(object);
        return Collections.unmodifiableSet(objectPopulation != null ? objectPopulation : new TreeSet<>());
    }

    @Override
    public void deleteInstance(ObjectInstance instance) {
        Class<?> object = instance.getClass();
        Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
        boolean success = objectPopulation != null ? objectPopulation.remove(instance) : false;
        if (!success) {
            throw new InstancePopulationException("Instance does not exist within the population", this, instance);
        }
    }

}
