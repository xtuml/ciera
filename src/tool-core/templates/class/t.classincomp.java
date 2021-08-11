package ${self.package};

${imports}

.if ( compIndep )
public interface ${self.name} extends IModelInstance<${self.name},${comp_name}>, ${compIndepInterface} {
.else
public interface ${self.name} extends IModelInstance<${self.name},${comp_name}> {
.end if

    // attributes
${attributes}

    // operations
${operations}

    // selections
${selectors}

}
