package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.InternalLight;
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


public class InternalLightImpl extends ModelInstance<InternalLight,MicrowaveOven> implements InternalLight {

    public static final String KEY_LETTERS = "MO_IL";
    public static final InternalLight EMPTY_INTERNALLIGHT = new EmptyInternalLight();

    private MicrowaveOven context;

    // constructors
    private InternalLightImpl( MicrowaveOven context ) {
        this.context = context;
        m_LightID = UniqueId.random();
        R2_illuminates_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new InternalLightStateMachine(this, context());
    }

    private InternalLightImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_LightID, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_LightID = m_LightID;
        R2_illuminates_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new InternalLightStateMachine(this, context(), initialState);
    }

    public static InternalLight create( MicrowaveOven context ) throws XtumlException {
        InternalLight newInternalLight = new InternalLightImpl( context );
        if ( context.addInstance( newInternalLight ) ) {
            newInternalLight.getRunContext().addChange(new InstanceCreatedDelta(newInternalLight, KEY_LETTERS));
            return newInternalLight;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static InternalLight create( MicrowaveOven context, UniqueId m_LightID, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_LightID, initialState);
    }

    public static InternalLight create( MicrowaveOven context, UniqueId instanceId, UniqueId m_LightID, int initialState ) throws XtumlException {
        InternalLight newInternalLight = new InternalLightImpl( context, instanceId, m_LightID, initialState );
        if ( context.addInstance( newInternalLight ) ) {
            return newInternalLight;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final InternalLightStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_LightID;
    @Override
    public UniqueId getLightID() throws XtumlException {
        checkLiving();
        return m_LightID;
    }
    @Override
    public void setLightID(UniqueId m_LightID) throws XtumlException {
        checkLiving();
        if (m_LightID.inequality( this.m_LightID)) {
            final UniqueId oldValue = this.m_LightID;
            this.m_LightID = m_LightID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_LightID", oldValue, this.m_LightID));
            if ( !R2_illuminates_Oven().isEmpty() ) R2_illuminates_Oven().setLightID( m_LightID );
        }
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getLightID());
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
    public static class switch_off extends Event {
        public switch_off(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "InternalLight";
        }
    }
    public static class switch_on extends Event {
        public switch_on(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "InternalLight";
        }
    }


    // selections
    private Oven R2_illuminates_Oven_inst;
    @Override
    public void setR2_illuminates_Oven( Oven inst ) {
        R2_illuminates_Oven_inst = inst;
    }
    @Override
    public Oven R2_illuminates_Oven() throws XtumlException {
        return R2_illuminates_Oven_inst;
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
    public InternalLight self() {
        return this;
    }

    @Override
    public InternalLight oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_INTERNALLIGHT;
    }

}

class EmptyInternalLight extends ModelInstance<InternalLight,MicrowaveOven> implements InternalLight {

    // attributes
    public UniqueId getLightID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setLightID( UniqueId m_LightID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public Oven R2_illuminates_Oven() {
        return OvenImpl.EMPTY_OVEN;
    }


    @Override
    public String getKeyLetters() {
        return InternalLightImpl.KEY_LETTERS;
    }

    @Override
    public InternalLight self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public InternalLight oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return InternalLightImpl.EMPTY_INTERNALLIGHT;
    }

}
