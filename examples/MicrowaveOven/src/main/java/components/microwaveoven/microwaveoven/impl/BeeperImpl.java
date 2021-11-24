package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.impl.OvenImpl;

import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IInstanceIdentifier;
import io.ciera.runtime.summit.classes.InstanceIdentifier;
import io.ciera.runtime.summit.classes.ModelInstance;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.statemachine.Event;
import io.ciera.runtime.summit.statemachine.IEvent;
import io.ciera.runtime.summit.time.TimerHandle;
import io.ciera.runtime.summit.types.IWhere;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;


public class BeeperImpl extends ModelInstance<Beeper,MicrowaveOven> implements Beeper {

    public static final String KEY_LETTERS = "MO_B";
    public static final Beeper EMPTY_BEEPER = new EmptyBeeper();

    private MicrowaveOven context;

    // constructors
    private BeeperImpl( MicrowaveOven context ) {
        this.context = context;
        m_BeeperID = UniqueId.random();
        m_beeper_timer = new TimerHandle();
        m_beep_count = 0;
        R3_is_located_in_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new BeeperStateMachine(this, context());
    }

    private BeeperImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_BeeperID, TimerHandle m_beeper_timer, int m_beep_count, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_BeeperID = m_BeeperID;
        this.m_beeper_timer = m_beeper_timer;
        this.m_beep_count = m_beep_count;
        R3_is_located_in_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new BeeperStateMachine(this, context(), initialState);
    }

    public static Beeper create( MicrowaveOven context ) throws XtumlException {
        Beeper newBeeper = new BeeperImpl( context );
        if ( context.addInstance( newBeeper ) ) {
            newBeeper.getRunContext().addChange(new InstanceCreatedDelta(newBeeper, KEY_LETTERS));
            return newBeeper;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Beeper create( MicrowaveOven context, UniqueId m_BeeperID, TimerHandle m_beeper_timer, int m_beep_count, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_BeeperID, m_beeper_timer, m_beep_count, initialState);
    }

    public static Beeper create( MicrowaveOven context, UniqueId instanceId, UniqueId m_BeeperID, TimerHandle m_beeper_timer, int m_beep_count, int initialState ) throws XtumlException {
        Beeper newBeeper = new BeeperImpl( context, instanceId, m_BeeperID, m_beeper_timer, m_beep_count, initialState );
        if ( context.addInstance( newBeeper ) ) {
            return newBeeper;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final BeeperStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_BeeperID;
    @Override
    public UniqueId getBeeperID() throws XtumlException {
        checkLiving();
        return m_BeeperID;
    }
    @Override
    public void setBeeperID(UniqueId m_BeeperID) throws XtumlException {
        checkLiving();
        if (m_BeeperID.inequality( this.m_BeeperID)) {
            final UniqueId oldValue = this.m_BeeperID;
            this.m_BeeperID = m_BeeperID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_BeeperID", oldValue, this.m_BeeperID));
            if ( !R3_is_located_in_Oven().isEmpty() ) R3_is_located_in_Oven().setBeeperID( m_BeeperID );
        }
    }
    private TimerHandle m_beeper_timer;
    @Override
    public TimerHandle getBeeper_timer() throws XtumlException {
        checkLiving();
        return m_beeper_timer;
    }
    @Override
    public void setBeeper_timer(TimerHandle m_beeper_timer) throws XtumlException {
        checkLiving();
        if (m_beeper_timer.inequality( this.m_beeper_timer)) {
            final TimerHandle oldValue = this.m_beeper_timer;
            this.m_beeper_timer = m_beeper_timer;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_beeper_timer", oldValue, this.m_beeper_timer));
        }
    }
    private int m_beep_count;
    @Override
    public int getBeep_count() throws XtumlException {
        checkLiving();
        return m_beep_count;
    }
    @Override
    public void setBeep_count(int m_beep_count) throws XtumlException {
        checkLiving();
        if (m_beep_count != this.m_beep_count) {
            final int oldValue = this.m_beep_count;
            this.m_beep_count = m_beep_count;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_beep_count", oldValue, this.m_beep_count));
        }
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getBeeperID());
        }
        catch ( XtumlException e ) {
            getRunContext().getLog().error(e);
            System.exit(1);
            return null;
        }
    }

    // operations


    // static operations


    // events
    public static class beep_delay_over extends Event {
        public beep_delay_over(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "Beeper";
        }
    }
    public static class beeping_stopped extends Event {
        public beeping_stopped(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 3;
        }
        @Override
        public String getClassName() {
            return "Beeper";
        }
    }
    public static class start_beeping extends Event {
        public start_beeping(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "Beeper";
        }
    }
    public static class stop_beeping extends Event {
        public stop_beeping(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 2;
        }
        @Override
        public String getClassName() {
            return "Beeper";
        }
    }


    // selections
    private Oven R3_is_located_in_Oven_inst;
    @Override
    public void setR3_is_located_in_Oven( Oven inst ) {
        R3_is_located_in_Oven_inst = inst;
    }
    @Override
    public Oven R3_is_located_in_Oven() throws XtumlException {
        return R3_is_located_in_Oven_inst;
    }


    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public MicrowaveOven context() {
        return context;
    }

    @Override
    public String getKeyLetters() {
        return KEY_LETTERS;
    }

    @Override
    public Beeper self() {
        return this;
    }

    @Override
    public Beeper oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_BEEPER;
    }

}

class EmptyBeeper extends ModelInstance<Beeper,MicrowaveOven> implements Beeper {

    // attributes
    public UniqueId getBeeperID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setBeeperID( UniqueId m_BeeperID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public TimerHandle getBeeper_timer() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setBeeper_timer( TimerHandle m_beeper_timer ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public int getBeep_count() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setBeep_count( int m_beep_count ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public Oven R3_is_located_in_Oven() {
        return OvenImpl.EMPTY_OVEN;
    }


    @Override
    public String getKeyLetters() {
        return BeeperImpl.KEY_LETTERS;
    }

    @Override
    public Beeper self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Beeper oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return BeeperImpl.EMPTY_BEEPER;
    }

}
