package ${self.package};

${imports}

class ${self.name} extends AbstractStateMachine implements StateMachine {

    private static volatile ${self.name} instance;

    static enum States {
        ${state_declarations}\
    }

    ${self.name}(${self.comp_name} domain) {
        super("${self.class_name} [CSM]", domain);
    }

    // state entry actions
    ${state_actions}

    // transition actions
    ${txn_actions}

    @Override
    public Supplier<Enum<?>> getTransition(Enum<?> currentState, Event event) {
        switch ((States) currentState) {
        ${transition_table_rows}\
        default:
            throw new IllegalStateException("Unknown state");
        }
    }

    @Override
    public void consumeEvent(Event event) {
        try {
            executeTransition(event, newState -> ${self.class_name}.currentState = newState);
        } catch (RuntimeException e) {
            throw new StateMachineActionException("Exception in state machine action", e, this, getCurrentState(), this, event);
        }
    }

    @Override
    public Enum<?> getCurrentState() {
        return ${self.class_name}.currentState;
    }

    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

    static StateMachine getInstance(${self.comp_name} domain) {
        if (instance == null || !instance.getDomain().equals(domain)) {
            instance = new ${self.name}(domain);
        }
        return instance;
    }

}
