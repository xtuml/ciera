package ${self.package};

${imports}

public class ${self.name} extends Domain {

    // utilities
    ${utilities}

    public ${self.name}(String name, Application application) {
        super(name, application);
        ${utility_initializers}
    }

    // domain functions
    ${functions}

    // relates and unrelates
    ${relationship_modifiers}

    // instance selections
    ${instance_selectors}

    // ports
    ${ports}

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
