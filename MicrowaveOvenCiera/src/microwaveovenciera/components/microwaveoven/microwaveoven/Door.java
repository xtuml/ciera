package microwaveovenciera.components.microwaveoven.microwaveoven;

import java.util.UUID;

import ciera.application.XtumlApplication;
import ciera.application.ApplicationThread;
import ciera.classes.EmptyInstance;
import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.LinkException;
import ciera.classes.exceptions.ModelIntegrityException;

public class Door extends ModelInstance {
    
    private static final int classId = 5;
    private static final String keyLetters = "MO_D";
    
    // empty instance
    public static final EmptyDoor emptyDoor = new EmptyDoor();
    
    // class attributes
    private UUID m_DoorID;
    private boolean m_is_secure;
    
    // associations
    private Oven MO_OOnR4;
    
    public void setMO_OOnR4(Oven mO_OOnR4) throws LinkException {
        if ( null == MO_OOnR4 ) MO_OOnR4 = mO_OOnR4;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR4() throws LinkException {
        if ( null != MO_OOnR4 ) MO_OOnR4 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public Door() {
        super();
    }
    
    public Door( UUID DoorID, boolean is_secure ) {
        super();
        m_DoorID = DoorID;
        m_is_secure = is_secure;
    }
    
    // attribute accessors
    public UUID getM_DoorID() throws EmptyInstanceException {
        checkLiving();
        return m_DoorID;
    }

    public boolean getM_is_secure() throws EmptyInstanceException {
        checkLiving();
        return m_is_secure;
    }

    public void setM_is_secure(boolean m_is_secure) throws EmptyInstanceException {
        checkLiving();
        this.m_is_secure = m_is_secure;
    }

    // selections
    public Oven selectOneMO_OOnR4() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_OOnR4( null );
    }
    
    public Oven selectOneMO_OOnR4( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_OOnR4 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_OOnR4) ) return MO_OOnR4;
            }
        }
        return (Oven)Oven.emptyOven;
    }

    // relates
    public void relateToMO_OAcrossR4( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.relateToMO_DAcrossR4( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR4( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.unrelateFromMO_DAcrossR4( this );
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
        return XtumlApplication.app.getDefaultThread( Door.class );
    }

}

class EmptyDoor extends Door implements EmptyInstance {
}

@SuppressWarnings("serial")
class DoorSet extends InstanceSet {

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

    @Override
    public Door getEmptyInstance() {
        return Door.emptyDoor;
    }

}

@SuppressWarnings("serial")
class EmptyDoorSet extends DoorSet implements EmptyInstanceSet {
}
