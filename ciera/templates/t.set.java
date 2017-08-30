package ${package_name};

${import_block}

public class ${class_set_name} extends InstanceSet {

    // constructor
    public ${class_set_name}() {
        super( ${class_name}.class );
    }

    // selections
${selections_block}

    @Override
    public ${class_name} getEmptyInstance() {
        return ${class_name}.empty${class_name};
    }

}
