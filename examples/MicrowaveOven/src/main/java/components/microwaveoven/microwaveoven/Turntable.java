package components.microwaveoven.microwaveoven;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import components.MicrowaveOven;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.domain.DynamicObjectInstance;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.UniqueId;

public class Turntable extends DynamicObjectInstance {

    public static final Turntable EMPTY = new EmptyTurntable();

    private Turntable() {
        super();
    }

    public Turntable(MicrowaveOven domain) {
        this(UniqueId.random(), domain, TurntableStateMachine.States.STATIONARY, UniqueId.random());
    }

    public Turntable(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_TurntableID) {
        super(instanceId, domain);
        this.m_TurntableID = m_TurntableID;
        this.R5_occupies_Oven_inst = Oven.EMPTY;
        this.setStateMachine(new TurntableStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_TurntableID;

    public UniqueId getTurntableID() {
        if (isAlive()) {
            return m_TurntableID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setTurntableID(UniqueId m_TurntableID) {
        if (isAlive()) {
            if (!m_TurntableID.equals(this.m_TurntableID)) {
                this.m_TurntableID = m_TurntableID;
                if (!R5_occupies_Oven().isEmpty()) {
                    R5_occupies_Oven().setTurntableID(m_TurntableID);
                }
            }
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    // selections
    private Oven R5_occupies_Oven_inst;

    public void setR5_occupies_Oven(Oven inst) {
        R5_occupies_Oven_inst = inst;
    }

    public Oven R5_occupies_Oven() {
        return R5_occupies_Oven_inst;
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Turntable self() {
        return this;
    }

    public static final class Set extends TreeSet<Turntable> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends Turntable> c) {
            super(c);
        }

        public Set(Comparator<? super Turntable> comparator) {
            super(comparator);
        }

        public Set(SortedSet<Turntable> s) {
            super(s);
        }

        // selections
        public Oven.Set R5_occupies_Oven() {
            return new Oven.Set(
                    this.stream().map(turntable -> turntable.R5_occupies_Oven()).collect(Collectors.toSet()));
        }

    }

    private static final class EmptyTurntable extends Turntable {

        // attributes
        @Override
        public void setTurntableID(UniqueId m_TurntableID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public UniqueId getTurntableID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        // selections
        @Override
        public Oven R5_occupies_Oven() {
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
