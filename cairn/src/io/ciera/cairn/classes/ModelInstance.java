package io.ciera.cairn.classes;

import io.ciera.summit.classes.IEmptyInstance;
import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.exceptions.DeletedInstanceException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.util.UniqueId;

public abstract class ModelInstance implements IModelInstance {
    
    // empty instance
    public static final ModelInstance emptyModelInstance = new EmptyModelInstance();

    private UniqueId instanceId;

    private IInstancePopulation context;

    // constructors
    public ModelInstance( IInstancePopulation context ) {
        instanceId = new UniqueId();
        this.context = null;
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
    public IInstancePopulation getContext() {
        return context;
    }
    
    @Override
    public void delete() throws XtumlException {
        checkLiving();
        context = null;
        instanceId.nullify();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( !(obj instanceof ModelInstance ) ) return false;
        return ((ModelInstance)obj).getInstanceId().equals(instanceId);
    }
    
    @Override
    public int compareTo( IModelInstance instance ) {
        return instanceId.compareTo( instance.getInstanceId() );
    }

}

class EmptyModelInstance extends ModelInstance implements IEmptyInstance {

    public EmptyModelInstance() {
		super( null );
	}

	@Override
    public String getKeyLetters() {
        return null;
    }

	@Override
	public IInstanceSet toSet() {
		return null;
	}

}
