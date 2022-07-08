package io.ciera.runtime.api;

import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface InstancePopulation {

  <T extends ObjectInstance> T createInstance(Supplier<T> constructor);

  <T extends ObjectInstance> T createInstance(
      Supplier<T> constructor, Consumer<T> instanceInitializer);

  <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

  default <T extends ObjectInstance> T getInstance(final Class<T> object) {
    return getInstance(object, o -> true);
  }

  <T extends ObjectInstance> T getInstance(Class<T> object, UUID instanceId);

  <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object);

  Stream<ObjectInstance> getAllInstances();

  void removeInstance(ObjectInstance instance);
}
