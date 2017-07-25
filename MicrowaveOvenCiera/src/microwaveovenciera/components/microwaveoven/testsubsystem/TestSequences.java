package microwaveovenciera.components.microwaveoven.testsubsystem;

import java.util.UUID;

import ciera.application.XtumlApplication;
import ciera.application.ApplicationThread;
import ciera.classes.EmptyInstance;
import ciera.classes.ModelInstance;
import ciera.exceptions.XtumlException;
import microwaveovenciera.components.microwaveoven.testsubsystem.testsequences.instancestatemachine.TestSequencesInstanceStateMachine;

public class TestSequences extends ModelInstance {
    
    private static final int classId = 100;
    private static final String keyLetters = "MO_TS";
    
    // empty instance
    public static final EmptyTestSequences emptyTestSequences = new EmptyTestSequences();
    
    // class attributes
    private UUID m_TestSeqID;
    
    // associations
    
    // constructors
    public TestSequences() {
        super( new TestSequencesInstanceStateMachine() );
        m_TestSeqID = UUID.randomUUID();
    }
    
    // attribute accessors
    public UUID getM_TestSeqID() throws XtumlException {
        checkLiving();
        return m_TestSeqID;
    }

    // selections
    
    // relates
    
    // unrelates

    @Override
    public int getClassId() {
        return classId;
    }
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

    @Override
    public ApplicationThread getDefaultThread() {
        return XtumlApplication.app.getDefaultThread( TestSequences.class );
    }

}

class EmptyTestSequences extends TestSequences implements EmptyInstance {
}