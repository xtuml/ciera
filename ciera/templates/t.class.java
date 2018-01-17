package ${package};

${imports}

public class ${name} extends ${extends} {
    
    public static final String KEY_LETTERS = "${key_letters}";
    public static final ${name} EMPTY_$u_{name} = new Empty${name}();

    // constructor
    public ${name}( IInstancePopulation context ) {
        super( context );
${attribute_initializers}
    }
    
    // attributes
${attributes}

    // operations
${operations}
    
    // selections
${selectors}

    @Override
    public String getKeyLetters() {
        return KEY_LETTERS;
    }

    @Override
    public IInstanceSet toSet() {
        IInstanceSet set = new ${class_set_name}();
        set.add( this );
        return set;
    }

}

class Empty${name} extends ${name} implements IEmptyInstance {
    
    public Empty${name}() {
        super( null );
    }

    // selections
${empty_selectors}

}
