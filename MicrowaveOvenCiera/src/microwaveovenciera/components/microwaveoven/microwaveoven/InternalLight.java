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

public class InternalLight extends ModelInstance {
    
    private static final int classId = 3;
    private static final String keyLetters = "MO_IL";
    
    // empty instance
    public static final EmptyInternalLight emptyInternalLight = new EmptyInternalLight();
    
    // class attributes
    private UUID m_LightID;
    
    // associations
    private Oven MO_OOnR2;
    
    public void setMO_OOnR2(Oven mO_OOnR2) throws LinkException {
        if ( null == MO_OOnR2 ) MO_OOnR2 = mO_OOnR2;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR2() throws LinkException {
        if ( null != MO_OOnR2 ) MO_OOnR2 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public InternalLight() {
        super();
    }
    
    public InternalLight( UUID InternalLightID ) {
        super();
        m_LightID = InternalLightID;
    }
    
    // attribute accessors
    public UUID getM_LightID() throws EmptyInstanceException {
        checkLiving();
        return m_LightID;
    }

    // selections
    public Oven selectOneMO_OOnR2() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_OOnR2( null );
    }
    
    public Oven selectOneMO_OOnR2( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
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
    public void relateToMO_OAcrossR2( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.relateToMO_ILAcrossR2( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR2( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.unrelateFromMO_ILAcrossR2( this );
    }

    @Override
    public int getClassId() {
        return classId;
    }

    @Override
    public ApplicationThread getDefaultThread() {
        return XtumlApplication.app.getDefaultThread( InternalLight.class );
    }

}

class EmptyInternalLight extends InternalLight implements EmptyInstance {
}

@SuppressWarnings("serial")
class InternalLightSet extends InstanceSet<InternalLight> {

    // empty set
    public static final EmptyInternalLightSet emptyInternalLightSet = new EmptyInternalLightSet();

    // selections
    OvenSet selectManyMO_OsOnR2() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_OsOnR2( null );
    }

    OvenSet selectManyMO_OsOnR2( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        OvenSet return_set = new OvenSet();
        for ( InternalLight internalLight : this ) {
            Oven selected = internalLight.selectOneMO_OOnR2( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }

    @Override
    public InternalLight getEmptyInstance() {
        return InternalLight.emptyInternalLight;
    }

}

@SuppressWarnings("serial")
class EmptyInternalLightSet extends InternalLightSet implements EmptyInstanceSet {
}