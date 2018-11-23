package ${self.package}.impl;

${imports}

public class ${self.name} extends StateMachine<${self.class_name},${self.comp_name}> {

${state_declarations}

    private ${self.class_name} self;

    public ${self.name}(${self.class_name} self, ${self.comp_name} context) {
.if ( "" != initial_state )
        this(self, context, ${initial_state});
    }

    public ${self.name}(${self.class_name} self, ${self.comp_name} context, int initialState) {
        super(context, initialState);
        this.self = self;
    }
.else
        super(context);
        this.self = self;
    }
.end if

${state_actions}

    @Override
    public ITransition[][] getStateEventMatrix() {
        return new ITransition[][] {
${transition_table_rows}
        };
    }

    @Override
    public ${self.class_name} self() {
        return self;
    }

    @Override
    public String getClassName() {
        return "${self.class_name}";
    }

}
