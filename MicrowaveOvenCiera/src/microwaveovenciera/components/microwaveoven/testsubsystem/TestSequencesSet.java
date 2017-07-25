package microwaveovenciera.components.microwaveoven.testsubsystem;

import ciera.classes.EmptyInstanceSet;
import ciera.classes.InstanceSet;

@SuppressWarnings("serial")
public class TestSequencesSet extends InstanceSet {

    // empty set
    public static final EmptyTestSequencesSet emptyTestSequencesSet = new EmptyTestSequencesSet();

    // selections

    @Override
    public TestSequences getEmptyInstance() {
        return TestSequences.emptyTestSequences;
    }

}

@SuppressWarnings("serial")
class EmptyTestSequencesSet extends TestSequencesSet implements EmptyInstanceSet {
}
