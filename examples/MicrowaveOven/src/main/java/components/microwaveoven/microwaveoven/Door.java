package components.microwaveoven.microwaveoven;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import components.MicrowaveOven;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.domain.DynamicObjectInstance;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.types.BaseSet;
import io.ciera.runtime.types.UniqueId;

public class Door extends DynamicObjectInstance {

    public static final Door EMPTY = new EmptyDoor();

    // constructors
    private Door() {
        super();
    }

    public Door(MicrowaveOven domain) {
        this(UniqueId.random(), domain, DoorStateMachine.States.OPEN, UniqueId.random(), false);
    }

    public Door(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_DoorID,
            boolean m_is_secure) {
        super(instanceId, domain);
        this.m_DoorID = m_DoorID;
        this.m_is_secure = m_is_secure;
        this.R4_provides_access_to_Oven_inst = Oven.EMPTY;
        this.setStateMachine(new DoorStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_DoorID;

    public UniqueId getDoorID() {
        if (isAlive()) {
            return m_DoorID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setDoorID(UniqueId m_DoorID) {
        if (isAlive()) {
            if (!m_DoorID.equals(this.m_DoorID)) {
                this.m_DoorID = m_DoorID;
                if (!R4_provides_access_to_Oven().isEmpty()) {
                    R4_provides_access_to_Oven().setDoorID(m_DoorID);
                }
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private boolean m_is_secure;

    public boolean getIs_secure() {
        if (isAlive()) {
            return m_is_secure;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setIs_secure(boolean m_is_secure) {
        if (isAlive()) {
            if (m_is_secure != this.m_is_secure) {
                this.m_is_secure = m_is_secure;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    // operations

    // static operations

    // selections
    private Oven R4_provides_access_to_Oven_inst;

    public void setR4_provides_access_to_Oven(Oven inst) {
        R4_provides_access_to_Oven_inst = inst;
    }

    public Oven R4_provides_access_to_Oven() {
        return R4_provides_access_to_Oven_inst;
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public Door self() {
        return this;
    }

    public static final class Set extends BaseSet<Door> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends Door> c) {
            super(c);
        }

        public Set(Comparator<? super Door> comparator) {
            super(comparator);
        }

        public Set(SortedSet<Door> s) {
            super(s);
        }

        // selections
        public Oven.Set R4_provides_access_to_Oven() {
            return new Oven.Set(
                    this.stream().map(door -> door.R4_provides_access_to_Oven()).collect(Collectors.toSet()));
        }

        @Override
        public Door any(Predicate<Door> where) {
            return stream().filter(where).findAny().orElse(Door.EMPTY);
        }

        @Override
        public Set where(Predicate<Door> where) {
            return new Set(stream().filter(where).collect(Collectors.toSet()));
        }

    }

    private static final class EmptyDoor extends Door {

        // attributes
        @Override
        public void setDoorID(UniqueId m_DoorID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public UniqueId getDoorID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public boolean getIs_secure() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setIs_secure(boolean m_is_secure) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        // selections
        @Override
        public Oven R4_provides_access_to_Oven() {
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