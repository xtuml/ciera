package io.ciera.runtime.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.EventTarget;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.application.MessageTarget;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.domain.Port;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.exceptions.InstancePopulationException;
import io.ciera.runtime.api.exceptions.MessageTargetException;
import io.ciera.runtime.api.types.UniqueId;

/**
 * A domain is a composite of translated model elements including classes, relationships, types,
 * functions, etc. The component provides access to out-bound (required) interface messages and the
 * instance population for every action within it.
 */
public abstract class AbstractDomain implements Domain {

  private final Supplier<Set<ObjectInstance>> objectPopulationSupplier;
  private final String name;
  private final Map<Class<?>, Set<ObjectInstance>> instancePopulation;

  public AbstractDomain(final String name) {
    this(name, ObjectPopulation::new);
  }

  public AbstractDomain(
      final String name, final Supplier<Set<ObjectInstance>> objectPopulationSupplier) {
    this.name = name;
    instancePopulation = new HashMap<>();
    this.objectPopulationSupplier = objectPopulationSupplier;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void initialize() {
    getContext().getApplication().getLogger().trace("%s initialized", this);
  }

  @Override
  public ExecutionContext getContext() {
    return Application.getInstance().defaultContext();
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

  public void addInstance(final ObjectInstance instance) {
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
  public <T extends ObjectInstance> T getInstance(
      final Class<T> object, final UniqueId instanceId) {
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
  public void deleteInstance(final ObjectInstance instance) {
    final Class<?> object = instance.getClass();
    final Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
    if (!(objectPopulation != null ? objectPopulation.remove(instance) : false)) {
      throw new InstancePopulationException(
          "Instance does not exist within the population", this, instance);
    }
  }

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

  @Override
  public EventTarget getEventTarget(final UniqueId targetId) {
    if (targetId == null) {
      return this;
    } else {
      return instancePopulation.values().stream()
          .flatMap(Set::stream)
          .filter(inst -> inst.getInstanceId().equals(targetId))
          .findAny()
          .orElse(null);
    }
  }

  @Override
  public MessageTarget getMessageTarget(final Class<? extends MessageTarget> targetClass) {
    throw new MessageTargetException(
        "Could not find target to deliver message: " + targetClass, null, null);
  }

  @Override
  public void consumeEvent(final Event event) {
    throw new EventTargetException("Could not find state machine to handle event", this, event);
  }

  @Override
  public Port getPort(final String portName) {
    throw new IllegalArgumentException(
        "Port with the name '" + portName + "' does not exist in domain: " + this);
  }

  @Override
  public String toString() {
    return String.format("Domain[%s]", name);
  }

  private static final class ObjectPopulation extends LinkedHashSet<ObjectInstance>
      implements Set<ObjectInstance> {

    private static final long serialVersionUID = 1L;

    private final Set<Object> primaryIdentifierSet;

    private ObjectPopulation() {
      primaryIdentifierSet = new HashSet<>();
    }

    @Override
    public boolean add(final ObjectInstance e) {
      final Object identifier = e.getIdentifier();
      return (InstanceIdentifier.EMPTY_IDENTIFIER.equals(identifier)
              || primaryIdentifierSet.add(identifier))
          && super.add(e);
    }

    @Override
    public boolean remove(final Object o) {
      if (o instanceof ObjectInstance) {
        primaryIdentifierSet.remove(((ObjectInstance) o).getIdentifier());
      }
      return super.remove(o);
    }

    @Override
    public void clear() {
      primaryIdentifierSet.clear();
      super.clear();
    }
  }
}
