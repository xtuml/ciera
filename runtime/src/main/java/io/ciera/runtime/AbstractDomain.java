package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;
import io.ciera.runtime.api.ObjectInstance;
import io.ciera.runtime.api.Timer;
import io.ciera.runtime.api.exceptions.InstancePopulationException;

/**
 * A domain is a composite of translated model elements including classes, relationships, types,
 * functions, etc. The component provides access to out-bound (required) interface messages and the
 * instance population for every action within it.
 */
public abstract class AbstractDomain implements Domain {

  // TODO dependencies
  private final String name;

  private static final Supplier<Set<ObjectInstance>> objectPopulationSupplier =
      ObjectInstanceSet::new;

  private final Map<Class<?>, Set<ObjectInstance>> instancePopulation = new HashMap<>();

  public AbstractDomain(final String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void initialize() {
    // getContext().getApplication().getLogger().trace("%s initialized", this);
  }

  @Override
  public <T extends ObjectInstance> T createInstance(
      Supplier<T> constructor, BiConsumer<T, Domain> instanceInitializer) {
    final T instance = constructor.get();
    instanceInitializer.accept(instance, this);
    addInstance(instance);
    return instance;
  }

  @Override
  public void addInstance(final ObjectInstance instance) {
    final Class<?> object = instance.getClass();
    Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
    if (objectPopulation == null) {
      objectPopulation = objectPopulationSupplier.get();
      instancePopulation.put(object, objectPopulation);
    }
    final boolean success = objectPopulation.add(instance);
    if (success && instance instanceof AbstractObjectInstance) {
      ((AbstractObjectInstance) instance).setDomain(this);
    } else if (!success) {
      throw new InstancePopulationException(
          "Instance is not unique within the population", this, instance);
    }
  }

  @Override
  public <T extends ObjectInstance> T getInstance(final Class<T> object, final Predicate<T> where) {
    final Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
    return objectPopulation != null
        ? objectPopulation.stream()
            .filter(object::isInstance)
            .map(object::cast)
            .filter(where)
            .findAny()
            .orElse(null)
        : null;
  }

  @Override
  public <T extends ObjectInstance> T getInstance(final Class<T> object, final UUID instanceId) {
    return getInstance(object, o -> o.getInstanceId().equals(instanceId));
  }

  @Override
  public <T extends ObjectInstance> Stream<T> getAllInstances(final Class<T> object) {
    return Optional.ofNullable(instancePopulation.get(object)).orElse(Set.of()).stream()
        .map(object::cast);
  }

  @Override
  public Stream<ObjectInstance> getAllInstances() {
    return instancePopulation.values().stream().flatMap(Set::stream);
  }

  @Override
  public void removeInstance(final ObjectInstance instance) {
    final Class<?> object = instance.getClass();
    final Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
    if (objectPopulation != null) {
      objectPopulation.remove(instance);
    }
  }

  @Override
  public <E extends Event> void generate(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    target.queueEvent(eventBuilder.apply(data));
  }

  @Override
  public <E extends Event> void generateAccelerated(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    target.queueAcceleratedEvent(eventBuilder.apply(data));
  }

  @Override
  public <E extends Event> Timer schedule(
      Function<Object[], E> eventBuilder, EventTarget target, Duration delay, Object... data) {
    return new DelayedEvent(eventBuilder.apply(data), target).schedule(delay);
  }

  @Override
  public <E extends Event> Timer schedule(
      Function<Object[], E> eventBuilder, EventTarget target, Instant expiration, Object... data) {
    return new DelayedEvent(eventBuilder.apply(data), target).schedule(expiration);
  }

  @Override
  public <E extends Event> Timer scheduleRecurring(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Duration delay,
      Duration period,
      Object... data) {
    return new DelayedEvent(eventBuilder.apply(data), target, period).schedule(delay);
  }

  @Override
  public <E extends Event> Timer scheduleRecurring(
      Function<Object[], E> eventBuilder,
      EventTarget target,
      Instant expiration,
      Duration period,
      Object... data) {
    return new DelayedEvent(eventBuilder.apply(data), target, period).schedule(expiration);
  }

  @Override
  public Domain getDomain() {
    return this;
  }

  @Override
  public Domain getDomain(final String domainName) {
    // TODO
    return null;
  }

  @Override
  public String toString() {
    return String.format("Domain[%s]", name);
  }
}
