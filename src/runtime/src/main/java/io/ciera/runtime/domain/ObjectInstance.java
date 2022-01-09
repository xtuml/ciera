package io.ciera.runtime.domain;

import io.ciera.runtime.action.InstanceActionHome;
import io.ciera.runtime.application.Event;
import io.ciera.runtime.application.EventTarget;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.exceptions.DeletedInstanceException;
import io.ciera.runtime.exceptions.EventTargetException;
import io.ciera.runtime.types.ModelType;
import io.ciera.runtime.types.UniqueId;

public abstract class ObjectInstance extends ModelType
        implements InstanceActionHome, EventTarget, Comparable<ObjectInstance> {

    private final UniqueId instanceId;
    private final Domain domain;
    private ExecutionContext context;
    private boolean alive;

    public ObjectInstance() {
        this.instanceId = null;
        this.domain = null;
        this.context = null;
        this.alive = false;
    }

    public ObjectInstance(Domain domain) {
        this(UniqueId.random(), domain);
    }

    public ObjectInstance(UniqueId instanceId, Domain domain) {
        this.instanceId = instanceId;
        this.domain = domain;
        this.context = null;
        this.alive = true;
    }

    public UniqueId getInstanceId() {
        return instanceId;
    }

    public void delete() {
        if (isAlive()) {
            alive = false;
            domain.deleteInstance(this);
        } else {
            throw new DeletedInstanceException("Cannot delete instance that has already been deleted", getDomain(),
                    this);
        }
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
    public void attachTo(ExecutionContext context) {
        this.context = context;
    }

    @Override
    public ObjectInstance self() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s[%.8s]", getClass().getSimpleName(), instanceId);
    }

    public static ObjectInstance fromString() {
        throw new UnsupportedOperationException("Object instances cannot be deserialized");
    }

    @Override
    public int compareTo(ObjectInstance o) {
        return instanceId.compareTo(o.instanceId);
    }

    @Override
    public void consumeEvent(Event event) {
        throw new EventTargetException("Cannot generate event to non-dynamic instance", this, event);
    }

}