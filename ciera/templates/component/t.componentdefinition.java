package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {
    
    private IRunContext runContext;

${instance_extents}

    public ${self.name}( IRunContext runContext ) {
        this.runContext = runContext;
${instance_extent_initializers}
    }

    // domain functions
${functions}

    // relates
${relates}

    // unrelates
${unrelates}
    
    // selections
${selectors}

    // ports
    /*
${ports}
    */

    // component initialization function
    @Override
    public void initialize() throws XtumlException {
${init}
    }

    @Override
    public boolean addInstance( IModelInstance instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot add empty instance to population." );
${instance_adds}
        return false;
    }

    @Override
    public boolean removeInstance( IModelInstance instance ) throws XtumlException {
        if ( null == instance ) throw new BadArgumentException( "Null instance passed." );
        if ( instance instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot remove empty instance from population." );
${instance_removes}
        return false;
    }

    @Override
    public Map<Integer, IRelationshipSet> initializeRelationshipSets() {
        Map<Integer, IRelationshipSet> relationships  = new HashMap<>();
${relationship_sets}\
        return relationships;
    }

    @Override
      public IRunContext getRunContext() {
            return runContext;
      }

      @Override
      public ${self.name} population() {
            return this;
      }

${relationships}

}
