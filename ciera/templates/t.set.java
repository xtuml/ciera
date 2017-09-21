package ${package_name};

${import_block}

public class ${class_set_name} extends ${extends_class} {

    // selections
${selections_block}

    @Override
    public ${class_name} getEmptyInstance() {
        return ${class_name}.empty${class_name};
    }

}
