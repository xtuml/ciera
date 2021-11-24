package components.microwaveoven.testsubsystem;


import components.MicrowaveOven;

import io.ciera.runtime.summit.classes.IModelInstance;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;


public interface TestSequences extends IModelInstance<TestSequences,MicrowaveOven> {

    // attributes
    public UniqueId getTestSeqID() throws XtumlException;
    public void setTestSeqID( UniqueId m_TestSeqID ) throws XtumlException;


    // operations


    // selections


}
