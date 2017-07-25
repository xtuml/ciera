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
import ciera.statemachine.Event;
import ciera.util.Timer;
import microwaveovenciera.components.microwaveoven.microwaveoven.beeper.instancestatemachine.BeeperInstanceStateMachine;

public class Beeper extends ModelInstance {
    
    private static final int classId = 4;
    private static final String keyLetters = "MO_B";
    
    // empty instance
    public static final EmptyBeeper emptyBeeper = new EmptyBeeper();
    
    // class attributes
    private UUID m_BeeperID;
    private Timer m_beeper_timer;
    private int m_beep_count;
    private Event m_beeper_delay_over;
    
    // associations
    private Oven MO_OOnR3;
    
    public void setMO_OOnR3(Oven mO_OOnR3) throws XtumlException {
        if ( null == MO_OOnR3 ) MO_OOnR3 = mO_OOnR3;
        else throw new LinkException( "Cannot link to already linked relationship." );
    }

    public void clearMO_OOnR3() throws XtumlException {
        if ( null != MO_OOnR3 ) MO_OOnR3 = null;
        else throw new LinkException( "Cannot unlink non-linked relationship." );
    }

    // constructors
    public Beeper() {
        super( new BeeperInstanceStateMachine() );
    }
    
    // attribute accessors
    public UUID getM_BeeperID() throws XtumlException {
        checkLiving();
        return m_BeeperID;
    }

    public Timer getM_beeper_timer() throws XtumlException {
        checkLiving();
        return m_beeper_timer;
    }

    public void setM_beeper_timer(Timer m_beeper_timer) throws XtumlException {
        checkLiving();
        this.m_beeper_timer = m_beeper_timer;
    }

    public int getM_beep_count() throws XtumlException {
        checkLiving();
        return m_beep_count;
    }

    public void setM_beep_count(int m_beep_count) throws XtumlException {
        checkLiving();
        this.m_beep_count = m_beep_count;
    }

    public Event getM_beeper_delay_over() throws XtumlException {
        checkLiving();
        return m_beeper_delay_over;
    }

    public void setM_beeper_delay_over(Event m_beeper_delay_over) throws XtumlException {
        checkLiving();
        this.m_beeper_delay_over = m_beeper_delay_over;
    }

    // selections
    public Oven selectOneMO_OOnR3() throws XtumlException {
        return selectOneMO_OOnR3( null );
    }
    
    public Oven selectOneMO_OOnR3( Where condition ) throws XtumlException {
        checkLiving();
        if ( !(this instanceof EmptyInstance ) ) {
            if ( null == MO_OOnR3 ) throw new ModelIntegrityException( "Uncoditional association with no related instance." );
            else {
                if ( null == condition || condition.evaluate(MO_OOnR3) ) return MO_OOnR3;
            }
        }
        return (Oven)Oven.emptyOven;
    }
    
    // relates
    public void relateToMO_OAcrossR3( Oven oven ) throws XtumlException {
        oven.relateToMO_BAcrossR3( this );
    }
    
    // unrelates
    public void unrelateFromMO_OAcrossR3( Oven oven ) throws XtumlException {
        oven.unrelateFromMO_BAcrossR3( this );
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
        return XtumlApplication.app.getDefaultThread( Beeper.class );
    }

}

class EmptyBeeper extends Beeper implements EmptyInstance {
}
