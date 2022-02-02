package ${self.package};

${imports}

public class ${self.name} extends InstanceStateMachine implements StateMachine, InstanceActionHome {

    public static enum States {
        ${state_declarations}\
    }

    public ${self.name}(${self.comp_name} domain, ${self.class_name} self) {
        super(domain, self);
    }

    // state entry actions
    ${state_actions}

    // transition actions
    ${txn_actions}

    @Override
    public TransitionRule getTransition(Enum<?> currentState, Event event) {
        switch ((States) currentState) {
        ${transition_table_rows}\
        default:
            throw new IllegalStateException("Unknown state");
        }
    }

    @Override
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

    @Override
    public ${self.class_name} self() {
        return (${self.class_name}) super.self();
    }

}
