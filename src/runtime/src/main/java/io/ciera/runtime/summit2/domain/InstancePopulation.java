package io.ciera.runtime.summit2.domain;

import java.util.Set;
import java.util.function.Predicate;

import io.ciera.runtime.summit2.types.UniqueId;

public interface InstancePopulation {

    public String getName();

    public <T extends ObjectInstance> void createInstance(Class<T> object);

    public void addInstance(ObjectInstance instance);

    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

    public <T extends ObjectInstance> T getInstance(Class<T> object);

    public <T extends ObjectInstance> Set<T> getAllInstances(Class<T> object);

    public void deleteInstance(ObjectInstance instance);

}