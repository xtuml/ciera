package microwaveovenciera.components.microwaveoven.microwaveoven;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.exceptions.XtumlException;

@SuppressWarnings("serial")
public class OvenSet extends InstanceSet {

    // empty set
    public static final OvenSet emptyOvenSet = new EmptyOvenSet();

    // selections
    public MagnetronTubeSet selectManyMO_MTsOnR1() throws XtumlException {
        return selectManyMO_MTsOnR1( null );
    }

    public MagnetronTubeSet selectManyMO_MTsOnR1( Where condition ) throws XtumlException {
        MagnetronTubeSet return_set = new MagnetronTubeSet();
        for ( ModelInstance oven : this ) {
            MagnetronTube selected = ((Oven)oven).selectOneMO_MTOnR1( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return MagnetronTubeSet.emptyMagnetronTubeSet;
        else return return_set;
    }

    public InternalLightSet selectManyMO_ILsOnR2() throws XtumlException {
        return selectManyMO_ILsOnR2( null );
    }

    public InternalLightSet selectManyMO_ILsOnR2( Where condition ) throws XtumlException {
        InternalLightSet return_set = new InternalLightSet();
        for ( ModelInstance oven : this ) {
            InternalLight selected = ((Oven)oven).selectOneMO_ILOnR2( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return InternalLightSet.emptyInternalLightSet;
        else return return_set;
    }

    public BeeperSet selectManyMO_BsOnR3() throws XtumlException {
        return selectManyMO_BsOnR3( null );
    }

    public BeeperSet selectManyMO_BsOnR3( Where condition ) throws XtumlException {
        BeeperSet return_set = new BeeperSet();
        for ( ModelInstance oven : this ) {
            Beeper selected = ((Oven)oven).selectOneMO_BOnR3( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return BeeperSet.emptyBeeperSet;
        else return return_set;
    }

    public DoorSet selectManyMO_DsOnR4() throws XtumlException {
        return selectManyMO_DsOnR4( null );
    }

    public DoorSet selectManyMO_DsOnR4( Where condition ) throws XtumlException {
        DoorSet return_set = new DoorSet();
        for ( ModelInstance oven : this ) {
            Door selected = ((Oven)oven).selectOneMO_DOnR4( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return DoorSet.emptyDoorSet;
        else return return_set;
    }

    public TurntableSet selectManyMO_TRNsOnR5() throws XtumlException {
        return selectManyMO_TRNsOnR5( null );
    }

    public TurntableSet selectManyMO_TRNsOnR5( Where condition ) throws XtumlException {
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

    // selections
    @Override
    public MagnetronTubeSet selectManyMO_MTsOnR1( Where condition ) throws XtumlException {
        return MagnetronTubeSet.emptyMagnetronTubeSet;
    }

    @Override
    public InternalLightSet selectManyMO_ILsOnR2( Where condition ) throws XtumlException {
        return InternalLightSet.emptyInternalLightSet;
    }

    @Override
    public BeeperSet selectManyMO_BsOnR3( Where condition ) throws XtumlException {
        return BeeperSet.emptyBeeperSet;
    }

    @Override
    public DoorSet selectManyMO_DsOnR4( Where condition ) throws XtumlException {
        return DoorSet.emptyDoorSet;
    }

    @Override
    public TurntableSet selectManyMO_TRNsOnR5( Where condition ) throws XtumlException {
        return TurntableSet.emptyTurntableSet;
    }
    
    @Override
    public Oven selectAnyMO_OFromInstances( Where condition ) {
        return Oven.emptyOven;
    }
    
    @Override
    public OvenSet selectManyMO_OsFromInstances( Where condition ) {
        return OvenSet.emptyOvenSet;
    }

}
