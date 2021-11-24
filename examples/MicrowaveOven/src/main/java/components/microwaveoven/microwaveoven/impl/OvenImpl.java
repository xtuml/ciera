package components.microwaveoven.microwaveoven.impl;


import components.MicrowaveOven;
import components.microwaveoven.microwaveoven.Beeper;
import components.microwaveoven.microwaveoven.Door;
import components.microwaveoven.microwaveoven.InternalLight;
import components.microwaveoven.microwaveoven.MagnetronTube;
import components.microwaveoven.microwaveoven.Oven;
import components.microwaveoven.microwaveoven.Turntable;
import components.microwaveoven.microwaveoven.impl.BeeperImpl;
import components.microwaveoven.microwaveoven.impl.DoorImpl;
import components.microwaveoven.microwaveoven.impl.InternalLightImpl;
import components.microwaveoven.microwaveoven.impl.MagnetronTubeImpl;
import components.microwaveoven.microwaveoven.impl.TurntableImpl;

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


public class OvenImpl extends ModelInstance<Oven,MicrowaveOven> implements Oven {

    public static final String KEY_LETTERS = "MO_O";
    public static final Oven EMPTY_OVEN = new EmptyOven();

    private MicrowaveOven context;

    // constructors
    private OvenImpl( MicrowaveOven context ) {
        this.context = context;
        m_OvenID = UniqueId.random();
        ref_TubeID = UniqueId.random();
        ref_LightID = UniqueId.random();
        ref_BeeperID = UniqueId.random();
        ref_DoorID = UniqueId.random();
        ref_TurntableID = UniqueId.random();
        m_oven_timer = new TimerHandle();
        m_remaining_cooking_time = 0;
        R1_houses_MagnetronTube_inst = MagnetronTubeImpl.EMPTY_MAGNETRONTUBE;
        R2_is_illuminated_by_InternalLight_inst = InternalLightImpl.EMPTY_INTERNALLIGHT;
        R3_features_Beeper_inst = BeeperImpl.EMPTY_BEEPER;
        R4_is_accessed_via_Door_inst = DoorImpl.EMPTY_DOOR;
        R5_has_Turntable_inst = TurntableImpl.EMPTY_TURNTABLE;
        statemachine = new OvenStateMachine(this, context());
    }

    private OvenImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_OvenID, UniqueId ref_TubeID, UniqueId ref_LightID, UniqueId ref_BeeperID, UniqueId ref_DoorID, UniqueId ref_TurntableID, TimerHandle m_oven_timer, int m_remaining_cooking_time, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_OvenID = m_OvenID;
        this.ref_TubeID = ref_TubeID;
        this.ref_LightID = ref_LightID;
        this.ref_BeeperID = ref_BeeperID;
        this.ref_DoorID = ref_DoorID;
        this.ref_TurntableID = ref_TurntableID;
        this.m_oven_timer = m_oven_timer;
        this.m_remaining_cooking_time = m_remaining_cooking_time;
        R1_houses_MagnetronTube_inst = MagnetronTubeImpl.EMPTY_MAGNETRONTUBE;
        R2_is_illuminated_by_InternalLight_inst = InternalLightImpl.EMPTY_INTERNALLIGHT;
        R3_features_Beeper_inst = BeeperImpl.EMPTY_BEEPER;
        R4_is_accessed_via_Door_inst = DoorImpl.EMPTY_DOOR;
        R5_has_Turntable_inst = TurntableImpl.EMPTY_TURNTABLE;
        statemachine = new OvenStateMachine(this, context(), initialState);
    }

    public static Oven create( MicrowaveOven context ) throws XtumlException {
        Oven newOven = new OvenImpl( context );
        if ( context.addInstance( newOven ) ) {
            newOven.getRunContext().addChange(new InstanceCreatedDelta(newOven, KEY_LETTERS));
            return newOven;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Oven create( MicrowaveOven context, UniqueId m_OvenID, UniqueId ref_TubeID, UniqueId ref_LightID, UniqueId ref_BeeperID, UniqueId ref_DoorID, UniqueId ref_TurntableID, TimerHandle m_oven_timer, int m_remaining_cooking_time, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_OvenID, ref_TubeID, ref_LightID, ref_BeeperID, ref_DoorID, ref_TurntableID, m_oven_timer, m_remaining_cooking_time, initialState);
    }

    public static Oven create( MicrowaveOven context, UniqueId instanceId, UniqueId m_OvenID, UniqueId ref_TubeID, UniqueId ref_LightID, UniqueId ref_BeeperID, UniqueId ref_DoorID, UniqueId ref_TurntableID, TimerHandle m_oven_timer, int m_remaining_cooking_time, int initialState ) throws XtumlException {
        Oven newOven = new OvenImpl( context, instanceId, m_OvenID, ref_TubeID, ref_LightID, ref_BeeperID, ref_DoorID, ref_TurntableID, m_oven_timer, m_remaining_cooking_time, initialState );
        if ( context.addInstance( newOven ) ) {
            return newOven;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final OvenStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_OvenID;
    @Override
    public void setOvenID(UniqueId m_OvenID) throws XtumlException {
        checkLiving();
        if (m_OvenID.inequality( this.m_OvenID)) {
            final UniqueId oldValue = this.m_OvenID;
            this.m_OvenID = m_OvenID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_OvenID", oldValue, this.m_OvenID));
        }
    }
    @Override
    public UniqueId getOvenID() throws XtumlException {
        checkLiving();
        return m_OvenID;
    }
    private UniqueId ref_TubeID;
    @Override
    public UniqueId getTubeID() throws XtumlException {
        checkLiving();
        return ref_TubeID;
    }
    @Override
    public void setTubeID(UniqueId ref_TubeID) throws XtumlException {
        checkLiving();
        if (ref_TubeID.inequality( this.ref_TubeID)) {
            final UniqueId oldValue = this.ref_TubeID;
            this.ref_TubeID = ref_TubeID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_TubeID", oldValue, this.ref_TubeID));
        }
    }
    private UniqueId ref_LightID;
    @Override
    public void setLightID(UniqueId ref_LightID) throws XtumlException {
        checkLiving();
        if (ref_LightID.inequality( this.ref_LightID)) {
            final UniqueId oldValue = this.ref_LightID;
            this.ref_LightID = ref_LightID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_LightID", oldValue, this.ref_LightID));
        }
    }
    @Override
    public UniqueId getLightID() throws XtumlException {
        checkLiving();
        return ref_LightID;
    }
    private UniqueId ref_BeeperID;
    @Override
    public UniqueId getBeeperID() throws XtumlException {
        checkLiving();
        return ref_BeeperID;
    }
    @Override
    public void setBeeperID(UniqueId ref_BeeperID) throws XtumlException {
        checkLiving();
        if (ref_BeeperID.inequality( this.ref_BeeperID)) {
            final UniqueId oldValue = this.ref_BeeperID;
            this.ref_BeeperID = ref_BeeperID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_BeeperID", oldValue, this.ref_BeeperID));
        }
    }
    private UniqueId ref_DoorID;
    @Override
    public void setDoorID(UniqueId ref_DoorID) throws XtumlException {
        checkLiving();
        if (ref_DoorID.inequality( this.ref_DoorID)) {
            final UniqueId oldValue = this.ref_DoorID;
            this.ref_DoorID = ref_DoorID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_DoorID", oldValue, this.ref_DoorID));
        }
    }
    @Override
    public UniqueId getDoorID() throws XtumlException {
        checkLiving();
        return ref_DoorID;
    }
    private UniqueId ref_TurntableID;
    @Override
    public void setTurntableID(UniqueId ref_TurntableID) throws XtumlException {
        checkLiving();
        if (ref_TurntableID.inequality( this.ref_TurntableID)) {
            final UniqueId oldValue = this.ref_TurntableID;
            this.ref_TurntableID = ref_TurntableID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_TurntableID", oldValue, this.ref_TurntableID));
        }
    }
    @Override
    public UniqueId getTurntableID() throws XtumlException {
        checkLiving();
        return ref_TurntableID;
    }
    private TimerHandle m_oven_timer;
    @Override
    public void setOven_timer(TimerHandle m_oven_timer) throws XtumlException {
        checkLiving();
        if (m_oven_timer.inequality( this.m_oven_timer)) {
            final TimerHandle oldValue = this.m_oven_timer;
            this.m_oven_timer = m_oven_timer;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_oven_timer", oldValue, this.m_oven_timer));
        }
    }
    @Override
    public TimerHandle getOven_timer() throws XtumlException {
        checkLiving();
        return m_oven_timer;
    }
    private int m_remaining_cooking_time;
    @Override
    public int getRemaining_cooking_time() throws XtumlException {
        checkLiving();
        return m_remaining_cooking_time;
    }
    @Override
    public void setRemaining_cooking_time(int m_remaining_cooking_time) throws XtumlException {
        checkLiving();
        if (m_remaining_cooking_time != this.m_remaining_cooking_time) {
            final int oldValue = this.m_remaining_cooking_time;
            this.m_remaining_cooking_time = m_remaining_cooking_time;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_remaining_cooking_time", oldValue, this.m_remaining_cooking_time));
        }
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getOvenID());
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
    public static class beeping_over extends Event {
        public beeping_over(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 4;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class cancel_cooking extends Event {
        public cancel_cooking(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 5;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class cooking_period extends Event {
        public cooking_period(IRunContext runContext, int populationId,  final int p_period ) {
            super(runContext, populationId, new Object[]{p_period});
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class cooking_period_over extends Event {
        public cooking_period_over(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 2;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class initialise extends Event {
        public initialise(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 3;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class oven_initialised extends Event {
        public oven_initialised(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 6;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class oven_safe extends Event {
        public oven_safe(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }
    public static class start_cooking extends Event {
        public start_cooking(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 7;
        }
        @Override
        public String getClassName() {
            return "Oven";
        }
    }


    // selections
    private MagnetronTube R1_houses_MagnetronTube_inst;
    @Override
    public void setR1_houses_MagnetronTube( MagnetronTube inst ) {
        R1_houses_MagnetronTube_inst = inst;
    }
    @Override
    public MagnetronTube R1_houses_MagnetronTube() throws XtumlException {
        return R1_houses_MagnetronTube_inst;
    }
    private InternalLight R2_is_illuminated_by_InternalLight_inst;
    @Override
    public void setR2_is_illuminated_by_InternalLight( InternalLight inst ) {
        R2_is_illuminated_by_InternalLight_inst = inst;
    }
    @Override
    public InternalLight R2_is_illuminated_by_InternalLight() throws XtumlException {
        return R2_is_illuminated_by_InternalLight_inst;
    }
    private Beeper R3_features_Beeper_inst;
    @Override
    public void setR3_features_Beeper( Beeper inst ) {
        R3_features_Beeper_inst = inst;
    }
    @Override
    public Beeper R3_features_Beeper() throws XtumlException {
        return R3_features_Beeper_inst;
    }
    private Door R4_is_accessed_via_Door_inst;
    @Override
    public void setR4_is_accessed_via_Door( Door inst ) {
        R4_is_accessed_via_Door_inst = inst;
    }
    @Override
    public Door R4_is_accessed_via_Door() throws XtumlException {
        return R4_is_accessed_via_Door_inst;
    }
    private Turntable R5_has_Turntable_inst;
    @Override
    public void setR5_has_Turntable( Turntable inst ) {
        R5_has_Turntable_inst = inst;
    }
    @Override
    public Turntable R5_has_Turntable() throws XtumlException {
        return R5_has_Turntable_inst;
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
    public Oven self() {
        return this;
    }

    @Override
    public Oven oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_OVEN;
    }

}

class EmptyOven extends ModelInstance<Oven,MicrowaveOven> implements Oven {

    // attributes
    public void setOvenID( UniqueId m_OvenID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getOvenID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public UniqueId getTubeID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setTubeID( UniqueId ref_TubeID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public void setLightID( UniqueId ref_LightID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getLightID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public UniqueId getBeeperID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setBeeperID( UniqueId ref_BeeperID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public void setDoorID( UniqueId ref_DoorID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getDoorID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setTurntableID( UniqueId ref_TurntableID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getTurntableID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setOven_timer( TimerHandle m_oven_timer ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public TimerHandle getOven_timer() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public int getRemaining_cooking_time() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setRemaining_cooking_time( int m_remaining_cooking_time ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations


    // selections
    @Override
    public MagnetronTube R1_houses_MagnetronTube() {
        return MagnetronTubeImpl.EMPTY_MAGNETRONTUBE;
    }
    @Override
    public InternalLight R2_is_illuminated_by_InternalLight() {
        return InternalLightImpl.EMPTY_INTERNALLIGHT;
    }
    @Override
    public Beeper R3_features_Beeper() {
        return BeeperImpl.EMPTY_BEEPER;
    }
    @Override
    public Door R4_is_accessed_via_Door() {
        return DoorImpl.EMPTY_DOOR;
    }
    @Override
    public Turntable R5_has_Turntable() {
        return TurntableImpl.EMPTY_TURNTABLE;
    }


    @Override
    public String getKeyLetters() {
        return OvenImpl.KEY_LETTERS;
    }

    @Override
    public Oven self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Oven oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return OvenImpl.EMPTY_OVEN;
    }

}
