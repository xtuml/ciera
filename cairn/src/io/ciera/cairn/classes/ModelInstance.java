package io.ciera.cairn.classes;

import java.util.Arrays;
import java.util.List;

import io.ciera.summit.application.IRunContext;
import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.components.IComponent;
import io.ciera.summit.exceptions.DeletedInstanceException;
import io.ciera.summit.exceptions.InstancePopulationException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.types.UniqueId;

public abstract class ModelInstance<T extends IModelInstance<T,C>,C extends IComponent<C>> implements IModelInstance<T,C> {


    private UniqueId instanceId;

    // constructors
    public ModelInstance() {
        instanceId = new UniqueId();
    }

    @Override
    public UniqueId getInstanceId() {
        return instanceId;
    }

    @Override
    public void checkLiving() throws XtumlException {
        if ( instanceId.isNull() ) throw new DeletedInstanceException( "Access of deleted instance " );
    }

    @Override
    public void delete() throws XtumlException {
        checkLiving();
        if ( context().removeInstance( this ) ) instanceId.nullify();
        else throw new InstancePopulationException( "Instance does not exist within this population." );
    }

    @Override
    public boolean equals( Object obj ) {
        if ( null != obj && obj instanceof IModelInstance<?,?> && !((IModelInstance<?,?>)obj).isEmpty() ) {
            return getInstanceId().equals( ((IModelInstance<?,?>)obj).getInstanceId() );
        }
        else return false;
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
    public boolean equality( T value) throws XtumlException {
        return equals( value );
    }
    
}
