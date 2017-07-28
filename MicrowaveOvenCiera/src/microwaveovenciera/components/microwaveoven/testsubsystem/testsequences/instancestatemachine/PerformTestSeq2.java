package microwaveovenciera.components.microwaveoven.testsubsystem.testsequences.instancestatemachine;

import ciera.statemachine.Event;
import ciera.statemachine.EventTarget;

public class PerformTestSeq2 extends Event {
    
    private static final int eventId = 2;
    private static final int eventNumber = 3;
    private static final int classNumber = 100;
    private static final String eventName = "Perform Test Seq 2";

    public PerformTestSeq2() {
    }

    public PerformTestSeq2( EventTarget t, boolean ts ) {
        super( t, ts );
    }

    @Override
    public int getEventId() {
        return eventId;
    }

    @Override
    public int getEventNumber() {
        return eventNumber;
    }

    @Override
    public int getClassNumber() {
        return classNumber;
    }

    @Override
    public String getEventName() {
        return eventName;
    }

}
