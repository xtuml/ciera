package ${self.package};

${imports}

public interface ${self.name} extends IModelInstance, IActionHome<${self.comp_name}> {
    
    // attributes
${attributes}

    // operations
${operations}
    
    // selections
${selectors}

}
