package io.ciera.runtime.domain;

import java.util.function.Predicate;
import java.util.stream.Stream;

import io.ciera.runtime.types.UniqueId;

public interface InstancePopulation {

    public <T extends ObjectInstance> T createInstance(Class<T> object);

    public void addInstance(ObjectInstance instance);

    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

    public <T extends ObjectInstance> T getInstance(Class<T> object);

    public <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object);

    public void deleteInstance(ObjectInstance instance);

}