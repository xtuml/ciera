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
import microwaveovenciera.components.microwaveoven.microwaveoven.internallight.instancestatemachine.InternalLightInstanceStateMachine;

public class InternalLight extends ModelInstance {
    
    private static final int classNumber = 3;
    private static final String keyLetters = "MO_IL";
    
    // empty instance
    public static final EmptyInternalLight emptyInternalLight = new EmptyInternalLight();
    
    // class attributes
    private UUID m_LightID;
    
    // associations
    private Oven MO_OOnR2;
    
    public void setMO_OOnR2(Oven mO_OOnR2) throws XtumlException {
        if ( null == MO_OOnR2 ) MO_OOnR2 = mO_OOnR2;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR2() throws XtumlException {
        if ( null != MO_OOnR2 ) MO_OOnR2 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public InternalLight() {
        super( new InternalLightInstanceStateMachine() );
        m_LightID = UUID.randomUUID();
    }
    
    // attribute accessors
    public UUID getM_LightID() throws XtumlException {
        checkLiving();
        return m_LightID;
    }

    // selections
    public Oven selectOneMO_OOnR2() throws XtumlException {
        return selectOneMO_OOnR2( null );
    }
    
    public Oven selectOneMO_OOnR2( Where condition ) throws XtumlException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_OOnR2 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_OOnR2) ) return MO_OOnR2;
            }
        }
        return (Oven)Oven.emptyOven;
    }

    // relates
    public void relateToMO_OAcrossR2( Oven oven ) throws XtumlException {
        oven.relateToMO_ILAcrossR2( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR2( Oven oven ) throws XtumlException {
        oven.unrelateFromMO_ILAcrossR2( this );
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
        return XtumlApplication.app.getDefaultThread( InternalLight.class );
    }

}

class EmptyInternalLight extends InternalLight implements EmptyInstance {
}
