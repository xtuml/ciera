package ${self.package};

${imports}

public class ${self.name} extends Template<${self.comp_name}> {

    public ${self.name}( ${self.comp_name} population ) {
        super( population );
    }

    @Override
    public void evaluate() throws XtumlException {
${self.blob}    }

}
