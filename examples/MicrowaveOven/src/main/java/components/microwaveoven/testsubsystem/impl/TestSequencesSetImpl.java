package components.microwaveoven.testsubsystem.impl;


import components.microwaveoven.testsubsystem.TestSequences;
import components.microwaveoven.testsubsystem.TestSequencesSet;

import io.ciera.runtime.summit.classes.InstanceSet;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.UniqueId;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


public class TestSequencesSetImpl extends InstanceSet<TestSequencesSet,TestSequences> implements TestSequencesSet {

    public TestSequencesSetImpl() {
    }

    public TestSequencesSetImpl(Comparator<? super TestSequences> comp) {
        super(comp);
    }

    // attributes
    @Override
    public void setTestSeqID( UniqueId m_TestSeqID ) throws XtumlException {
        for ( TestSequences testsequences : this ) testsequences.setTestSeqID( m_TestSeqID );
    }


    // selections


    @Override
    public TestSequences nullElement() {
        return TestSequencesImpl.EMPTY_TESTSEQUENCES;
    }

    @Override
    public TestSequencesSet emptySet() {
      return new TestSequencesSetImpl();
    }

    @Override
    public TestSequencesSet emptySet(Comparator<? super TestSequences> comp) {
      return new TestSequencesSetImpl(comp);
    }

    @Override
    public List<TestSequences> elements() {
        return Arrays.asList(toArray(new TestSequences[0]));
    }

}
