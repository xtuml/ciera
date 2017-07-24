package ciera.classes;

import java.util.concurrent.ConcurrentSkipListSet;

@SuppressWarnings("serial")
public abstract class InstanceSet<T extends ModelInstance> extends ConcurrentSkipListSet<T> {
    
    public T selectAny() {
        return selectAny( null );
    }
    
    public T selectAny( Where condition ) {
        for ( T selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return selected;
            }
        }
        return getEmptyInstance();
    }
    
    public InstanceSet<T> selectMany( Where condition ) {
        InstanceSet<T> return_set = (InstanceSet<T>)this.clone();
        for ( T selected : this ) {
            if ( null != condition && !condition.evaluate(selected) ) {
                return_set.remove(selected);
            }
        }
        return return_set;
    }

    public abstract T getEmptyInstance();
    
}