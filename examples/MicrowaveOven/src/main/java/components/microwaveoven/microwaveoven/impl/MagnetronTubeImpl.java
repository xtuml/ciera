package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.datatypes.Tube_Wattage;
import components.microwaveoven.microwaveoven.MagnetronTube;
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
import io.ciera.runtime.summit.types.IWhere;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.UniqueId;


public class MagnetronTubeImpl extends ModelInstance<MagnetronTube,MicrowaveOven> implements MagnetronTube {

    public static final String KEY_LETTERS = "MO_MT";
    public static final MagnetronTube EMPTY_MAGNETRONTUBE = new EmptyMagnetronTube();

    private MicrowaveOven context;

    // constructors
    private MagnetronTubeImpl( MicrowaveOven context ) {
        this.context = context;
        m_TubeID = UniqueId.random();
        m_current_power_output = Tube_Wattage.UNINITIALIZED_ENUM;
        R1_is_housed_in_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new MagnetronTubeStateMachine(this, context());
    }

    private MagnetronTubeImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_TubeID, Tube_Wattage m_current_power_output, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_TubeID = m_TubeID;
        this.m_current_power_output = m_current_power_output;
        R1_is_housed_in_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new MagnetronTubeStateMachine(this, context(), initialState);
    }

    public static MagnetronTube create( MicrowaveOven context ) throws XtumlException {
        MagnetronTube newMagnetronTube = new MagnetronTubeImpl( context );
        if ( context.addInstance( newMagnetronTube ) ) {
            newMagnetronTube.getRunContext().addChange(new InstanceCreatedDelta(newMagnetronTube, KEY_LETTERS));
            return newMagnetronTube;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static MagnetronTube create( MicrowaveOven context, UniqueId m_TubeID, Tube_Wattage m_current_power_output, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_TubeID, m_current_power_output, initialState);
    }

    public static MagnetronTube create( MicrowaveOven context, UniqueId instanceId, UniqueId m_TubeID, Tube_Wattage m_current_power_output, int initialState ) throws XtumlException {
        MagnetronTube newMagnetronTube = new MagnetronTubeImpl( context, instanceId, m_TubeID, m_current_power_output, initialState );
        if ( context.addInstance( newMagnetronTube ) ) {
            return newMagnetronTube;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final MagnetronTubeStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_TubeID;
    @Override
    public UniqueId getTubeID() throws XtumlException {
        checkLiving();
        return m_TubeID;
    }
    @Override
    public void setTubeID(UniqueId m_TubeID) throws XtumlException {
        checkLiving();
        if (m_TubeID.inequality( this.m_TubeID)) {
            final UniqueId oldValue = this.m_TubeID;
            this.m_TubeID = m_TubeID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_TubeID", oldValue, this.m_TubeID));
            if ( !R1_is_housed_in_Oven().isEmpty() ) R1_is_housed_in_Oven().setTubeID( m_TubeID );
        }
    }
    private Tube_Wattage m_current_power_output;
    @Override
    public void setCurrent_power_output(Tube_Wattage m_current_power_output) throws XtumlException {
        checkLiving();
        if (m_current_power_output.inequality( this.m_current_power_output)) {
            final Tube_Wattage oldValue = this.m_current_power_output;
            this.m_current_power_output = m_current_power_output;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_current_power_output", oldValue, this.m_current_power_output));
        }
    }
    @Override
    public Tube_Wattage getCurrent_power_output() throws XtumlException {
        checkLiving();
        return m_current_power_output;
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getTubeID());
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
    public static class decrease_power extends Event {
        public decrease_power(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 2;
        }
        @Override
        public String getClassName() {
            return "MagnetronTube";
        }
    }
    public static class increase_power extends Event {
        public increase_power(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "MagnetronTube";
        }
    }
    public static class power_off extends Event {
        public power_off(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "MagnetronTube";
        }
    }
    public static class power_on extends Event {
        public power_on(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 3;
        }
        @Override
        public String getClassName() {
            return "MagnetronTube";
        }
    }


    // selections
    private Oven R1_is_housed_in_Oven_inst;
    @Override
    public void setR1_is_housed_in_Oven( Oven inst ) {
        R1_is_housed_in_Oven_inst = inst;
    }
    @Override
    public Oven R1_is_housed_in_Oven() throws XtumlException {
        return R1_is_housed_in_Oven_inst;
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
    public MagnetronTube self() {
        return this;
    }

    @Override
    public MagnetronTube oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_MAGNETRONTUBE;
    }

}

class EmptyMagnetronTube extends ModelInstance<MagnetronTube,MicrowaveOven> implements MagnetronTube {

    // attributes
    public UniqueId getTubeID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setTubeID( UniqueId m_TubeID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public void setCurrent_power_output( Tube_Wattage m_current_power_output ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public Tube_Wattage getCurrent_power_output() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public Oven R1_is_housed_in_Oven() {
        return OvenImpl.EMPTY_OVEN;
    }


    @Override
    public String getKeyLetters() {
        return MagnetronTubeImpl.KEY_LETTERS;
    }

    @Override
    public MagnetronTube self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public MagnetronTube oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return MagnetronTubeImpl.EMPTY_MAGNETRONTUBE;
    }

}
