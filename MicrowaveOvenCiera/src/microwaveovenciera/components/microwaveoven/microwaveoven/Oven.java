package microwaveovenciera.components.microwaveoven.microwaveoven;

import java.util.UUID;

import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.LinkException;
import ciera.classes.exceptions.ModelIntegrityException;
import ciera.util.Timer;

public class Oven extends ModelInstance {
    
    private static final int classId = 1;
    private static final String keyLetters = "MO_O";

    // empty instance
    public static final EmptyOven emptyOven = new EmptyOven();
    
    // class attributes
    private UUID m_OvenID;
    private Timer m_oven_timer;
    private int m_remaining_cooking_time;

    // associations
    private MagnetronTube MO_MTOnR1;
    private InternalLight MO_ILOnR2;
    private Beeper MO_BOnR3;
    private Door MO_DOnR4;
    private Turntable MO_TRNOnR5;

    // constructors
    public Oven() {
        super();
    }
    
    public Oven( UUID OvenID, Timer oven_timer, int remaining_cooking_time ) {
        super();
        m_OvenID = OvenID;
        m_oven_timer = oven_timer;
        m_remaining_cooking_time = remaining_cooking_time;
    }
    
    // attribute accessors
    public UUID getM_OvenID() throws EmptyInstanceException {
        checkLiving();
        return m_OvenID;
    }

    public void setM_OvenID(UUID m_OvenID) throws EmptyInstanceException {
        checkLiving();
        this.m_OvenID = m_OvenID;
    }
    
    public UUID getM_TubeID() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_MTOnR1().getM_TubeID();
    }
    
    public UUID getM_LightID() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_ILOnR2().getM_LightID();
    }
    
    public UUID getM_BeeperID() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_BOnR3().getM_BeeperID();
    }
    
    public UUID getM_DoorID() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_DOnR4().getM_DoorID();
    }
    
    public UUID getM_TurntableID() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_TRNOnR5().getM_TurntableID();
    }

    public Timer getM_oven_timer() throws EmptyInstanceException {
        checkLiving();
        return m_oven_timer;
    }

    public void setM_oven_timer(Timer m_oven_timer) throws EmptyInstanceException {
        checkLiving();
        this.m_oven_timer = m_oven_timer;
    }

    public int getM_remaining_cooking_time() throws EmptyInstanceException {
        checkLiving();
        return m_remaining_cooking_time;
    }

    public void setM_remaining_cooking_time(int m_remaining_cooking_time) throws EmptyInstanceException {
        checkLiving();
        this.m_remaining_cooking_time = m_remaining_cooking_time;
    }

    // selections
    public MagnetronTube selectOneMO_MTOnR1() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_MTOnR1( null );
    }
    
    public MagnetronTube selectOneMO_MTOnR1( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_MTOnR1 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_MTOnR1) ) return MO_MTOnR1;
            }
        }
        return MagnetronTube.emptyMagnetronTube;
    }

    public InternalLight selectOneMO_ILOnR2() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_ILOnR2( null );
    }
    
    public InternalLight selectOneMO_ILOnR2( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_ILOnR2 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_ILOnR2) ) return MO_ILOnR2;
            }
        }
        return InternalLight.emptyInternalLight;
    }

    public Beeper selectOneMO_BOnR3() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_BOnR3( null );
    }
    
    public Beeper selectOneMO_BOnR3( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_BOnR3 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_BOnR3) ) return MO_BOnR3;
            }
        }
        return Beeper.emptyBeeper;
    }

    public Door selectOneMO_DOnR4() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_DOnR4( null );
    }
    
    public Door selectOneMO_DOnR4( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_DOnR4 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_DOnR4) ) return MO_DOnR4;
            }
        }
        return Door.emptyDoor;
    }

    public Turntable selectOneMO_TRNOnR5() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_TRNOnR5( null );
    }
    
    public Turntable selectOneMO_TRNOnR5( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_TRNOnR5 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_TRNOnR5) ) return MO_TRNOnR5;
            }
        }
        return Turntable.emptyTurntable;
    }
    
    // relates
    public void relateToMO_MTAcrossR1( MagnetronTube magnetronTube ) throws EmptyInstanceException, LinkException {
        checkLiving();
        magnetronTube.checkLiving();
        if ( null == MO_MTOnR1 ) {
            MO_MTOnR1 = magnetronTube;
            magnetronTube.setMO_OOnR1( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_ILAcrossR2( InternalLight internalLight ) throws EmptyInstanceException, LinkException {
        checkLiving();
        internalLight.checkLiving();
        if ( null == MO_ILOnR2 ) {
            MO_ILOnR2 = internalLight;
            internalLight.setMO_OOnR2( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_BAcrossR3( Beeper beeper ) throws EmptyInstanceException, LinkException {
        checkLiving();
        beeper.checkLiving();
        if ( null == MO_BOnR3 ) {
            MO_BOnR3 = beeper;
            beeper.setMO_OOnR3( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_DAcrossR4( Door door ) throws EmptyInstanceException, LinkException {
        checkLiving();
        door.checkLiving();
        if ( null == MO_BOnR3 ) {
            MO_DOnR4 = door;
            door.setMO_OOnR4( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_TRNAcrossR5( Turntable turntable ) throws EmptyInstanceException, LinkException {
        checkLiving();
        turntable.checkLiving();
        if ( null == MO_TRNOnR5 ) {
            MO_TRNOnR5 = turntable;
            turntable.setMO_OOnR5( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }
    
    // unrelates
    public void unrelateFromMO_MTAcrossR1( MagnetronTube magnetronTube ) throws EmptyInstanceException, LinkException {
        checkLiving();
        magnetronTube.checkLiving();
        if ( null != MO_MTOnR1 ) {
            MO_MTOnR1 = null;
            magnetronTube.clearMO_OOnR1();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_ILAcrossR2( InternalLight internalLight ) throws EmptyInstanceException, LinkException {
        checkLiving();
        internalLight.checkLiving();
        if ( null != MO_BOnR3 ) {
            MO_BOnR3 = null;
            internalLight.clearMO_OOnR2();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_BAcrossR3( Beeper beeper ) throws EmptyInstanceException, LinkException {
        checkLiving();
        beeper.checkLiving();
        if ( null != MO_BOnR3 ) {
            MO_BOnR3 = null;
            beeper.clearMO_OOnR3();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_DAcrossR4( Door door ) throws EmptyInstanceException, LinkException {
        checkLiving();
        door.checkLiving();
        if ( null != MO_BOnR3 ) {
            MO_DOnR4 = null;
            door.clearMO_OOnR4();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_TRNAcrossR5( Turntable turntable ) throws EmptyInstanceException, LinkException {
        checkLiving();
        turntable.checkLiving();
        if ( null != MO_TRNOnR5 ) {
            MO_TRNOnR5 = null;
            turntable.clearMO_OOnR5();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    @Override
    public int getClassId() {
        return classId;
    }
    
}

class EmptyOven extends Oven implements EmptyInstance {
}

@SuppressWarnings("serial")
class OvenSet extends InstanceSet<Oven> {

    // empty set
    public static final EmptyOvenSet emptyOvenSet = new EmptyOvenSet();

    // selections
    public MagnetronTubeSet selectManyMO_MTsOnR1() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_MTsOnR1( null );
    }

    public MagnetronTubeSet selectManyMO_MTsOnR1( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        MagnetronTubeSet return_set = new MagnetronTubeSet();
        for ( Oven oven : this ) {
            MagnetronTube selected = oven.selectOneMO_MTOnR1( condition );
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
        for ( Oven oven : this ) {
            InternalLight selected = oven.selectOneMO_ILOnR2( condition );
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
        for ( Oven oven : this ) {
            Beeper selected = oven.selectOneMO_BOnR3( condition );
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
        for ( Oven oven : this ) {
            Door selected = oven.selectOneMO_DOnR4( condition );
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
        for ( Oven oven : this ) {
            Turntable selected = oven.selectOneMO_TRNOnR5( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return TurntableSet.emptyTurntableSet;
        else return return_set;
    }

    @Override
    public Oven getEmptyInstance() {
        return Oven.emptyOven;
    }
}

@SuppressWarnings("serial")
class EmptyOvenSet extends OvenSet implements EmptyInstanceSet {
}