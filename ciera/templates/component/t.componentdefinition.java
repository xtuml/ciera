package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    public ${self.name}( IRunContext runContext ) {
        super( runContext );
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

    /*
    @Override
    public Map<String, IInstanceSet> initializeInstanceSets() {
        Map<String, IInstanceSet> classes = new HashMap<>();
${class_sets}\
        return classes;
    }
    */

    @Override
    public <E extends IModelInstance> IInstanceSet<E> getInstanceSet( String keyLetters ) throws XtumlException {
        return null;  // TODO implement
    }

    @Override
    public Map<Integer, IRelationshipSet> initializeRelationshipSets() {
        Map<Integer, IRelationshipSet> relationships  = new HashMap<>();
${relationship_sets}\
        return relationships;
    }

${relationships}

}
