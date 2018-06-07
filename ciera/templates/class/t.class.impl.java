package ${self.package}.impl;

${imports}

public class ${self.name}Impl extends ${self.extends} implements ${self.name} {

    public static final String KEY_LETTERS = "${self.key_letters}";
    public static final ${self.name} EMPTY_$u_{self.name} = new Empty${self.name}();

    private ${self.comp_name} context;

    // constructors
    private ${self.name}Impl( ${self.comp_name} context ) {
        this.context = context;
${attribute_initializers}    }
.if ( "" != attribute_list )
    private ${self.name}Impl( ${self.comp_name} context${attribute_list} ) {
        this.context = context;
${attribute_initializers2}    }
.end if

    public static ${self.name} create( ${self.comp_name} context ) throws XtumlException {
        ${self.name} new${self.name} = new ${self.name}Impl( context );
        if ( context.addInstance( new${self.name} ) ) return new${self.name};
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }
.if ( "" != attribute_list )

    public static ${self.name} create( ${self.comp_name} context${attribute_list} ) throws XtumlException {
        ${self.name} new${self.name} = new ${self.name}Impl( context${attribute_invocation_list} );
        if ( context.addInstance( new${self.name} ) ) return new${self.name};
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }
.end if

    // attributes
${attributes}

    // operations
${operations}

    // selections
${selectors}

    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public ${self.comp_name} context() {
        return context;
    }

    @Override
    public String getKeyLetters() {
        return KEY_LETTERS;
    }

    @Override
    public ${self.name} defaultValue() {
        return EMPTY_$u_{self.name};
    }

}

class Empty${self.name} extends ${self.extends} implements ${self.name} {

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
    public ${self.name} defaultValue() {
        return ${self.name}Impl.EMPTY_$u_{self.name};
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

}
