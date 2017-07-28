package microwaveovenciera.components.microwaveoven.testsubsystem;

import java.util.UUID;

import ciera.classes.EmptyInstance;
import ciera.classes.ModelInstance;
import ciera.exceptions.XtumlException;
import microwaveovenciera.components.microwaveoven.testsubsystem.testsequences.instancestatemachine.TestSequencesInstanceStateMachine;

public class TestSequences extends ModelInstance {
    
    private static final int classNumber = 100;
    private static final String keyLetters = "MO_TS";
    
    // empty instance
    public static final TestSequences emptyTestSequences = new EmptyTestSequences();
    
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
    public int getClassNumber() {
        return classNumber;
    }
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

}

class EmptyTestSequences extends TestSequences implements EmptyInstance {
}