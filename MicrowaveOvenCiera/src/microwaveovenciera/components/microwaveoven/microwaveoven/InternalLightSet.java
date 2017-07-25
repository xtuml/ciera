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
    public static final EmptyInternalLightSet emptyInternalLightSet = new EmptyInternalLightSet();

    // selections
    OvenSet selectManyMO_OsOnR2() throws XtumlException {
        return selectManyMO_OsOnR2( null );
    }

    OvenSet selectManyMO_OsOnR2( Where condition ) throws XtumlException {
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
}
