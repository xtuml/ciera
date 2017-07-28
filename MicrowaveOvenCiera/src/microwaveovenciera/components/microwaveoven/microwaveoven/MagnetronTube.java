package microwaveovenciera.components.microwaveoven.microwaveoven;

import java.util.UUID;

import microwaveovenciera.components.microwaveoven.datatypes.TubeWattage;
import microwaveovenciera.components.microwaveoven.microwaveoven.magnetrontube.instancestatemachine.MagnetronTubeInstanceStateMachine;
import ciera.classes.EmptyInstance;
import ciera.classes.ModelInstance;
import ciera.classes.Where;
import ciera.exceptions.EmptyInstanceException;
import ciera.exceptions.LinkException;
import ciera.exceptions.ModelIntegrityException;
import ciera.exceptions.XtumlException;

public class MagnetronTube extends ModelInstance {
    
    private static final int classNumber = 2;
    private static final String keyLetters = "MO_MT";
    
    // empty instance
    public static final MagnetronTube emptyMagnetronTube = new EmptyMagnetronTube();
    
    // class attributes
    private UUID m_TubeID;
    private TubeWattage m_current_power_output;
    
    // associations
    private Oven MO_OOnR1;
    
    public void setMO_OOnR1(Oven mO_OOnR1) throws XtumlException {
        if ( null == MO_OOnR1 ) MO_OOnR1 = mO_OOnR1;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR1() throws XtumlException {
        if ( null != MO_OOnR1 ) MO_OOnR1 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }
    
    // constructors
    public MagnetronTube() {
        super( new MagnetronTubeInstanceStateMachine() );
        m_TubeID = UUID.randomUUID();
    }
    
    // attribute accessors
    public UUID getM_TubeID() throws XtumlException {
        checkLiving();
        return m_TubeID;
    }

    public TubeWattage getM_current_power_output() throws XtumlException {
        checkLiving();
        return m_current_power_output;
    }

    public void setM_current_power_output(TubeWattage m_current_power_output) throws XtumlException {
        checkLiving();
        this.m_current_power_output = m_current_power_output;
    }

    // selections
    public Oven selectOneMO_OOnR1() throws XtumlException {
        return selectOneMO_OOnR1( null );
    }
    
    public Oven selectOneMO_OOnR1( Where condition ) throws XtumlException {
        checkLiving();
        if ( null == MO_OOnR1 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
        else if ( null == condition || condition.evaluate(MO_OOnR1) ) return MO_OOnR1;
        else return Oven.emptyOven;
    }

    // relates
    public void relateToMO_OAcrossR1( Oven oven ) throws XtumlException {
        oven.relateToMO_MTAcrossR1( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR1( Oven oven ) throws XtumlException {
        oven.unrelateFromMO_MTAcrossR1( this );
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

}

class EmptyMagnetronTube extends MagnetronTube implements EmptyInstance {

    // selections
    @Override
    public Oven selectOneMO_OOnR1( Where condition ) throws XtumlException {
        return Oven.emptyOven;
    }

    // relates
    @Override
    public void relateToMO_OAcrossR1( Oven oven ) throws XtumlException {
        throw new EmptyInstanceException( "Relate of empty instance" );
    }
    
    @Override
    public void setMO_OOnR1(Oven mO_OOnR1) throws XtumlException {
        throw new EmptyInstanceException( "Relate of empty instance" );
    }
    
    // unrelates
    @Override
    public void unrelateFromMO_OAcrossR1( Oven oven ) throws XtumlException {
        throw new EmptyInstanceException( "Unrelate of empty instance" );
    }

    @Override
    public void clearMO_OOnR1() throws XtumlException {
        throw new EmptyInstanceException( "Unrelate of empty instance" );
    }

}
