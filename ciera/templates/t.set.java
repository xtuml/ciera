package ${package_name};

${import_block}

public class ${class_set_name} extends InstanceSet {

    // empty set
    public static final ${class_set_name} empty${class_set_name} = new Empty${class_set_name}();

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

class Empty${class_set_name} extends ${class_set_name} implements EmptyInstanceSet {

    // selections
${empty_selections_block}

}
