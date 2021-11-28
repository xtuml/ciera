package io.ciera.runtime.domain;

import io.ciera.runtime.action.InstanceActionHome;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.exceptions.DeserializationException;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.ModelType;
import io.ciera.runtime.types.UniqueId;

public abstract class ObjectInstance extends ModelType
        implements InstanceActionHome, EventTarget, Comparable<ObjectInstance> {

    private final UniqueId instanceId;
    private final Domain domain;
    private final ExecutionContext context;
    private final Logger logger;
    private boolean alive;

    public ObjectInstance() {
        this.instanceId = null;
        this.domain = null;
        this.context = null;
        this.logger = null;
    }

    public ObjectInstance(Domain domain) {
        this(domain, null);
    }

    public ObjectInstance(Domain domain, ExecutionContext context) {
        this(UniqueId.random(), domain, context);
    }

    public ObjectInstance(UniqueId instanceId, Domain domain, ExecutionContext context) {
        this.instanceId = instanceId;
        this.domain = domain;
        this.context = context;
        this.logger = domain.getLogger();
        this.alive = true;
    }

    public UniqueId getInstanceId() {
        return instanceId;
    }

    public String getName() {
        return getClass().getSimpleName() + "[" + instanceId.toString().substring(0, 8) + "]";
    }

    public void delete() {
        alive = false;
        domain.deleteInstance(this);
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean isAlive() {
        return alive;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return context != null ? context : getDomain().getContext();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public ObjectInstance self() {
        return this;
    }

    @Override
    public String toString() {
        return getName();
    }

    public static ObjectInstance fromString() {
        throw new DeserializationException("Object instances cannot be deserialized.");
    }

    @Override
    public int compareTo(ObjectInstance o) {
        return getName().compareTo(o.getName());
    }

    @Override
    public UniqueId getTargetHandle() {
        return instanceId;
    }

    @Override
    public void consumeEvent(Event event) {
        throw new InstancePopulationException(
                "Cannot generate event '" + event.getName() + "' to non-dynamic instance '" + getName() + "'");
    }

}
