package io.ciera.runtime.summit.classes;

import java.util.Arrays;
import java.util.List;

import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.components.IComponent;
import io.ciera.runtime.summit.exceptions.DeletedInstanceException;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

public abstract class ModelInstance<T extends IModelInstance<T, C>, C extends IComponent<C>>
        implements IModelInstance<T, C>, Comparable<T> {

    private UniqueId instanceId;

    // constructors
    public ModelInstance() {
        instanceId = UniqueId.random();
    }

    @Override
    public UniqueId getInstanceId() {
        return instanceId;
    }

    @Override
    public void checkLiving() throws XtumlException {
        if (instanceId.isNull())
            throw new DeletedInstanceException("Access of deleted instance ");
    }

    @Override
    public void delete() throws XtumlException {
        checkLiving();
        if (context().removeInstance(this))
            instanceId.nullify();
        else
            throw new InstancePopulationException("Instance does not exist within this population.");
    }

    @Override
    public boolean equals(Object obj) {
        if (null != obj && obj instanceof IModelInstance<?, ?>) {
            return getInstanceId().equals(((IModelInstance<?, ?>) obj).getInstanceId());
        } else
            return false;
    }

    @Override
    public int hashCode() {
        return getInstanceId().hashCode();
    }

    @Override
    public IInstanceIdentifier getId1() {
        return new InstanceIdentifier() {
            @Override
            public List<Object> getElements() {
                return Arrays.asList(getInstanceId());
            }
        };
    }

    @Override
    public IInstanceIdentifier getId2() {
        return new InstanceIdentifier() {
            @Override
            public List<Object> getElements() {
                return Arrays.asList(getInstanceId());
            }
        };
    }

    @Override
    public IInstanceIdentifier getId3() {
        return new InstanceIdentifier() {
            @Override
            public List<Object> getElements() {
                return Arrays.asList(getInstanceId());
            }
        };
    }

    @Override
    public IRunContext getRunContext() {
        return null;
    }

    @Override
    public C context() {
        return null;
    }

    @Override
    public boolean equality(T value) throws XtumlException {
        return equals(value);
    }

    @Override
    public int compareTo(T o) {
        return instanceId.compareTo(o.getInstanceId());
    }

}
