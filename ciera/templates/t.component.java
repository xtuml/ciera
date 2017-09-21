package ${package_name};

${import_block}

public class ${component_name} extends ${extends_class} {
    
    private static final Class<?>[] classes = new Class<?>[] {
        ${class_list}
    };
    
    // selections
${select_from_instances_block}

    // component initialization functions
    @Override
.if ( init_block_throws_exception )
    public void initialize() throws XtumlException {
.else
    public void initialize() {
.end if
${init_block}
    }

    @Override
    public IInstanceSet getNewInstanceSetForClass( Class<?> type ) {
${new_instance_set_block}
        return null;
    }

    @Override
    public Class<?>[] getClasses() {
        return classes;
    }

}
