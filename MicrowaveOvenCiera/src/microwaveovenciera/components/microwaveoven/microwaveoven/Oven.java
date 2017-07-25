package microwaveovenciera.components.microwaveoven.microwaveoven;

import java.util.UUID;

import ciera.application.XtumlApplication;
import ciera.application.ApplicationThread;
import ciera.classes.EmptyInstance;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.exceptions.LinkException;
import ciera.exceptions.ModelIntegrityException;
import ciera.exceptions.XtumlException;
import ciera.util.Timer;
import microwaveovenciera.components.microwaveoven.microwaveoven.oven.instancestatemachine.OvenInstanceStateMachine;

public class Oven extends ModelInstance {
    
    private static final int classId = 1;
    private static final String keyLetters = "MO_O";

    // empty instance
    public static final EmptyOven emptyOven = new EmptyOven();
    
    // class attributes
    private UUID m_OvenID;
    private UUID ref_TubeID;
    private UUID ref_LightID;
    private UUID ref_BeeperID;
    private UUID ref_DoorID;
    private UUID ref_TurntableID;
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
        super( new OvenInstanceStateMachine() );
        m_OvenID = UUID.randomUUID();
    }
    
    // attribute accessors
    public UUID getM_OvenID() throws XtumlException {
        checkLiving();
        return m_OvenID;
    }

    public void setM_OvenID(UUID m_OvenID) throws XtumlException {
        checkLiving();
        this.m_OvenID = m_OvenID;
    }
    
    public UUID getM_TubeID() throws XtumlException {
        checkLiving();
        ref_TubeID = selectOneMO_MTOnR1().getM_TubeID();
        return ref_TubeID;
    }
    
    public UUID getM_LightID() throws XtumlException {
        checkLiving();
        ref_LightID = selectOneMO_ILOnR2().getM_LightID();
        return ref_LightID;
    }
    
    public UUID getM_BeeperID() throws XtumlException {
        checkLiving();
        ref_BeeperID = selectOneMO_BOnR3().getM_BeeperID();
        return ref_BeeperID;
    }
    
    public UUID getM_DoorID() throws XtumlException {
        checkLiving();
        ref_DoorID = selectOneMO_DOnR4().getM_DoorID();
        return ref_DoorID;
    }
    
    public UUID getM_TurntableID() throws XtumlException {
        checkLiving();
        ref_TurntableID = selectOneMO_TRNOnR5().getM_TurntableID();
        return ref_TurntableID;
    }

    public Timer getM_oven_timer() throws XtumlException {
        checkLiving();
        return m_oven_timer;
    }

    public void setM_oven_timer(Timer m_oven_timer) throws XtumlException {
        checkLiving();
        this.m_oven_timer = m_oven_timer;
    }

    public int getM_remaining_cooking_time() throws XtumlException {
        checkLiving();
        return m_remaining_cooking_time;
    }

    public void setM_remaining_cooking_time(int m_remaining_cooking_time) throws XtumlException {
        checkLiving();
        this.m_remaining_cooking_time = m_remaining_cooking_time;
    }

    // selections
    public MagnetronTube selectOneMO_MTOnR1() throws XtumlException {
        return selectOneMO_MTOnR1( null );
    }
    
    public MagnetronTube selectOneMO_MTOnR1( Where condition ) throws XtumlException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_MTOnR1 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_MTOnR1) ) return MO_MTOnR1;
            }
        }
        return MagnetronTube.emptyMagnetronTube;
    }

    public InternalLight selectOneMO_ILOnR2() throws XtumlException {
        return selectOneMO_ILOnR2( null );
    }
    
    public InternalLight selectOneMO_ILOnR2( Where condition ) throws XtumlException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_ILOnR2 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_ILOnR2) ) return MO_ILOnR2;
            }
        }
        return InternalLight.emptyInternalLight;
    }

    public Beeper selectOneMO_BOnR3() throws XtumlException {
        return selectOneMO_BOnR3( null );
    }
    
    public Beeper selectOneMO_BOnR3( Where condition ) throws XtumlException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_BOnR3 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_BOnR3) ) return MO_BOnR3;
            }
        }
        return Beeper.emptyBeeper;
    }

    public Door selectOneMO_DOnR4() throws XtumlException {
        return selectOneMO_DOnR4( null );
    }
    
    public Door selectOneMO_DOnR4( Where condition ) throws XtumlException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_DOnR4 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_DOnR4) ) return MO_DOnR4;
            }
        }
        return Door.emptyDoor;
    }

    public Turntable selectOneMO_TRNOnR5() throws XtumlException {
        return selectOneMO_TRNOnR5( null );
    }
    
    public Turntable selectOneMO_TRNOnR5( Where condition ) throws XtumlException {
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
    public void relateToMO_MTAcrossR1( MagnetronTube magnetronTube ) throws XtumlException {
        checkLiving();
        magnetronTube.checkLiving();
        if ( null == MO_MTOnR1 ) {
            MO_MTOnR1 = magnetronTube;
            magnetronTube.setMO_OOnR1( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_ILAcrossR2( InternalLight internalLight ) throws XtumlException {
        checkLiving();
        internalLight.checkLiving();
        if ( null == MO_ILOnR2 ) {
            MO_ILOnR2 = internalLight;
            internalLight.setMO_OOnR2( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_BAcrossR3( Beeper beeper ) throws XtumlException {
        checkLiving();
        beeper.checkLiving();
        if ( null == MO_BOnR3 ) {
            MO_BOnR3 = beeper;
            beeper.setMO_OOnR3( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_DAcrossR4( Door door ) throws XtumlException {
        checkLiving();
        door.checkLiving();
        if ( null == MO_BOnR3 ) {
            MO_DOnR4 = door;
            door.setMO_OOnR4( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void relateToMO_TRNAcrossR5( Turntable turntable ) throws XtumlException {
        checkLiving();
        turntable.checkLiving();
        if ( null == MO_TRNOnR5 ) {
            MO_TRNOnR5 = turntable;
            turntable.setMO_OOnR5( this );
        }
        else throw new LinkException( "Cannot link to already linked relationship." );
    }
    
    // unrelates
    public void unrelateFromMO_MTAcrossR1( MagnetronTube magnetronTube ) throws XtumlException {
        checkLiving();
        magnetronTube.checkLiving();
        if ( null != MO_MTOnR1 ) {
            MO_MTOnR1 = null;
            magnetronTube.clearMO_OOnR1();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_ILAcrossR2( InternalLight internalLight ) throws XtumlException {
        checkLiving();
        internalLight.checkLiving();
        if ( null != MO_BOnR3 ) {
            MO_BOnR3 = null;
            internalLight.clearMO_OOnR2();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_BAcrossR3( Beeper beeper ) throws XtumlException {
        checkLiving();
        beeper.checkLiving();
        if ( null != MO_BOnR3 ) {
            MO_BOnR3 = null;
            beeper.clearMO_OOnR3();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_DAcrossR4( Door door ) throws XtumlException {
        checkLiving();
        door.checkLiving();
        if ( null != MO_BOnR3 ) {
            MO_DOnR4 = null;
            door.clearMO_OOnR4();
        }
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    public void unrelateFromMO_TRNAcrossR5( Turntable turntable ) throws XtumlException {
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
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

    @Override
    public ApplicationThread getDefaultThread() {
        return XtumlApplication.app.getDefaultThread( Oven.class );
    }
    
}

class EmptyOven extends Oven implements EmptyInstance {
}
