package ${self.package};

${imports}

public class ${self.name} implements ITemplate<${self.comp_name}> {

    @Override
    public void evaluate() throws XtumlException {
${self.blob}    }

}
