package io.ciera.runtime.domain;

import java.util.Set;
import java.util.function.Predicate;

import io.ciera.runtime.application.Named;
import io.ciera.runtime.types.UniqueId;

public interface InstancePopulation extends Named {

    public <T extends ObjectInstance> T createInstance(Class<T> object);

    public void addInstance(ObjectInstance instance);

    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

    public <T extends ObjectInstance> T getInstance(Class<T> object);

    public <T extends ObjectInstance> Set<T> getAllInstances(Class<T> object);

    public void deleteInstance(ObjectInstance instance);

}