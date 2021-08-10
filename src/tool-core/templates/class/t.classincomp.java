package ${self.package};

${imports}

public interface ${self.name} extends IModelInstance<${self.name},${self.comp_name}>, ${compIndepInterface} {

    // attributes
${attributes}

    // operations
${operations}

    // selections
${selectors}

}
