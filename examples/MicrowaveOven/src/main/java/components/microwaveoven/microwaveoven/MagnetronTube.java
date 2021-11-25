package components.microwaveoven.microwaveoven;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import components.MicrowaveOven;
import components.microwaveoven.datatypes.Tube_Wattage;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.domain.DynamicObjectInstance;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.BaseSet;
import io.ciera.runtime.types.UniqueId;

public class MagnetronTube extends DynamicObjectInstance {

    public static final MagnetronTube EMPTY = new EmptyMagnetronTube();

    public MagnetronTube() {
        super();
    }

    public MagnetronTube(MicrowaveOven domain) {
        this(UniqueId.random(), domain, MagnetronTubeStateMachine.State.OUTPUT_POWER_STABLE_AND_OFF, UniqueId.random(),
                Tube_Wattage.UNINITIALIZED);
    }

    public MagnetronTube(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_TubeID,
            Tube_Wattage m_current_power_output) {
        super(instanceId, domain);
        this.m_TubeID = m_TubeID;
        this.m_current_power_output = m_current_power_output;
        this.R1_is_housed_in_Oven_inst = Oven.EMPTY;
        this.setStateMachine(new MagnetronTubeStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_TubeID;

    public UniqueId getTubeID() {
        if (isAlive()) {
            return m_TubeID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setTubeID(UniqueId m_TubeID) {
        if (isAlive()) {
            if (!m_TubeID.equals(this.m_TubeID)) {
                this.m_TubeID = m_TubeID;
                if (!R1_is_housed_in_Oven().isEmpty()) {
                    R1_is_housed_in_Oven().setTubeID(m_TubeID);
                }
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private Tube_Wattage m_current_power_output;

    public Tube_Wattage getCurrent_power_output() {
        if (isAlive()) {
            return m_current_power_output;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setCurrent_power_output(Tube_Wattage m_current_power_output) {
        if (isAlive()) {
            if (!m_current_power_output.equals(this.m_current_power_output)) {
                this.m_current_power_output = m_current_power_output;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    // selections
    private Oven R1_is_housed_in_Oven_inst;

    public void setR1_is_housed_in_Oven(Oven inst) {
        R1_is_housed_in_Oven_inst = inst;
    }

    public Oven R1_is_housed_in_Oven() {
        return R1_is_housed_in_Oven_inst;
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public MagnetronTube self() {
        return this;
    }

    public static final class Set extends BaseSet<MagnetronTube> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends MagnetronTube> c) {
            super(c);
        }

        public Set(Comparator<? super MagnetronTube> comparator) {
            super(comparator);
        }

        public Set(SortedSet<MagnetronTube> s) {
            super(s);
        }

        // selections
        public Oven.Set R1_is_housed_in_Oven() {
            return new Oven.Set(this.stream().map(magnetrontube -> magnetrontube.R1_is_housed_in_Oven())
                    .collect(Collectors.toSet()));
        }

        @Override
        public MagnetronTube any(Predicate<MagnetronTube> where) {
            return stream().filter(where).findAny().orElse(MagnetronTube.EMPTY);
        }

        @Override
        public Set where(Predicate<MagnetronTube> where) {
            return new Set(stream().filter(where).collect(Collectors.toSet()));
        }

    }

    private static final class EmptyMagnetronTube extends MagnetronTube {

        // attributes
        @Override
        public UniqueId getTubeID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setTubeID(UniqueId m_TubeID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public void setCurrent_power_output(Tube_Wattage m_current_power_output) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public Tube_Wattage getCurrent_power_output() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        // selections
        @Override
        public Oven R1_is_housed_in_Oven() {
            return Oven.EMPTY;
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