case $u_{self.state_name}:
    switch (eventId) {
    ${transition_row_cells}\
    default:
        throw new IllegalStateException("Unknown event in state");
    }
