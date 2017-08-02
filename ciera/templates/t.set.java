package ${package_name};

${import_block}

@SuppressWarnings("serial")
public class ${class_name}Set extends InstanceSet {

    // empty set
    public static final ${class_name}Set empty${class_name}Set = new Empty${class_name}Set();

    // selections
    ${selections_block}

    @Override
    public ${class_name} getEmptyInstance() {
        return ${class_name}.empty${class_name};
    }

}

@SuppressWarnings("serial")
class Empty${class_name}Set extends ${class_name}Set implements EmptyInstanceSet {

    // selections
    ${empty_selections_block}

}
