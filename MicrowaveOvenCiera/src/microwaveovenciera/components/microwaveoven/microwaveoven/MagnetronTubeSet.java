package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.exceptions.XtumlException;

@SuppressWarnings("serial")
public class MagnetronTubeSet extends InstanceSet {

    // empty set
    public static final EmptyMagnetronTubeSet emptyMagnetronTubeSet = new EmptyMagnetronTubeSet();

    // selections
    OvenSet selectManyMO_OsOnR1() throws XtumlException {
        return selectManyMO_OsOnR1( null );
    }

    OvenSet selectManyMO_OsOnR1( Where condition ) throws XtumlException {
        OvenSet return_set = new OvenSet();
        for ( ModelInstance magnetronTube : this ) {
            Oven selected = ((MagnetronTube)magnetronTube).selectOneMO_OOnR1( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }
    
    public MagnetronTube selectAnyMO_MTFromInstances( Where condition ) {
        return (MagnetronTube)selectAny( condition );
    }
    
    public MagnetronTubeSet selectManyMO_MTsFromInstances( Where condition ) {
        return (MagnetronTubeSet)selectMany( condition );
    }

    @Override
    public MagnetronTube getEmptyInstance() {
        return MagnetronTube.emptyMagnetronTube;
    }

}

@SuppressWarnings("serial")
class EmptyMagnetronTubeSet extends MagnetronTubeSet implements EmptyInstanceSet {
}
