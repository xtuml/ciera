package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Door;
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


public class DoorImpl extends ModelInstance<Door,MicrowaveOven> implements Door {

    public static final String KEY_LETTERS = "MO_D";
    public static final Door EMPTY_DOOR = new EmptyDoor();

    private MicrowaveOven context;

    // constructors
    private DoorImpl( MicrowaveOven context ) {
        this.context = context;
        m_DoorID = UniqueId.random();
        m_is_secure = false;
        R4_provides_access_to_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new DoorStateMachine(this, context());
    }

    private DoorImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_DoorID, boolean m_is_secure, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_DoorID = m_DoorID;
        this.m_is_secure = m_is_secure;
        R4_provides_access_to_Oven_inst = OvenImpl.EMPTY_OVEN;
        statemachine = new DoorStateMachine(this, context(), initialState);
    }

    public static Door create( MicrowaveOven context ) throws XtumlException {
        Door newDoor = new DoorImpl( context );
        if ( context.addInstance( newDoor ) ) {
            newDoor.getRunContext().addChange(new InstanceCreatedDelta(newDoor, KEY_LETTERS));
            return newDoor;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Door create( MicrowaveOven context, UniqueId m_DoorID, boolean m_is_secure, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_DoorID, m_is_secure, initialState);
    }

    public static Door create( MicrowaveOven context, UniqueId instanceId, UniqueId m_DoorID, boolean m_is_secure, int initialState ) throws XtumlException {
        Door newDoor = new DoorImpl( context, instanceId, m_DoorID, m_is_secure, initialState );
        if ( context.addInstance( newDoor ) ) {
            return newDoor;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final DoorStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_DoorID;
    @Override
    public void setDoorID(UniqueId m_DoorID) throws XtumlException {
        checkLiving();
        if (m_DoorID.inequality( this.m_DoorID)) {
            final UniqueId oldValue = this.m_DoorID;
            this.m_DoorID = m_DoorID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_DoorID", oldValue, this.m_DoorID));
            if ( !R4_provides_access_to_Oven().isEmpty() ) R4_provides_access_to_Oven().setDoorID( m_DoorID );
        }
    }
    @Override
    public UniqueId getDoorID() throws XtumlException {
        checkLiving();
        return m_DoorID;
    }
    private boolean m_is_secure;
    @Override
    public boolean getIs_secure() throws XtumlException {
        checkLiving();
        return m_is_secure;
    }
    @Override
    public void setIs_secure(boolean m_is_secure) throws XtumlException {
        checkLiving();
        if (m_is_secure != this.m_is_secure) {
            final boolean oldValue = this.m_is_secure;
            this.m_is_secure = m_is_secure;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_is_secure", oldValue, this.m_is_secure));
        }
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getDoorID());
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
    public static class close extends Event {
        public close(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "Door";
        }
    }
    public static class release extends Event {
        public release(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "Door";
        }
    }


    // selections
    private Oven R4_provides_access_to_Oven_inst;
    @Override
    public void setR4_provides_access_to_Oven( Oven inst ) {
        R4_provides_access_to_Oven_inst = inst;
    }
    @Override
    public Oven R4_provides_access_to_Oven() throws XtumlException {
        return R4_provides_access_to_Oven_inst;
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
    public Door self() {
        return this;
    }

    @Override
    public Door oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_DOOR;
    }

}

class EmptyDoor extends ModelInstance<Door,MicrowaveOven> implements Door {

    // attributes
    public void setDoorID( UniqueId m_DoorID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getDoorID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public boolean getIs_secure() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setIs_secure( boolean m_is_secure ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public Oven R4_provides_access_to_Oven() {
        return OvenImpl.EMPTY_OVEN;
    }


    @Override
    public String getKeyLetters() {
        return DoorImpl.KEY_LETTERS;
    }

    @Override
    public Door self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Door oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return DoorImpl.EMPTY_DOOR;
    }

}
