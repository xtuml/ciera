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

public class InternalLight extends DynamicObjectInstance {

    public static final InternalLight EMPTY = new EmptyInternalLight();

    private InternalLight() {
        super();
    }

    public InternalLight(MicrowaveOven domain) {
        this(UniqueId.random(), domain, InternalLightStateMachine.State.OFF, UniqueId.random());
    }

    public InternalLight(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_LightID) {
        super(instanceId, domain);
        this.m_LightID = m_LightID;
        this.R2_illuminates_Oven_inst = Oven.EMPTY;
        this.setStateMachine(new InternalLightStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_LightID;

    public UniqueId getLightID() {
        if (isAlive()) {
            return m_LightID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setLightID(UniqueId m_LightID) {
        if (isAlive()) {
            if (!m_LightID.equals(this.m_LightID)) {
                this.m_LightID = m_LightID;
                if (!R2_illuminates_Oven().isEmpty()) {
                    R2_illuminates_Oven().setLightID(m_LightID);
                }
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    // selections
    private Oven R2_illuminates_Oven_inst;

    public void setR2_illuminates_Oven(Oven inst) {
        R2_illuminates_Oven_inst = inst;
    }

    public Oven R2_illuminates_Oven() {
        return R2_illuminates_Oven_inst;
    }

    @Override
    public InternalLight self() {
        return this;
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    public static final class Set extends TreeSet<InternalLight> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends InternalLight> c) {
            super(c);
        }

        public Set(Comparator<? super InternalLight> comparator) {
            super(comparator);
        }

        public Set(SortedSet<InternalLight> s) {
            super(s);
        }

        // selections
        public Oven.Set R2_illuminates_Oven() {
            return new Oven.Set(this.stream().map(internallight -> internallight.R2_illuminates_Oven())
                    .collect(Collectors.toSet()));
        }

    }

    private static final class EmptyInternalLight extends InternalLight {

        // attributes
        @Override
        public UniqueId getLightID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setLightID(UniqueId m_LightID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public Oven R2_illuminates_Oven() {
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