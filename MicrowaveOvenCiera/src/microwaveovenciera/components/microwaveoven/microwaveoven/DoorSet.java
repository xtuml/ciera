package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;

@SuppressWarnings("serial")
public class DoorSet extends InstanceSet {

    // empty set
    public static final EmptyDoorSet emptyDoorSet = new EmptyDoorSet();

    // selections
    OvenSet selectManyMO_OsOnR4() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_OsOnR4( null );
    }

    OvenSet selectManyMO_OsOnR4( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        OvenSet return_set = new OvenSet();
        for ( ModelInstance door : this ) {
            Oven selected = ((Door)door).selectOneMO_OOnR4( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }
    
    public Door selectAnyMO_DFromInstances( Where condition ) {
        return (Door)selectAny( condition );
    }
    
    public DoorSet selectManyMO_DsFromInstances( Where condition ) {
        return (DoorSet)selectMany( condition );
    }

    @Override
    public Door getEmptyInstance() {
        return Door.emptyDoor;
    }

}

@SuppressWarnings("serial")
class EmptyDoorSet extends DoorSet implements EmptyInstanceSet {
}
