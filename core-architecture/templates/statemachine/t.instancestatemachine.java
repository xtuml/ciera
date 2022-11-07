// states
static enum States {
    ${state_declarations}\
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
