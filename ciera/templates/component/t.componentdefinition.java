package ${package};

${imports}

public class ${name} extends ${extends} {
    
    public ${name}() {
        super(${port_initializers});
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

}
