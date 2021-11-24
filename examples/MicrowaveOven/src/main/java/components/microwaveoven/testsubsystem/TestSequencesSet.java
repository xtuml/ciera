package components.microwaveoven.testsubsystem;


import io.ciera.runtime.summit.classes.IInstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface TestSequencesSet extends IInstanceSet<TestSequencesSet,TestSequences> {

    // attributes
    public void setTestSeqID( UniqueId m_TestSeqID ) throws XtumlException;


    // selections


}
