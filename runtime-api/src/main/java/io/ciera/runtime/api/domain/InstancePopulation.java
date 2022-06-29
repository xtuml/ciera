package io.ciera.runtime.api.domain;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.ciera.runtime.api.types.UniqueId;

public interface InstancePopulation {

  <T extends ObjectInstance> T createInstance(Supplier<T> constructor);

  <T extends ObjectInstance> T createInstance(
      Supplier<T> constructor, Consumer<T> instanceInitializer);

  void addInstance(ObjectInstance instance);

  <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where);

  <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId);

  <T extends ObjectInstance> T getInstance(Class<T> object);

  <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object);

  Stream<ObjectInstance> getAllInstances();

  void deleteInstance(ObjectInstance instance);

  <T extends ObjectInstance> int getUniqueInteger(Class<T> object, Function<T, Integer> keyMapper);
}
