    private final ${self.name} statemachine;
    
    @Override
    public void accept(IEvent event) throws XtumlException {
        statemachine.accept(event);
    }
