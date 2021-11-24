package components.microwaveoven.testsubsystem.impl;


import components.MicrowaveOven;
import components.microwaveoven.testsubsystem.TestSequences;

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


public class TestSequencesImpl extends ModelInstance<TestSequences,MicrowaveOven> implements TestSequences {

    public static final String KEY_LETTERS = "MO_TS";
    public static final TestSequences EMPTY_TESTSEQUENCES = new EmptyTestSequences();

    private MicrowaveOven context;

    // constructors
    private TestSequencesImpl( MicrowaveOven context ) {
        this.context = context;
        m_TestSeqID = UniqueId.random();
        statemachine = new TestSequencesStateMachine(this, context());
    }

    private TestSequencesImpl( MicrowaveOven context, UniqueId instanceId, UniqueId m_TestSeqID, int initialState ) {
        super(instanceId);
        this.context = context;
        this.m_TestSeqID = m_TestSeqID;
        statemachine = new TestSequencesStateMachine(this, context(), initialState);
    }

    public static TestSequences create( MicrowaveOven context ) throws XtumlException {
        TestSequences newTestSequences = new TestSequencesImpl( context );
        if ( context.addInstance( newTestSequences ) ) {
            newTestSequences.getRunContext().addChange(new InstanceCreatedDelta(newTestSequences, KEY_LETTERS));
            return newTestSequences;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static TestSequences create( MicrowaveOven context, UniqueId m_TestSeqID, int initialState ) throws XtumlException {
        return create(context, UniqueId.random(), m_TestSeqID, initialState);
    }

    public static TestSequences create( MicrowaveOven context, UniqueId instanceId, UniqueId m_TestSeqID, int initialState ) throws XtumlException {
        TestSequences newTestSequences = new TestSequencesImpl( context, instanceId, m_TestSeqID, initialState );
        if ( context.addInstance( newTestSequences ) ) {
            return newTestSequences;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    private final TestSequencesStateMachine statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.transition(event);
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }


    // attributes
    private UniqueId m_TestSeqID;
    @Override
    public UniqueId getTestSeqID() throws XtumlException {
        checkLiving();
        return m_TestSeqID;
    }
    @Override
    public void setTestSeqID(UniqueId m_TestSeqID) throws XtumlException {
        checkLiving();
        if (m_TestSeqID.inequality( this.m_TestSeqID)) {
            final UniqueId oldValue = this.m_TestSeqID;
            this.m_TestSeqID = m_TestSeqID;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_TestSeqID", oldValue, this.m_TestSeqID));
        }
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getTestSeqID());
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
    public static class initialize extends Event {
        public initialize(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 3;
        }
        @Override
        public String getClassName() {
            return "TestSequences";
        }
    }
    public static class perform_test_seq_1 extends Event {
        public perform_test_seq_1(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 0;
        }
        @Override
        public String getClassName() {
            return "TestSequences";
        }
    }
    public static class perform_test_seq_2 extends Event {
        public perform_test_seq_2(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 2;
        }
        @Override
        public String getClassName() {
            return "TestSequences";
        }
    }
    public static class test_seq_complete extends Event {
        public test_seq_complete(IRunContext runContext, int populationId) {
            super(runContext, populationId);
        }
        @Override
        public int getId() {
            return 1;
        }
        @Override
        public String getClassName() {
            return "TestSequences";
        }
    }


    // selections


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
    public TestSequences self() {
        return this;
    }

    @Override
    public TestSequences oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_TESTSEQUENCES;
    }

}

class EmptyTestSequences extends ModelInstance<TestSequences,MicrowaveOven> implements TestSequences {

    // attributes
    public UniqueId getTestSeqID() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setTestSeqID( UniqueId m_TestSeqID ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations


    // selections


    @Override
    public String getKeyLetters() {
        return TestSequencesImpl.KEY_LETTERS;
    }

    @Override
    public TestSequences self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public TestSequences oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return TestSequencesImpl.EMPTY_TESTSEQUENCES;
    }

}
