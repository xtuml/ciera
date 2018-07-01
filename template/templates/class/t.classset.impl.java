package ${self.package}.impl;

${imports}

public class ${self.name}Impl extends ${self.extends} implements ${self.name} {

    // attributes
${attributes}

    // selections
${selectors}

    @Override
    public ${self.class_name} nullElement() {
        return ${self.class_name}Impl.EMPTY_$u_{self.class_name};
    }

    @Override
    public ${self.name} emptySet() {
      return new ${self.name}Impl();
    }

    @Override
    public ${self.name} value() {
        return this;
    }

    @Override
    public List<${self.class_name}> elements() {
        return Arrays.asList(toArray(new ${self.class_name}[0]));
    }

}
