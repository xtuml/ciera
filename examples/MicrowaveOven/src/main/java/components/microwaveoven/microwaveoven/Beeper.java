package components.microwaveoven.microwaveoven;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import components.MicrowaveOven;
import io.ciera.runtime.application.ExecutionContext;
import io.ciera.runtime.application.Logger;
import io.ciera.runtime.application.Timer;
import io.ciera.runtime.domain.DynamicObjectInstance;
import io.ciera.runtime.exceptions.InstancePopulationException;
import io.ciera.runtime.exceptions.EmptyInstanceException;
import io.ciera.runtime.types.UniqueId;

public class Beeper extends DynamicObjectInstance {

    public static final Beeper EMPTY = new EmptyBeeper();

    // constructors
    private Beeper() {
        super();
    }

    public Beeper(MicrowaveOven domain) {
        this(UniqueId.random(), domain, BeeperStateMachine.States.AWAITING_BEEPER_REQUEST, UniqueId.random(), null, 0);
    }

    public Beeper(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_BeeperID,
            Timer m_beeper_timer, int m_beep_count) {
        super(instanceId, domain);
        this.m_BeeperID = m_BeeperID;
        this.m_beeper_timer = m_beeper_timer;
        this.m_beep_count = m_beep_count;
        this.R3_is_located_in_Oven_inst = Oven.EMPTY;
        this.setStateMachine(new BeeperStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_BeeperID;

    public UniqueId getBeeperID() {
        if (isAlive()) {
            return m_BeeperID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setBeeperID(UniqueId m_BeeperID) {
        if (isAlive()) {
            if (!m_BeeperID.equals(this.m_BeeperID)) {
                this.m_BeeperID = m_BeeperID;
                if (!R3_is_located_in_Oven().isEmpty()) {
                    R3_is_located_in_Oven().setBeeperID(m_BeeperID);
                }
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private Timer m_beeper_timer;

    public Timer getBeeper_timer() {
        if (isAlive()) {
            return m_beeper_timer;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setBeeper_timer(Timer m_beeper_timer) {
        if (isAlive()) {
            if (!m_beeper_timer.equals(this.m_beeper_timer)) {
                this.m_beeper_timer = m_beeper_timer;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    private int m_beep_count;

    public int getBeep_count() {
        if (isAlive()) {
            return m_beep_count;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setBeep_count(int m_beep_count) {
        if (isAlive()) {
            if (m_beep_count != this.m_beep_count) {
                this.m_beep_count = m_beep_count;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    // selections
    private Oven R3_is_located_in_Oven_inst;

    public void setR3_is_located_in_Oven(Oven inst) {
        R3_is_located_in_Oven_inst = inst;
    }

    public Oven R3_is_located_in_Oven() {
        return R3_is_located_in_Oven_inst;
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    public static final class Set extends TreeSet<Beeper> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends Beeper> c) {
            super(c);
        }

        public Set(Comparator<? super Beeper> comparator) {
            super(comparator);
        }

        public Set(SortedSet<Beeper> s) {
            super(s);
        }

        // selections
        public Oven.Set R3_is_located_in_Oven() {
            return new Oven.Set(
                    this.stream().map(beeper -> beeper.R3_is_located_in_Oven()).collect(Collectors.toSet()));
        }

    }

    private static final class EmptyBeeper extends Beeper {

        // attributes
        @Override
        public UniqueId getBeeperID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setBeeperID(UniqueId m_BeeperID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public Timer getBeeper_timer() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setBeeper_timer(Timer m_beeper_timer) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        @Override
        public int getBeep_count() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setBeep_count(int m_beep_count) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
        }

        // operations

        // selections
        @Override
        public Oven R3_is_located_in_Oven() {
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