package io.ciera.cairn.classes;

import io.ciera.summit.classes.IEmptyInstance;
import io.ciera.summit.classes.IInstanceIdentifier;
import io.ciera.summit.classes.IInstancePopulation;
import io.ciera.summit.classes.IInstanceSet;
import io.ciera.summit.classes.IModelInstance;
import io.ciera.summit.exceptions.DeletedInstanceException;
import io.ciera.summit.exceptions.XtumlException;
import io.ciera.summit.util.UniqueId;

public abstract class ModelInstance implements IModelInstance {
    

    private UniqueId instanceId;
    private IInstancePopulation context;

    // constructors
    public ModelInstance( IInstancePopulation context ) {
        instanceId = new UniqueId();
        this.context = context;
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
    	if ( null != obj && obj instanceof IModelInstance ) {
    		return getKeyLetters().equals( ((IModelInstance)obj).getKeyLetters() ) &&
    			   getInstanceId().equals( ((IModelInstance)obj).getInstanceId() ) &&
    		       ( null == getId1() || getId1().equals( ((IModelInstance)obj).getId1() ) ) &&
    		       ( null == getId2() || getId2().equals( ((IModelInstance)obj).getId2() ) ) &&
    		       ( null == getId3() || getId3().equals( ((IModelInstance)obj).getId3() ) );
    	}
    	else return false;
    }
    
    @Override
    public int hashCode() {
    	int hash = getKeyLetters().hashCode();
    	hash = hash * 31 + getInstanceId().hashCode();
    	if ( null != getId1() ) hash = hash * 31 + getId1().hashCode();
    	if ( null != getId2() ) hash = hash * 31 + getId2().hashCode();
    	if ( null != getId3() ) hash = hash * 31 + getId3().hashCode();
    	return hash;
    }
    
    // empty instance
    public static final ModelInstance emptyModelInstance = new EmptyModelInstance();

	@Override
	public IInstanceIdentifier getId1() {
		return null;
	}

	@Override
	public IInstanceIdentifier getId2() {
		return null;
	}

	@Override
	public IInstanceIdentifier getId3() {
		return null;
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
