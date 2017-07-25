package ciera.classes;

import java.util.concurrent.ConcurrentSkipListSet;

@SuppressWarnings("serial")
public abstract class InstanceSet extends ConcurrentSkipListSet<ModelInstance> {
    
    public ModelInstance selectAny() {
        return selectAny( null );
    }
    
    @SuppressWarnings("unchecked")
    public <T extends ModelInstance> T selectAny( Where condition ) {
        for ( ModelInstance selected : this ) {
            if ( null == condition || condition.evaluate(selected) ) {
                return (T)selected;
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

    public abstract <T extends ModelInstance> T getEmptyInstance();

}