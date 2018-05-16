package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    // selections
${selectors}

    @Override
    public ${self.class_name} emptyInstance() {
        return ${self.class_name}.EMPTY_${self.class_name};
    }
    @Override
    public IInstanceSet<${self.class_name}> emptySet() {
      return new ${self.name}();
    }

}
