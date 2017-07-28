package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.exceptions.XtumlException;

@SuppressWarnings("serial")
public class InternalLightSet extends InstanceSet {

    // empty set
    public static final InternalLightSet emptyInternalLightSet = new EmptyInternalLightSet();

    // selections
    public OvenSet selectManyMO_OsOnR2() throws XtumlException {
        return selectManyMO_OsOnR2( null );
    }

    public OvenSet selectManyMO_OsOnR2( Where condition ) throws XtumlException {
        OvenSet return_set = new OvenSet();
        for ( ModelInstance internalLight : this ) {
            Oven selected = ((InternalLight)internalLight).selectOneMO_OOnR2( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }
    
    public InternalLight selectAnyMO_ILFromInstances( Where condition ) {
        return (InternalLight)selectAny( condition );
    }
    
    public InternalLightSet selectManyMO_ILsFromInstances( Where condition ) {
        return (InternalLightSet)selectMany( condition );
    }

    @Override
    public InternalLight getEmptyInstance() {
        return InternalLight.emptyInternalLight;
    }

}

@SuppressWarnings("serial")
class EmptyInternalLightSet extends InternalLightSet implements EmptyInstanceSet {

    // selections
    @Override
    public OvenSet selectManyMO_OsOnR2( Where condition ) throws XtumlException {
        return OvenSet.emptyOvenSet;
    }
    
    @Override
    public InternalLight selectAnyMO_ILFromInstances( Where condition ) {
        return InternalLight.emptyInternalLight;
    }
    
    @Override
    public InternalLightSet selectManyMO_ILsFromInstances( Where condition ) {
        return InternalLightSet.emptyInternalLightSet;
    }

}
