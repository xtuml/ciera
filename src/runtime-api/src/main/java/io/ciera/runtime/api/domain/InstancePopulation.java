package io.ciera.runtime.api.domain;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import io.ciera.runtime.api.types.UniqueId;

public interface InstancePopulation {

    public <T extends ObjectInstance> T createInstance(Class<T> object);

    public <T extends ObjectInstance> T createInstance(Class<T> object, Consumer<T> instanceInitializer);

    public void addInstance(ObjectInstance instance);

    public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

    public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

    public <T extends ObjectInstance> T getInstance(Class<T> object);

    public <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object);

    public Stream<ObjectInstance> getAllInstances();

    public void deleteInstance(ObjectInstance instance);

    public <T extends ObjectInstance> int getUniqueInteger(Class<T> object, Function<T, Integer> keyMapper);

}