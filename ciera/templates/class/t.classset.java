package ${self.package};

${imports}

public class ${self.name} extends ${self.extends} {

    // selections
${selectors}

    @Override
    public ${self.class_name} nullElement() {
        return ${self.class_name}.EMPTY_${self.class_name};
    }
    @Override
    public ${self.name} emptySet() {
      return new ${self.name}();
    }

}
