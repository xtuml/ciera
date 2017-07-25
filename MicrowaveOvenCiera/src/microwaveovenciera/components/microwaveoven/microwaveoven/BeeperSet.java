package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;

@SuppressWarnings("serial")
public class BeeperSet extends InstanceSet {

    // empty set
    public static final EmptyBeeperSet emptyBeeperSet = new EmptyBeeperSet();

    // selections
    public OvenSet selectManyMO_OsOnR3() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_OsOnR3( null );
    }

    public OvenSet selectManyMO_OsOnR3( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        OvenSet return_set = new OvenSet();
        for ( ModelInstance beeper : this ) {
            Oven selected = ((Beeper)beeper).selectOneMO_OOnR3( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }
    
    public Beeper selectAnyMO_BFromInstances( Where condition ) {
        return (Beeper)selectAny( condition );
    }
    
    public BeeperSet selectManyMO_BsFromInstances( Where condition ) {
        return (BeeperSet)selectMany( condition );
    }

    @Override
    public Beeper getEmptyInstance() {
        return Beeper.emptyBeeper;
    }

}

@SuppressWarnings("serial")
class EmptyBeeperSet extends BeeperSet implements EmptyInstanceSet {
}