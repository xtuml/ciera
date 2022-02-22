package io.ciera.runtime.domain;

import java.util.Set;

import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractObjectInstance implements ObjectInstance {

    private final UniqueId instanceId;
    private final Domain domain;
    private boolean active;

    public AbstractObjectInstance() {
        this.instanceId = null;
        this.domain = null;
        this.active = false;
    }

    public AbstractObjectInstance(Domain domain) {
        this(UniqueId.random(), domain);
    }

    public AbstractObjectInstance(UniqueId instanceId, Domain domain) {
        this.instanceId = instanceId;
        this.domain = domain;
        this.active = true;
    }

    public Set<ObjectInstance> getSubtypeInstances() {
        return Set.of();
    }

    @Override
    public UniqueId getInstanceId() {
        return instanceId;
    }

    @Override
    public void delete() {
        if (isActive()) {
            active = false;
            domain.deleteInstance(this);
        } else {
            throw new DeletedInstanceException("Cannot delete instance that has already been deleted", getDomain(),
                    this);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return domain.getContext();
    }

    @Override
    public ObjectInstance self() {
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s[%.8s]", getClass().getSimpleName(), instanceId);
    }

    @Override
    public void consumeEvent(Event event) {
        if (getSubtypeInstances().isEmpty()) {
            throw new EventTargetException("Cannot generate event to non-dynamic instance", this, event);
        } else {
            getApplication().getLogger().trace("Passing event through non-dynamic supertype: " + this);
            getSubtypeInstances().stream().forEach(o -> o.consumeEvent(event));
        }
    }

}
