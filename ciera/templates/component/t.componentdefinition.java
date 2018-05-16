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

    // creates
${creates}

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
	  public ${self.name} getPopulationContext() {
		    return this;
	  }

${relationships}

}
