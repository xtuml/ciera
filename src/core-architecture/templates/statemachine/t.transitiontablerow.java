case $u_{self.state_name}:
    switch (eventId) {
    ${transition_row_cells}\
    default:
        throw new TransitionException(currentState, event, "Unknown event in state");
    }
