package ${package};

${imports}

public class ${name} extends ${extends} {

    // selections
${selectors}

    @Override
    public ${class_name} getEmptyInstance() {
        return ${class_name}.empty${class_name};
    }

}
