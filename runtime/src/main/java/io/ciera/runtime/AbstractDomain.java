package io.ciera.runtime;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import io.ciera.runtime.api.Domain;
import io.ciera.runtime.api.Event;
import io.ciera.runtime.api.EventTarget;
import io.ciera.runtime.api.ObjectInstance;
import io.ciera.runtime.api.SystemClock;
import io.ciera.runtime.api.Timer;
import io.ciera.runtime.api.exceptions.InstancePopulationException;

/**
 * A domain is a composite of translated model elements including classes, relationships, types,
 * functions, etc. The component provides access to out-bound (required) interface messages and the
 * instance population for every action within it.
 */
public abstract class AbstractDomain implements Domain {

  private final Supplier<Set<ObjectInstance>> objectPopulationSupplier;
  private final String name;
  private final Map<Class<?>, Set<ObjectInstance>> instancePopulation = new HashMap<>();
  private final Queue<Timer> timers = new PriorityQueue<>();

  private final SystemClock clock;

  public AbstractDomain(final String name, final SystemClock clock) {
    this(name, clock, ObjectInstanceSet::new);
  }

  public AbstractDomain(
      final String name,
      final SystemClock clock,
      final Supplier<Set<ObjectInstance>> objectPopulationSupplier) {
    this.name = name;
    this.clock = clock;
    this.objectPopulationSupplier = objectPopulationSupplier;
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
  public <T extends ObjectInstance> T createInstance(final Supplier<T> constructor) {
    return createInstance(constructor, null);
  }

  @Override
  public <T extends ObjectInstance> T createInstance(
      final Supplier<T> constructor, final Consumer<T> instanceInitializer) {
    final T instance = constructor.get();
    if (instanceInitializer != null) {
      instanceInitializer.accept(instance);
    }
    addInstance(instance);
    return instance;
  }

  void addInstance(final ObjectInstance instance) {
    final Class<?> object = instance.getClass();
    Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
    if (objectPopulation == null) {
      objectPopulation = objectPopulationSupplier.get();
      instancePopulation.put(object, objectPopulation);
    }
    final boolean success = objectPopulation.add(instance);
    if (!success) {
      throw new InstancePopulationException(
          "Instance is not unique within the population", this, instance);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends ObjectInstance> T getInstance(final Class<T> object, final Predicate<T> where) {
    final Set<T> objectPopulation = (Set<T>) instancePopulation.get(object);
    return objectPopulation != null
        ? objectPopulation.stream().filter(where).findAny().orElse(null)
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

  /*
   * TODO
  @Override
  public <T extends ObjectInstance> int getUniqueInteger(
      final Class<T> object, final Function<T, Integer> keyMapper) {
    final Set<Integer> existingKeys =
        getAllInstances(object).map(keyMapper).collect(Collectors.toSet());
    return IntStream.iterate(1, x -> x + 1)
        .filter(i -> !existingKeys.contains(i))
        .findAny()
        .getAsInt();
  }
  */

  @Override
  public <E extends Event> void generate(
      final Function<Object[], E> eventBuilder, final EventTarget target, final Object... data) {
    target.consumeEvent(eventBuilder.apply(data));
  }

  @Override
  public Timer schedule(final Runnable action, final Duration delay) {
    final Timer t = new EventTimer(clock, action).schedule(delay);
    timers.add(t);
    return t;
  }

  @Override
  public Timer schedule(final Runnable action, final Duration delay, final Duration period) {
    final Timer t = new EventTimer(clock, action, period).schedule(delay);
    timers.add(t);
    return t;
  }

  @Override
  public Timer schedule(final Runnable action, final Instant expiration) {
    final Timer t = new EventTimer(clock, action).schedule(expiration);
    timers.add(t);
    return t;
  }

  @Override
  public Timer schedule(final Runnable action, final Instant expiration, final Duration period) {
    final Timer t = new EventTimer(clock, action, period).schedule(expiration);
    timers.add(t);
    return t;
  }

  @Override
  public Domain getDomain() {
    return this;
  }

  @Override
  public Domain getDomain(final String domainName) {
    return null;
  }

  @Override
  public String toString() {
    return String.format("Domain[%s]", name);
  }
}
