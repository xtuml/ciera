package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.Turntable;
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
import io.ciera.runtime.summit.types.IWhere;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;


public class TurntableImpl extends ModelInstance<Turntable,MicrowaveOven> implements Turntable {

    public static final String KEY_LETTERS = "MO_TRN";
    public static final Turntable EMPTY_TURNTABLE = new EmptyTurntable();

    private MicrowaveOven context;

    // constructors
    private TurntableImpl( MicrowaveOven context ) {
        this.context = context;
        m_TurntableID = UniqueId.random();
        R5_occupies_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new TurntableStateMachine(this, context());
    }

    private TurntableImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_TurntableID, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_TurntableID = m_TurntableID;
        R5_occupies_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new TurntableStateMachine(this, context(), initialState);
    }

    public static Turntable create( MicrowaveOven context ) throws XtumlException {
        Turntable newTurntable = new TurntableImpl( context );
        if ( context.addInstance( newTurntable ) ) {
            newTurntable.getRunContext().addChange(new InstanceCreatedDelta(newTurntable, KEY_LETTERS));
            return newTurntable;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Turntable create( MicrowaveOven context, UniqueId m_TurntableID, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_TurntableID, initialState);
    }

    public static Turntable create( MicrowaveOven context, UniqueId instanceId, UniqueId m_TurntableID, int initialState ) throws XtumlException {
        Turntable newTurntable = new TurntableImpl( context, instanceId, m_TurntableID, initialState );
        if ( context.addInstance( newTurntable ) ) {
            return newTurntable;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final TurntableStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_TurntableID;
    @Override
    public void setTurntableID(UniqueId m_TurntableID) throws XtumlException {
        checkLiving();
        if (m_TurntableID.inequality( this.m_TurntableID)) {
            final UniqueId oldValue = this.m_TurntableID;
            this.m_TurntableID = m_TurntableID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_TurntableID", oldValue, this.m_TurntableID));
            if ( !R5_occupies_Oven().isEmpty() ) R5_occupies_Oven().setTurntableID( m_TurntableID );
        }
    }
    @Override
    public UniqueId getTurntableID() throws XtumlException {
        checkLiving();
        return m_TurntableID;
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getTurntableID());
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
    public static class spin extends Event {
        public spin(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "Turntable";
        }
    }
    public static class stop extends Event {
        public stop(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "Turntable";
        }
    }


    // selections
    private Oven R5_occupies_Oven_inst;
    @Override
    public void setR5_occupies_Oven( Oven inst ) {
        R5_occupies_Oven_inst = inst;
    }
    @Override
    public Oven R5_occupies_Oven() throws XtumlException {
        return R5_occupies_Oven_inst;
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
    public Turntable self() {
        return this;
    }

    @Override
    public Turntable oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_TURNTABLE;
    }

}

class EmptyTurntable extends ModelInstance<Turntable,MicrowaveOven> implements Turntable {

    // attributes
    public void setTurntableID( UniqueId m_TurntableID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getTurntableID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public Oven R5_occupies_Oven() {
        return OvenImpl.EMPTY_OVEN;
    }


    @Override
    public String getKeyLetters() {
        return TurntableImpl.KEY_LETTERS;
    }

    @Override
    public Turntable self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Turntable oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return TurntableImpl.EMPTY_TURNTABLE;
    }

}
