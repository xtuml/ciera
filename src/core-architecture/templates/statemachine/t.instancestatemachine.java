package ${self.package};

${imports}

public class ${self.name} extends InstanceStateMachine {

    public static enum States {
        ${state_declarations}\
    }

    private static enum Events {
        ${event_declarations}\
    }

    public ${self.name}(${self.comp_name} domain, Enum<?> initialState, ${self.class_name} self) {
        super(domain, initialState, self);
    }

    // state entry actions
    ${state_actions}

    // transition actions
    ${txn_actions}

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        States state = (States) currentState;
        Events eventId = (Events) event.getEventId();
        switch (state) {
        ${transition_table_rows}\
        default:
            throw new IllegalStateException("Unknown state.");
        }
    }

    // events
    ${event_definitions}

    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

    @Override
    public ${self.class_name} self() {
        return (${self.class_name}) super.self();
    }

}
