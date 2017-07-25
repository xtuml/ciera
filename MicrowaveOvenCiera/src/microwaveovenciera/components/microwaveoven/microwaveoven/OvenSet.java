package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.ModelIntegrityException;

@SuppressWarnings("serial")
public class OvenSet extends InstanceSet {

    // empty set
    public static final EmptyOvenSet emptyOvenSet = new EmptyOvenSet();

    // selections
    public MagnetronTubeSet selectManyMO_MTsOnR1() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_MTsOnR1( null );
    }

    public MagnetronTubeSet selectManyMO_MTsOnR1( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        MagnetronTubeSet return_set = new MagnetronTubeSet();
        for ( ModelInstance oven : this ) {
            MagnetronTube selected = ((Oven)oven).selectOneMO_MTOnR1( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return MagnetronTubeSet.emptyMagnetronTubeSet;
        else return return_set;
    }

    public InternalLightSet selectManyMO_ILsOnR2() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_ILsOnR2( null );
    }

    public InternalLightSet selectManyMO_ILsOnR2( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        InternalLightSet return_set = new InternalLightSet();
        for ( ModelInstance oven : this ) {
            InternalLight selected = ((Oven)oven).selectOneMO_ILOnR2( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return InternalLightSet.emptyInternalLightSet;
        else return return_set;
    }

    public BeeperSet selectManyMO_BsOnR3() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_BsOnR3( null );
    }

    public BeeperSet selectManyMO_BsOnR3( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        BeeperSet return_set = new BeeperSet();
        for ( ModelInstance oven : this ) {
            Beeper selected = ((Oven)oven).selectOneMO_BOnR3( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return BeeperSet.emptyBeeperSet;
        else return return_set;
    }

    public DoorSet selectManyMO_DsOnR4() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_DsOnR4( null );
    }

    public DoorSet selectManyMO_DsOnR4( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        DoorSet return_set = new DoorSet();
        for ( ModelInstance oven : this ) {
            Door selected = ((Oven)oven).selectOneMO_DOnR4( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return DoorSet.emptyDoorSet;
        else return return_set;
    }

    public TurntableSet selectManyMO_TRNsOnR5() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_TRNsOnR5( null );
    }

    public TurntableSet selectManyMO_TRNsOnR5( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        TurntableSet return_set = new TurntableSet();
        for ( ModelInstance oven : this ) {
            Turntable selected = ((Oven)oven).selectOneMO_TRNOnR5( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return TurntableSet.emptyTurntableSet;
        else return return_set;
    }
    
    public Oven selectAnyMO_OFromInstances( Where condition ) {
        return (Oven)selectAny( condition );
    }
    
    public OvenSet selectManyMO_OsFromInstances( Where condition ) {
        return (OvenSet)selectMany( condition );
    }

    @Override
    public Oven getEmptyInstance() {
        return Oven.emptyOven;
    }
}

@SuppressWarnings("serial")
class EmptyOvenSet extends OvenSet implements EmptyInstanceSet {
}