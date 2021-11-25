package components.microwaveoven.testsubsystem;

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

public class TestSequences extends DynamicObjectInstance {

    public static final TestSequences EMPTY = new EmptyTestSequences();

    private TestSequences() {
        super();
    }

    public TestSequences(MicrowaveOven domain) {
        this(UniqueId.random(), domain, TestSequencesStateMachine.State.AWAITING_TEST_SEQUENCE_INITIATION,
                UniqueId.random());
    }

    public TestSequences(UniqueId instanceId, MicrowaveOven domain, Enum<?> initialState, UniqueId m_TestSeqID) {
        super(instanceId, domain);
        this.m_TestSeqID = m_TestSeqID;
        this.setStateMachine(new TestSequencesStateMachine(domain, initialState, this));
    }

    // attributes
    private UniqueId m_TestSeqID;

    public UniqueId getTestSeqID() {
        if (isAlive()) {
            return m_TestSeqID;
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }

    public void setTestSeqID(UniqueId m_TestSeqID) {
        if (isAlive()) {
            if (!m_TestSeqID.equals(this.m_TestSeqID)) {
                this.m_TestSeqID = m_TestSeqID;
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }

    @Override
    public MicrowaveOven getDomain() {
        return (MicrowaveOven) super.getDomain();
    }

    @Override
    public TestSequences self() {
        return this;
    }

    public static final class Set extends BaseSet<TestSequences> {

        private static final long serialVersionUID = 1l;

        public Set() {
            super();
        }

        public Set(Collection<? extends TestSequences> c) {
            super(c);
        }

        public Set(Comparator<? super TestSequences> comparator) {
            super(comparator);
        }

        public Set(SortedSet<TestSequences> s) {
            super(s);
        }

        @Override
        public TestSequences any(Predicate<TestSequences> where) {
            return stream().filter(where).findAny().orElse(TestSequences.EMPTY);
        }

        @Override
        public Set where(Predicate<TestSequences> where) {
            return new Set(stream().filter(where).collect(Collectors.toSet()));
        }

    }

    private static final class EmptyTestSequences extends TestSequences {

        // attributes
        @Override
        public UniqueId getTestSeqID() {
            throw new EmptyInstanceException("Cannot get attribute of empty instance.");
        }

        @Override
        public void setTestSeqID(UniqueId m_TestSeqID) {
            throw new EmptyInstanceException("Cannot set attribute of empty instance.");
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