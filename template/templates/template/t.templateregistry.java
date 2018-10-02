package ${self.package};

${imports}

public class ${self.name} implements ITemplateRegistry {

    private ${self.comp_name} population;

    public ${self.name}( ${self.comp_name} population ) {
        this.population = population;
    }

    @Override
    public ITemplate getTemplate( String filename ) {
        switch ( filename ) {
${template_initializers}        default:
            return (symbolTable) -> {};
        }
    }

}
