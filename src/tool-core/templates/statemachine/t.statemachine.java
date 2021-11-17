package ${self.package}.impl;

${imports}

public class ${self.name} extends StateMachine<${class_name},${comp_name}> {

${state_declarations}

    private ${class_name} self;

    public ${self.name}(${class_name} self, ${comp_name} context) {
.if ( "" != initial_state )
        this(self, context, ${initial_state});
    }

    public ${self.name}(${class_name} self, ${comp_name} context, int initialState) {
        super(context, initialState);
        this.self = self;
    }
.else
        super(context);
        this.self = self;
    }
.end if

${state_actions}

${txn_actions}

    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
${transition_table_rows}
        };
    }

    @Override
    public ${class_name} self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "${class_name}";
    }

}
