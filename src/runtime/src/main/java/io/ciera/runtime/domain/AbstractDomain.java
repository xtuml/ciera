package io.ciera.runtime.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.exceptions.InstancePopulationException;
import io.ciera.runtime.api.types.UniqueId;

/**
 * A domain is a composite of translated model elements including classes,
 * relationships, types, functions, etc. The component provides access to
 * out-bound (required) interface messages and the instance population for every
 * action within it.
 */
public abstract class AbstractDomain implements Domain {

	private final String name;
	private final Application application;
	private ExecutionContext context;

	private final Map<Class<?>, Set<ObjectInstance>> instancePopulation;

	public AbstractDomain(String name, Application application) {
		this.name = name;
		this.application = application;
		this.context = null;
		this.instancePopulation = new HashMap<>();
	}

	@Override
	public Domain getDomain() {
		return this;
	}

	@Override
	public ExecutionContext getContext() {
		return context != null ? context : getApplication().defaultContext();
	}

	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public void attachTo(ExecutionContext context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return String.format("Domain[%s]", name);
	}

	@Override
	public <T extends ObjectInstance> T createInstance(Class<T> type) {
		return createInstance(type, null);
	}

	@Override
	public <T extends ObjectInstance> T createInstance(Class<T> type, Consumer<T> instanceInitializer) {
		try {
			Constructor<T> constructor = type.getConstructor(getClass());
			T instance = constructor.newInstance(this);
			if (instanceInitializer != null) {
				instanceInitializer.accept(instance);
			}
			addInstance(instance);
			return instance;
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new InstancePopulationException("Could not create instance of type '" + type.getSimpleName() + "'", e,
					this);
		}
	}

	@Override
	public void addInstance(ObjectInstance instance) {
		Class<?> object = instance.getClass();
		Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
		if (objectPopulation == null) {
			objectPopulation = new LinkedHashSet<>();
			instancePopulation.put(object, objectPopulation);
		}
		boolean success = objectPopulation.add(instance);
		if (!success) {
			throw new InstancePopulationException("Instance is not unique within the population", this, instance);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ObjectInstance> T getInstance(Class<T> object, Predicate<T> where) {
		Set<T> objectPopulation = (Set<T>) instancePopulation.get(object);
		return objectPopulation != null ? objectPopulation.stream().filter(where).findAny().orElse(null) : null;
	}

	@Override
	public <T extends ObjectInstance> T getInstance(Class<T> object, UniqueId instanceId) {
		return getInstance(object, o -> o.getInstanceId().equals(instanceId));
	}

	@Override
	public <T extends ObjectInstance> T getInstance(Class<T> object) {
		return getInstance(object, o -> true);
	}

	@Override
	public <T extends ObjectInstance> Stream<T> getAllInstances(Class<T> object) {
		return Optional.ofNullable(instancePopulation.get(object)).orElse(Set.of()).stream().map(object::cast);
	}

	@Override
	public void deleteInstance(ObjectInstance instance) {
		Class<?> object = instance.getClass();
		Set<ObjectInstance> objectPopulation = instancePopulation.get(object);
		if (!(objectPopulation != null ? objectPopulation.remove(instance) : false)) {
			throw new InstancePopulationException("Instance does not exist within the population", this, instance);
		}
	}

	@Override
	public <T extends ObjectInstance> int getUniqueInteger(Class<T> object, Function<T, Integer> keyMapper) {
		final Set<Integer> existingKeys = getAllInstances(object).map(keyMapper).collect(Collectors.toSet());
		return IntStream.iterate(1, x -> x + 1).filter(i -> !existingKeys.contains(i)).findAny().getAsInt();
	}
}
