package io.ciera.runtime.domain;

import java.util.Set;

import io.ciera.runtime.api.application.Application;
import io.ciera.runtime.api.application.Event;
import io.ciera.runtime.api.application.ExecutionContext;
import io.ciera.runtime.api.domain.Domain;
import io.ciera.runtime.api.domain.DynamicObjectInstance;
import io.ciera.runtime.api.domain.EmptyInstance;
import io.ciera.runtime.api.domain.ObjectInstance;
import io.ciera.runtime.api.exceptions.DeletedInstanceException;
import io.ciera.runtime.api.exceptions.EventTargetException;
import io.ciera.runtime.api.types.UniqueId;

public abstract class AbstractObjectInstance implements ObjectInstance {

    private static final long serialVersionUID = 1L;

    private final UniqueId instanceId;
    private final String domainName;
    private transient Domain domain;
    private boolean active;

    public AbstractObjectInstance() {
        this.instanceId = null;
        this.domainName = null;
        this.domain = null;
        this.active = false;
    }

    public AbstractObjectInstance(Domain domain) {
        this(UniqueId.random(), domain);
    }

    public AbstractObjectInstance(UniqueId instanceId, Domain domain) {
        this.instanceId = instanceId;
        this.domainName = domain.getName();
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
            getDomain().deleteInstance(this);
        } else {
            throw new DeletedInstanceException("Cannot delete instance that has already been deleted", getDomain(),
                    this);
        }
    }

    @Override
    public boolean isEmpty() {
        return this instanceof EmptyInstance;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public Domain getDomain() {
        if (domain == null) {
            domain = Application.getInstance().getDomain(domainName);
        }
        return domain;
    }

    @Override
    public ExecutionContext getContext() {
        return getDomain().getContext();
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
        if (isDynamic()) {
            getApplication().getLogger().trace("Passing event through non-dynamic supertype: " + this);
            getSubtypeInstances().stream().filter(AbstractObjectInstance.class::isInstance)
                    .map(AbstractObjectInstance.class::cast).filter(AbstractObjectInstance::isDynamic)
                    .forEach(o -> o.consumeEvent(event));
        } else {
            throw new EventTargetException("Cannot generate event to non-dynamic instance", this, event);
        }
    }

    protected boolean isDynamic() {
        return this instanceof DynamicObjectInstance
                || getSubtypeInstances().stream().filter(AbstractObjectInstance.class::isInstance)
                        .map(AbstractObjectInstance.class::cast).anyMatch(AbstractObjectInstance::isDynamic);
    }

}
