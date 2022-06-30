package ${self.package};

${imports}

class ${self.name} extends InstanceStateMachine implements StateMachine, InstanceActionHome {

    private static volatile ${self.name} instance;

    static enum States {
        ${state_declarations}\
    }

    ${self.name}(${self.comp_name} domain, ${self.class_name} self) {
        super(domain, self);
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
    public ${self.comp_name} getDomain() {
        return (${self.comp_name}) super.getDomain();
    }

    @Override
    public ${self.class_name} self() {
        return (${self.class_name}) super.self();
    }

    static StateMachine getInstance(${self.comp_name} domain, ${self.class_name} self) {
        if (instance == null || !instance.getDomain().equals(domain) || !instance.self().equals(self)) {
            instance = new ${self.name}(domain, self);
        }
        return instance;
    }

}
