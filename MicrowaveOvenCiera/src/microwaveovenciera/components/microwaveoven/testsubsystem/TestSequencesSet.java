package microwaveovenciera.components.microwaveoven.testsubsystem;

import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;
import ciera.classes.Where;

@SuppressWarnings("serial")
public class TestSequencesSet extends InstanceSet {

    // empty set
    public static final TestSequencesSet emptyTestSequencesSet = new EmptyTestSequencesSet();

    // selections
    public TestSequences selectAnyMO_TSFromInstances( Where condition ) {
        return (TestSequences)selectAny( condition );
    }
    
    public TestSequencesSet selectManyMO_TSsFromInstances( Where condition ) {
        return (TestSequencesSet)selectMany( condition );
    }

    @Override
    public TestSequences getEmptyInstance() {
        return TestSequences.emptyTestSequences;
    }

}

@SuppressWarnings("serial")
class EmptyTestSequencesSet extends TestSequencesSet implements EmptyInstanceSet {

    // selections
    @Override
    public TestSequences selectAnyMO_TSFromInstances( Where condition ) {
        return TestSequences.emptyTestSequences;
    }
    
    @Override
    public TestSequencesSet selectManyMO_TSsFromInstances( Where condition ) {
        return TestSequencesSet.emptyTestSequencesSet;
    }

}
