package io.ciera.runtime.api;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * A domain is a composite of translated model elements including classes, relationships, types,
 * functions, etc. The component provides access to out-bound (required) interface messages and the
 * instance population for every action within it.
 */
public interface Domain extends InstancePopulation, ActionHome {

  String getName();

  // Application initialization functions
  void initialize();

  <T extends ObjectInstance> T createInstance(
      Supplier<T> constructor, BiConsumer<T, Domain> instanceInitializer);
}
