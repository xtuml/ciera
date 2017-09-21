package ${package_name};

${import_block}

public class ${class_name} extends ${extends_class} {
    
    private static final int classNumber = ${class_number};
    private static final String keyLetters = "${class_kl}";

    // constructor
    public ${class_name}() {
.if ( has_ism )
        super( new ${class_name}InstanceStateMachine() );
.else
        super();
.end if
${attr_init_block}
    }
    
    // class attributes
${attribute_block}

    // operations
${operations_block}
    
    // attribute accessors
${accessors_block}

    // selections
${selections_block}
    
    // relates
${relates_block}
    
    // unrelates
${unrelates_block}

    // associations
${assoc_attr_block}
    
${rto_functions_block}

    // empty instance
    public static final ${class_name} empty${class_name} = new Empty${class_name}();

    @Override
    public int getClassNumber() {
        return classNumber;
    }
    
    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

    @Override
    public IInstanceSet toSet() {
        IInstanceSet return_set = new ${class_set_name}();
        return_set.add( this );
        return return_set;
    }

}

class Empty${class_name} extends ${class_name} implements IEmptyInstance {

    // selections
${empty_selections_block}

    // relates
${empty_relates_block}

    // unrelates
${empty_unrelates_block}

}
