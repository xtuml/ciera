package ${self.package}.impl;

${imports}

public class ${self.name}Impl<C extends IComponent<C>> extends Utility<C> implements ${self.name} {

    public ${self.name}Impl( C population ) {
        super( population );
    }

${utility_functions}

}
