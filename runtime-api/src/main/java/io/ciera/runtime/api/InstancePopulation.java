package io.ciera.runtime.api;

import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface InstancePopulation {

  void addInstance(final ObjectInstance instance);

  <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

  default <T extends ObjectInstance> T getInstance(final Class<T> object) {
    return getInstance(object, o -> true);
  }

  <T extends ObjectInstance> T getInstance(Class<T> object, UUID instanceId);

  <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object);

  Stream<ObjectInstance> getAllInstances();

  void removeInstance(ObjectInstance instance);
}
