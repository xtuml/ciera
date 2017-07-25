package microwaveovenciera.components.microwaveoven.microwaveoven;

import java.util.UUID;

import microwaveovenciera.components.microwaveoven.datatypes.TubeWattage;
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

public class MagnetronTube extends ModelInstance {
    
    private static final int classId = 2;
    private static final String keyLetters = "MO_MT";
    
    // empty instance
    public static final EmptyMagnetronTube emptyMagnetronTube = new EmptyMagnetronTube();
    
    // class attributes
    private UUID m_TubeID;
    private TubeWattage m_current_power_output;
    
    // associations
    private Oven MO_OOnR1;
    
    public void setMO_OOnR1(Oven mO_OOnR1) throws LinkException {
        if ( null == MO_OOnR1 ) MO_OOnR1 = mO_OOnR1;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR1() throws LinkException {
        if ( null != MO_OOnR1 ) MO_OOnR1 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public MagnetronTube() {
        super();
    }
    
    public MagnetronTube( UUID MagnetronTubeID, TubeWattage current_power_output ) {
        super();
        m_TubeID = MagnetronTubeID;
        m_current_power_output = current_power_output;
    }
    
    // attribute accessors
    public UUID getM_TubeID() throws EmptyInstanceException {
        checkLiving();
        return m_TubeID;
    }

    public TubeWattage getM_current_power_output() throws EmptyInstanceException {
        checkLiving();
        return m_current_power_output;
    }

    public void setM_current_power_output(TubeWattage m_current_power_output) throws EmptyInstanceException {
        checkLiving();
        this.m_current_power_output = m_current_power_output;
    }

    // selections
    public Oven selectOneMO_OOnR1() throws ModelIntegrityException, EmptyInstanceException {
        return selectOneMO_OOnR1( null );
    }
    
    public Oven selectOneMO_OOnR1( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_OOnR1 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_OOnR1) ) return MO_OOnR1;
            }
        }
        return (Oven)Oven.emptyOven;
    }

    // relates
    public void relateToMO_OAcrossR1( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.relateToMO_MTAcrossR1( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR1( Oven oven ) throws EmptyInstanceException, LinkException {
        oven.unrelateFromMO_MTAcrossR1( this );
    }

    @Override
    public int getClassId() {
        return classId;
    }

    @Override
    public ApplicationThread getDefaultThread() {
        return XtumlApplication.app.getDefaultThread( MagnetronTube.class );
    }

}

class EmptyMagnetronTube extends MagnetronTube implements EmptyInstance {
}

@SuppressWarnings("serial")
class MagnetronTubeSet extends InstanceSet {

    // empty set
    public static final EmptyMagnetronTubeSet emptyMagnetronTubeSet = new EmptyMagnetronTubeSet();

    // selections
    OvenSet selectManyMO_OsOnR1() throws ModelIntegrityException, EmptyInstanceException {
        return selectManyMO_OsOnR1( null );
    }

    OvenSet selectManyMO_OsOnR1( Where condition ) throws ModelIntegrityException, EmptyInstanceException {
        OvenSet return_set = new OvenSet();
        for ( ModelInstance magnetronTube : this ) {
            Oven selected = ((MagnetronTube)magnetronTube).selectOneMO_OOnR1( condition );
            if ( !(selected instanceof EmptyInstance ) ) return_set.add( selected );
        }
        if ( return_set.isEmpty() ) return OvenSet.emptyOvenSet;
        else return return_set;
    }

    @Override
    public MagnetronTube getEmptyInstance() {
        return MagnetronTube.emptyMagnetronTube;
    }

}

@SuppressWarnings("serial")
class EmptyMagnetronTubeSet extends MagnetronTubeSet implements EmptyInstanceSet {
}