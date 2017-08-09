package ${package_name};

${import_block}

@SuppressWarnings("serial")
public class ${class_set_name} extends InstanceSet {

    // empty set
    public static final ${class_set_name} empty${class_set_name} = new Empty${class_set_name}();

    // selections
    ${selections_block}

    @Override
    public ${class_name} getEmptyInstance() {
        return ${class_name}.empty${class_name};
    }

}

@SuppressWarnings("serial")
class Empty${class_set_name} extends ${class_set_name} implements EmptyInstanceSet {

    // selections
    ${empty_selections_block}

}
