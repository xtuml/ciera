package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    public ${self.name}( IRunContext runContext ) {
        super( runContext );
${instance_extent_initializers}
${relationship_extent_initializers}
${utility_initializers}
    }

    // domain functions
${functions}

    // relates and unrelates
${relationship_modifiers}

    // instance selections
${instance_selectors}

    // relationship selections
${relationship_selectors}

    // ports
    /*
${ports}
    */

    // utilities
${utilities}

    // component initialization function
    @Override
    public void initialize() throws XtumlException {
${init}
    }

    @Override
    public boolean addInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
${instance_adds}
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance<?,?> instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
${instance_removes}
        return false;
    }

    @Override
    public ${self.name} population() {
        return this;
    }

}
