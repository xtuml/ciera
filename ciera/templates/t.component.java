package ${package_name};

import ciera.components.Component;
.if ( init_block_throws_exception )
import ciera.exceptions.XtumlException;
.end if


public class ${component_name} extends Component {
    
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
    public Class<?>[] getClasses() {
        return classes;
    }

}
