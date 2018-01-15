package ${package};

${imports}

public class ${name} extends ${extends} {
    
    private static final String keyLetters = "${key_letters}";

    // constructor
    public ${name}() {
        super();
${attribute_initializers}
    }
    
    // attributes
${attributes}

    // operations
${operations}
    
    // selections
${selectors}

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

}
