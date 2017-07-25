package microwaveovenciera.components.microwaveoven.microwaveoven;

import java.util.UUID;

import ciera.application.XtumlApplication;
import ciera.application.ApplicationThread;
import ciera.classes.EmptyInstance;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.classes.exceptions.EmptyInstanceException;
import ciera.classes.exceptions.LinkException;
import ciera.classes.exceptions.ModelIntegrityException;
import microwaveovenciera.components.microwaveoven.microwaveoven.turntable.instancestatemachine.TurntableInstanceStateMachine;

public class Turntable extends ModelInstance {
    
    private static final int classId = 6;
    private static final String keyLetters = "MO_TRN";
    
    // empty instance
    public static final EmptyTurntable emptyTurntable = new EmptyTurntable();
    
    // class attributes
    private UUID m_TurntableID;
    
    // associations
    private Oven MO_OOnR5;
    
    public void setMO_OOnR5(Oven mO_OOnR5) throws LinkException {
        if ( null == MO_OOnR5 ) MO_OOnR5 = mO_OOnR5;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR5() throws LinkException {
        if ( null != MO_OOnR5 ) MO_OOnR5 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public Turntable() {
        super( new TurntableInstanceStateMachine() );
    }
    
    // attribute accessors
    public UUID getM_TurntableID() throws EmptyInstanceException {
        checkLiving();
        return m_TurntableID;
    }

    // selections
    public Oven selectOneMO_OOnR5() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_OOnR5( null );
    }
    
    public Oven selectOneMO_OOnR5( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_OOnR5 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_OOnR5) ) return MO_OOnR5;
            }
        }
        return (Oven)Oven.emptyOven;
    }

    // relates
    public void relateToMO_OAcrossR5( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.relateToMO_TRNAcrossR5( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR5( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.unrelateFromMO_TRNAcrossR5( this );
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
        return XtumlApplication.app.getDefaultThread( Turntable.class );
    }

}

class EmptyTurntable extends Turntable implements EmptyInstance {
}