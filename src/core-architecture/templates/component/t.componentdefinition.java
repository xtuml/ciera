package ${self.package};

${imports}

public class ${self.name} extends Domain {

    // utilities
    ${utilities}

    // ports
    ${ports}

    public ${self.name}(String name, Application application) {
        super(name, application);
        ${utility_initializers}
        ${port_initializers}
    }

    // domain functions
    ${functions}

    // static class operations
    ${class_operations}

    // relates and unrelates
    ${relationship_modifiers}

    // port accessors
    ${port_accessors}

    // component initialization function
    @Override
    public void initialize() {
        ${init}
    }

    @Override
    public ${self.name} getDomain() {
        return (${self.name}) super.getDomain();
    }

}
