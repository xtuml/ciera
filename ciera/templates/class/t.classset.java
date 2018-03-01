package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    public ${self.name}() {
        super( ${self.class_name}.KEY_LETTERS );
    }

    // selections
${selectors}

}
