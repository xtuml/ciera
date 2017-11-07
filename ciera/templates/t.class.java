package ${package};

${imports}

public class ${name} extends ${extends} {
    
    private static final String keyLetters = "${key_letters}";

    // constructor
    public ${name}() {
.if ( stateful )
        super( new ${name}InstanceStateMachine() );
.else
        super();
.end if
${attribute_initializers}
    }
    
    // class attributes
${attributes}

    // operations
${operations}
    
    // selections
${selectors}
    
    // relates
${relators}
    
    // unrelates
${unrelators}

    // associations
${associations}

    // empty instance
    public static final ${name} empty${name} = new Empty${name}();

    @Override
    public String getKeyLetters() {
        return keyLetters;
    }

    @Override
    public IInstanceSet toSet() {
        IInstanceSet set = new ${class_set_name}();
        set.add( this );
        return set;
    }

}

class Empty${name} extends ${name} implements IEmptyInstance {

    // selections
${empty_selectors}

    // relates
${empty_relators}

    // unrelates
${empty_unrelators}

}
