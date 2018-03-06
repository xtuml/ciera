package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    private IRunContext runContext;
    
    public ${self.name}( IRunContext runContext ) {
        super(${port_initializers});
        this.runContext = runContext;
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
${ports}

    // component initialization function
    @Override
    public void initialize() throws XtumlException {
${init}
    }

    @Override
    public Map<String, IInstanceSet> initializeInstanceSets() {
        Map<String, IInstanceSet> classes = new HashMap<>();
${classes}\
        return classes;
    }

    @Override
    public Map<Integer, IRelationshipSet> initializeRelationshipSets() {
        Map<Integer, IRelationshipSet> relationships  = new HashMap<>();
${relationships}\
        return relationships;
    }

    @Override
    public IRunContext getRunContext() {
        return runContext;
    }

}
