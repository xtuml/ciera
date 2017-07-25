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
import microwaveovenciera.components.microwaveoven.microwaveoven.door.instancestatemachine.DoorInstanceStateMachine;

public class Door extends ModelInstance {
    
    private static final int classNumber = 5;
    private static final String keyLetters = "MO_D";
    
    // empty instance
    public static final EmptyDoor emptyDoor = new EmptyDoor();
    
    // class attributes
    private UUID m_DoorID;
    private boolean m_is_secure;
    
    // associations
    private Oven MO_OOnR4;
    
    public void setMO_OOnR4(Oven mO_OOnR4) throws XtumlException {
        if ( null == MO_OOnR4 ) MO_OOnR4 = mO_OOnR4;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR4() throws XtumlException {
        if ( null != MO_OOnR4 ) MO_OOnR4 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public Door() {
        super( new DoorInstanceStateMachine() );
        m_DoorID = UUID.randomUUID();
    }
    
    // attribute accessors
    public UUID getM_DoorID() throws XtumlException {
        checkLiving();
        return m_DoorID;
    }

    public boolean getM_is_secure() throws XtumlException {
        checkLiving();
        return m_is_secure;
    }

    public void setM_is_secure(boolean m_is_secure) throws XtumlException {
        checkLiving();
        this.m_is_secure = m_is_secure;
    }

    // selections
    public Oven selectOneMO_OOnR4() throws XtumlException {
        return selectOneMO_OOnR4( null );
    }
    
    public Oven selectOneMO_OOnR4( Where condition ) throws XtumlException {
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
    public void relateToMO_OAcrossR4( Oven oven ) throws XtumlException {
        oven.relateToMO_DAcrossR4( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR4( Oven oven ) throws XtumlException {
        oven.unrelateFromMO_DAcrossR4( this );
    }

    @Override
    public int getClassNumber() {
        return classNumber;
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
