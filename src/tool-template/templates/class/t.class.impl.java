package ${self.package}.impl;

${imports}

public class ${self.name}Impl extends ${extends_str} implements ${self.name} {

    public static final String KEY_LETTERS = "${key_letters}";
    public static final ${self.name} EMPTY_$u_{self.name} = new Empty${self.name}();

    private ${comp_name} context;

    // constructors
    private ${self.name}Impl( ${comp_name} context ) {
        this.context = context;
${attribute_initializers}${relationship_initializers}${state_machine_initializer}    }

.if (attribute_list != "" )
    private ${self.name}Impl( ${comp_name} context${attribute_list}${initial_state} ) {
        this.context = context;
${attribute_initializers2}${relationship_initializers}${state_machine_initializer2}    }
.end if

    private ${self.name}Impl( ${comp_name} context, UniqueId instanceId${attribute_list}${initial_state} ) {
        super(instanceId);
        this.context = context;
${attribute_initializers2}${relationship_initializers}${state_machine_initializer2}    }

    public static ${self.name} create( ${comp_name} context ) throws XtumlException {
        ${self.name} new${self.name} = new ${self.name}Impl( context );
        if ( context.addInstance( new${self.name} ) ) {
            new${self.name}.getRunContext().addChange(new InstanceCreatedDelta(new${self.name}, KEY_LETTERS));
            return new${self.name};
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

.if (attribute_list != "" )
    public static ${self.name} create( ${comp_name} context${attribute_list}${initial_state} ) throws XtumlException {
        ${self.name} new${self.name} = new ${self.name}Impl( context${attribute_invocation_list}${initial_state2} );
        if ( context.addInstance( new${self.name} ) ) {
            return new${self.name};
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }
.end if

    public static ${self.name} create( ${comp_name} context, UniqueId instanceId${attribute_list}${initial_state} ) throws XtumlException {
        ${self.name} new${self.name} = new ${self.name}Impl( context, instanceId${attribute_invocation_list}${initial_state2} );
        if ( context.addInstance( new${self.name} ) ) {
            return new${self.name};
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

${state_machine_decl}

    // attributes
${attributes}

    // instance identifiers
.if ( "" != id1_attributes )
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(${id1_attributes});
        }
        catch ( XtumlException e ) {
            getRunContext().getLog().error(e);
            System.exit(1);
            return null;
        }
    }
.end if
.if ( "" != id2_attributes )
    @Override
    public IInstanceIdentifier getId2() {
        try {
            return new InstanceIdentifier(${id2_attributes});
        }
        catch ( XtumlException e ) {
            getRunContext().getLog().error(e);
            System.exit(1);
            return null;
        }
    }
.end if
.if ( "" != id3_attributes )
    @Override
    public IInstanceIdentifier getId3() {
        try {
            return new InstanceIdentifier(${id3_attributes});
        }
        catch ( XtumlException e ) {
            getRunContext().getLog().error(e);
            System.exit(1);
            return null;
        }
    }
.end if

    // operations
${operations}

    // static operations
${static_operations}

    // events
${state_machine_events}

    // selections
${selectors}

    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public ${comp_name} context() {
        return context;
    }

    @Override
    public String getKeyLetters() {
        return KEY_LETTERS;
    }

    @Override
    public ${self.name} self() {
        return this;
    }

    @Override
    public ${self.name} oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_$u_{self.name};
    }

}

class Empty${self.name} extends ${extends_str} implements ${self.name} {

    // attributes
${empty_attributes}

    // operations
${empty_operations}

    // selections
${empty_selectors}

    @Override
    public String getKeyLetters() {
        return ${self.name}Impl.KEY_LETTERS;
    }

    @Override
    public ${self.name} self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public ${self.name} oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return ${self.name}Impl.EMPTY_$u_{self.name};
    }

}
