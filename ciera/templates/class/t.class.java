package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {
    
    public static final String KEY_LETTERS = "${self.key_letters}";
    public static final ${self.name} EMPTY_$u_{self.name} = new Empty${self.name}();

    // constructor
    public ${self.name}( ${self.comp_name} context ) {
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

}

class Empty${self.name} extends ${self.name} implements IEmptyInstance {
    
    public Empty${self.name}() {
        super( null );
    }

    // selections
${empty_selectors}

}
