case $u_{self.state_name}:
    switch (event.getEventId()) {
    ${transition_row_cells}\
    default:
        throw new IllegalStateException("Unknown event in state");
    }
