package io.ciera.runtime.summit2.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

import io.ciera.runtime.summit2.action.ActionHome;
import io.ciera.runtime.summit2.application.ExecutionContext;
import io.ciera.runtime.summit2.application.Logger;
import io.ciera.runtime.summit2.exceptions.InstancePopulationException;
import io.ciera.runtime.summit2.exceptions.ModelIntegrityException;
import io.ciera.runtime.summit2.types.UniqueId;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * out-bound (required) interface messages and the instance population for every
 * action within it.
 */
public abstract class Domain implements ActionHome, InstancePopulation {

    private String name;
    private ExecutionContext context;
    private Logger logger;

    private Map<Class<?>, Set<ObjectInstance>> instancePopulation;

    public Domain(String name, ExecutionContext context, Logger logger) {
        this.name = name;
        this.instancePopulation = new TreeMap<>();
    }

    @Override
    public String getName() {
        return name;
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
        return context;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String toString() {
        return String.format("DOMAIN[%s]", name);
    }

    @Override
    public <T extends ObjectInstance> void createInstance(Class<T> type) {
        try {
            Constructor<T> constructor = type.getConstructor();
            T instance = constructor.newInstance();
            addInstance(instance);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            throw new InstancePopulationException("Could not create instance of type '" + type.getSimpleName() + "'",
                    e);
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
            throw new ModelIntegrityException(
                    "Instance is not unique within the population of '" + getName() + "': " + instance);
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
        return (Set<T>) Collections.unmodifiableSet(instancePopulation.get(object));
    }

    @Override
    public void deleteInstance(ObjectInstance instance) {
        Class<?> object = instance.getClass();
        Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
        boolean success = objectPopulation != null ? objectPopulation.remove(instance) : false;
        if (!success) {
            throw new ModelIntegrityException(
                    "Instance does not exist within the population of '" + getName() + "': " + instance);
        }
    }

}
