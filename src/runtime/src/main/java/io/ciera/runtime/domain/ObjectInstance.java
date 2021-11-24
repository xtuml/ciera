package io.ciera.runtime.domain;

import io.ciera.runtime.action.ActionHome;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.exceptions.DeserializationException;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.ModelType;
import io.ciera.runtime.types.UniqueId;

public abstract class ObjectInstance extends ModelType implements ActionHome, EventTarget, Comparable<ObjectInstance> {

    private UniqueId instanceId;
    private Domain domain;
    private ExecutionContext context;
    private Logger logger;
    private boolean alive;

    public ObjectInstance(Domain domain, ExecutionContext context, Logger logger) {
        this(UniqueId.random(), domain, context, logger);
    }

    public ObjectInstance(UniqueId instanceId, Domain domain, ExecutionContext context, Logger logger) {
        this.instanceId = instanceId;
        this.domain = domain;
        this.context = context;
        this.logger = logger;
        this.alive = false;
    }

    public UniqueId getInstanceId() {
        return instanceId;
    }

    public String getName() {
        return getClass().getSimpleName() + "[" + instanceId.toString() + "]";
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
        return context;
    }

    @Override
    public Logger getLogger() {
        return logger;
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
    public void consumeEvent(Event event) {
        throw new InstancePopulationException(
                "Cannot generate event '" + event.getName() + "' to non-dynamic instance '" + getName() + "'");
    }

}
