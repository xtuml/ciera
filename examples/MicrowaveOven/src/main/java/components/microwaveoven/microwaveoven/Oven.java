package components.microwaveoven.microwaveoven;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import components.MicrowaveOven;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.Timer;
import io.ciera.runtime.domain.DynamicObjectInstance;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.BaseSet;
import io.ciera.runtime.types.UniqueId;

public class Oven extends DynamicObjectInstance {

    public static final Oven EMPTY = new EmptyOven();

    private Oven() {
        super();
    }

    public Oven(MicrowaveOven domain) {
        this(UniqueId.random(), domain, OvenStateMachine.State.AWAITING_COOKING_REQUEST, UniqueId.random(),
                new UniqueId(), new UniqueId(), new UniqueId(), new UniqueId(), new UniqueId(), null, 0);
    }

    public Oven(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_OvenID, UniqueId ref_TubeID,
            UniqueId ref_LightID, UniqueId ref_BeeperID, UniqueId ref_DoorID, UniqueId ref_TurntableID,
            Timer m_oven_timer, int m_remaining_cooking_time) {
        super(instanceId, domain);
        this.m_OvenID = m_OvenID;
        this.ref_TubeID = ref_TubeID;
        this.ref_LightID = ref_LightID;
        this.ref_BeeperID = ref_BeeperID;
        this.ref_DoorID = ref_DoorID;
        this.ref_TurntableID = ref_TurntableID;
        this.m_oven_timer = m_oven_timer;
        this.m_remaining_cooking_time = m_remaining_cooking_time;
        this.R1_houses_MagnetronTube_inst = MagnetronTube.EMPTY;
        this.R2_is_illuminated_by_InternalLight_inst = InternalLight.EMPTY;
        this.R3_features_Beeper_inst = Beeper.EMPTY;
        this.R4_is_accessed_via_Door_inst = Door.EMPTY;
        this.R5_has_Turntable_inst = Turntable.EMPTY;
        this.setStateMachine(new OvenStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_OvenID;

    public UniqueId getOvenID() {
        if (isAlive()) {
            return m_OvenID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setOvenID(UniqueId m_OvenID) {
        if (isAlive()) {
            if (!m_OvenID.equals(this.m_OvenID)) {
                this.m_OvenID = m_OvenID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private UniqueId ref_TubeID;

    public UniqueId getTubeID() {
        if (isAlive()) {
            return ref_TubeID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setTubeID(UniqueId ref_TubeID) {
        if (isAlive()) {
            if (!ref_TubeID.equals(this.ref_TubeID)) {
                this.ref_TubeID = ref_TubeID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private UniqueId ref_LightID;

    public UniqueId getLightID() {
        if (isAlive()) {
            return ref_LightID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setLightID(UniqueId ref_LightID) {
        if (isAlive()) {
            if (!ref_LightID.equals(this.ref_LightID)) {
                this.ref_LightID = ref_LightID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private UniqueId ref_BeeperID;

    public UniqueId getBeeperID() {
        if (isAlive()) {
            return ref_BeeperID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setBeeperID(UniqueId ref_BeeperID) {
        if (isAlive()) {
            if (!ref_BeeperID.equals(this.ref_BeeperID)) {
                this.ref_BeeperID = ref_BeeperID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private UniqueId ref_DoorID;

    public UniqueId getDoorID() {
        if (isAlive()) {
            return ref_DoorID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setDoorID(UniqueId ref_DoorID) {
        if (isAlive()) {
            if (!ref_DoorID.equals(this.ref_DoorID)) {
                this.ref_DoorID = ref_DoorID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private UniqueId ref_TurntableID;

    public UniqueId getTurntableID() {
        if (isAlive()) {
            return ref_TurntableID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setTurntableID(UniqueId ref_TurntableID) {
        if (isAlive()) {
            if (!ref_TurntableID.equals(this.ref_TurntableID)) {
                this.ref_TurntableID = ref_TurntableID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private Timer m_oven_timer;

    public Timer getOven_timer() {
        if (isAlive()) {
            return m_oven_timer;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setOven_timer(Timer m_oven_timer) {
        if (isAlive()) {
            if (!m_oven_timer.equals(this.m_oven_timer)) {
                this.m_oven_timer = m_oven_timer;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private int m_remaining_cooking_time;

    public int getRemaining_cooking_time() {
        if (isAlive()) {
            return m_remaining_cooking_time;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setRemaining_cooking_time(int m_remaining_cooking_time) {
        if (isAlive()) {
            if (m_remaining_cooking_time != this.m_remaining_cooking_time) {
                this.m_remaining_cooking_time = m_remaining_cooking_time;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    // selections
    private MagnetronTube R1_houses_MagnetronTube_inst;

    public void setR1_houses_MagnetronTube(MagnetronTube inst) {
        R1_houses_MagnetronTube_inst = inst;
    }

    public MagnetronTube R1_houses_MagnetronTube() {
        return R1_houses_MagnetronTube_inst;
    }

    private InternalLight R2_is_illuminated_by_InternalLight_inst;

    public void setR2_is_illuminated_by_InternalLight(InternalLight inst) {
        R2_is_illuminated_by_InternalLight_inst = inst;
    }

    public InternalLight R2_is_illuminated_by_InternalLight() {
        return R2_is_illuminated_by_InternalLight_inst;
    }

    private Beeper R3_features_Beeper_inst;

    public void setR3_features_Beeper(Beeper inst) {
        R3_features_Beeper_inst = inst;
    }

    public Beeper R3_features_Beeper() {
        return R3_features_Beeper_inst;
    }

    private Door R4_is_accessed_via_Door_inst;

    public void setR4_is_accessed_via_Door(Door inst) {
        R4_is_accessed_via_Door_inst = inst;
    }

    public Door R4_is_accessed_via_Door() {
        return R4_is_accessed_via_Door_inst;
    }

    private Turntable R5_has_Turntable_inst;

    public void setR5_has_Turntable(Turntable inst) {
        R5_has_Turntable_inst = inst;
    }

    public Turntable R5_has_Turntable() {
        return R5_has_Turntable_inst;
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Oven self() {
        return this;
    }

    public static final class Set extends BaseSet<Oven> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends Oven> c) {
            super(c);
        }

        public Set(Comparator<? super Oven> comparator) {
            super(comparator);
        }

        public Set(SortedSet<Oven> s) {
            super(s);
        }

        // selections
        public MagnetronTube.Set R1_houses_MagnetronTube() {
            return new MagnetronTube.Set(
                    this.stream().map(oven -> oven.R1_houses_MagnetronTube()).collect(Collectors.toSet()));
        }

        public InternalLight.Set R2_is_illuminated_by_InternalLight() {
            return new InternalLight.Set(
                    this.stream().map(oven -> oven.R2_is_illuminated_by_InternalLight()).collect(Collectors.toSet()));
        }

        public Beeper.Set R3_features_Beeper() {
            return new Beeper.Set(this.stream().map(oven -> oven.R3_features_Beeper()).collect(Collectors.toSet()));
        }

        public Door.Set R4_is_accessed_via_Door() {
            return new Door.Set(this.stream().map(oven -> oven.R4_is_accessed_via_Door()).collect(Collectors.toSet()));
        }

        public Turntable.Set R5_has_Turntable() {
            return new Turntable.Set(this.stream().map(oven -> oven.R5_has_Turntable()).collect(Collectors.toSet()));
        }

        @Override
        public Oven any(Predicate<Oven> where) {
            return stream().filter(where).findAny().orElse(Oven.EMPTY);
        }

        @Override
        public Set where(Predicate<Oven> where) {
            return new Set(stream().filter(where).collect(Collectors.toSet()));
        }

    }

    private static final class EmptyOven extends Oven {

        // attributes
        @Override
        public void setOvenID(UniqueId m_OvenID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public UniqueId getOvenID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public UniqueId getTubeID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setTubeID(UniqueId ref_TubeID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public void setLightID(UniqueId ref_LightID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public UniqueId getLightID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public UniqueId getBeeperID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setBeeperID(UniqueId ref_BeeperID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public void setDoorID(UniqueId ref_DoorID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public UniqueId getDoorID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setTurntableID(UniqueId ref_TurntableID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public UniqueId getTurntableID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setOven_timer(Timer m_oven_timer) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public Timer getOven_timer() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public int getRemaining_cooking_time() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setRemaining_cooking_time(int m_remaining_cooking_time) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        // selections
        @Override
        public MagnetronTube R1_houses_MagnetronTube() {
            return MagnetronTube.EMPTY;
        }

        @Override
        public InternalLight R2_is_illuminated_by_InternalLight() {
            return InternalLight.EMPTY;
        }

        @Override
        public Beeper R3_features_Beeper() {
            return Beeper.EMPTY;
        }

        @Override
        public Door R4_is_accessed_via_Door() {
            return Door.EMPTY;
        }

        @Override
        public Turntable R5_has_Turntable() {
            return Turntable.EMPTY;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public UniqueId getInstanceId() {
            throw new EmptyInstanceException("Cannot get instance ID of empty instance.");
        }

        @Override
        public MicrowaveOven getDomain() {
            throw new EmptyInstanceException("Cannot get domain of empty instance.");
        }

        @Override
        public ExecutionContext getContext() {
            throw new EmptyInstanceException("Cannot get execution context of empty instance.");
        }

        @Override
        public Logger getLogger() {
            throw new EmptyInstanceException("Cannot get logger of empty instance.");
        }

    }

}
