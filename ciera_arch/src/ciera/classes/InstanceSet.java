package ciera.classes;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentSkipListSet;

import ciera.application.XtumlApplication;

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
    
    public static InstanceSet getNewInstanceSetForClass( Class<?> object ) {
        String className = object.getCanonicalName() + "Set";
        try {
            Class<?> setClass = Class.forName( className );
            Constructor<?> setConstructor = setClass.getConstructor();
            Object newInstanceSet = setConstructor.newInstance();
            return (InstanceSet)newInstanceSet;
        }
        catch ( Exception e ) {
            e.printStackTrace();
            XtumlApplication.app.stop();
        }
        return null;
    }

    public abstract ModelInstance getEmptyInstance();

}
