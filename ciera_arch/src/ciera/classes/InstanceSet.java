package ciera.classes;

import java.util.concurrent.ConcurrentSkipListSet;

@SuppressWarnings("serial")
public abstract class InstanceSet extends ConcurrentSkipListSet<ModelInstance> {
    
    public ModelInstance selectAny( Where condition ) {
        for ( ModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return selected;
            }
        }
        return getEmptyInstance();
    }
    
    public InstanceSet selectMany( Where condition ) {
        InstanceSet return_set = (InstanceSet)this.clone();
        for ( ModelInstance selected : this ) {
            if ( null != condition && !condition.evaluate(selected) ) {
                return_set.remove(selected);
            }
        }
        return return_set;
    }

    public abstract ModelInstance getEmptyInstance();

}