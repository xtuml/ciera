package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.exceptions.XtumlException;

@SuppressWarnings("serial")
public class TurntableSet extends InstanceSet {

    // empty set
    public static final TurntableSet emptyTurntableSet = new EmptyTurntableSet();

    // selections
    public OvenSet selectManyMO_OsOnR5() throws XtumlException {
        return selectManyMO_OsOnR5( null );
    }

    public OvenSet selectManyMO_OsOnR5( Where condition ) throws XtumlException {
        OvenSet return_set = new OvenSet();
        for ( ModelInstance turntable : this ) {
            Oven selected = ((Turntable)turntable).selectOneMO_OOnR5( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }
    
    public Turntable selectAnyMO_TRNFromInstances( Where condition ) {
        return (Turntable)selectAny( condition );
    }
    
    public TurntableSet selectManyMO_TRNsFromInstances( Where condition ) {
        return (TurntableSet)selectMany( condition );
    }

    @Override
    public Turntable getEmptyInstance() {
        return Turntable.emptyTurntable;
    }

}

@SuppressWarnings("serial")
class EmptyTurntableSet extends TurntableSet implements EmptyInstanceSet {

    // selections
    @Override
    public OvenSet selectManyMO_OsOnR5( Where condition ) throws XtumlException {
        return OvenSet.emptyOvenSet;
    }
    
    @Override
    public Turntable selectAnyMO_TRNFromInstances( Where condition ) {
        return Turntable.emptyTurntable;
    }
    
    @Override
    public TurntableSet selectManyMO_TRNsFromInstances( Where condition ) {
        return TurntableSet.emptyTurntableSet;
    }

}
