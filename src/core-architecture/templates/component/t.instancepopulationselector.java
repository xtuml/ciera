    private ${set_name} ${self.class_name}_extent;
    public ${set_name} ${self.name}() {
        ${set_name} ${self.name} = new ${set_name}Impl();
        ${self.name}.addAll(${self.class_name}_extent);
        return ${self.name};
    }
