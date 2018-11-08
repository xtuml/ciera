package ${self.package}.impl;

${imports}

public class ${self.name} extends StateMachine<${self.class_name},${self.comp_name}> {

${state_declarations}

    private ${self.class_name} self;

    public ${self.name}(${self.class_name} self, ${self.comp_name} context) {
.if ( "" != initial_state )
        super(context, ${initial_state});
.else
        super(context);
.end if
        this.self = self;
    }

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
