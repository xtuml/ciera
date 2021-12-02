.if (not self.is_class_based)
    @Override
    public ${modifiers}${type_name} ${self.name}(${parameter_list}) ${body}
.else
        public ${modifiers}${type_name} ${self.name}(${parameter_list}) ${body}
.end if
