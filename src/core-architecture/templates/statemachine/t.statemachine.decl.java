    private final ${self.name} statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        if (isAlive()) {
            statemachine.transition(event);
        }
    }

    @Override
    public int getCurrentState() {
        return statemachine.getCurrentState();
    }
