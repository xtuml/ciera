package ${self.package}.impl;

${imports}

public class ${self.name} extends StateMachine<${self.class_name},${self.comp_name}> {

${state_declarations}

    private ${self.class_name} self;

    public ${self.name}(${self.class_name} self, ${self.comp_name} context) {
        super(context);
.if ( "" != initial_state )
        setCurrentState(${initial_state});
.end if
        this.self = self;
    }

${state_actions}


    @Override
    public void transition(IEvent event) throws XtumlException {
        switch (getCurrentState()) {
${transition_table_rows}
        default:
            throw new StateMachineException("Instance in invalid state");
        }
    }

    @Override
    public void accept(IEvent event) throws XtumlException {
        transition(event);
    }

    @Override
    public Analog_Input self() {
        return self;
    }

}
