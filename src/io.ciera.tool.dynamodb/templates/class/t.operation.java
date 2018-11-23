.if ( not self.is_class_based )
    @Override
    public ${modifiers}${type_name} ${self.name}(${parameter_list}) throws XtumlException ${body}
.else
        public ${modifiers}${type_name} ${self.name}(${parameter_list}) throws XtumlException ${body}
.end if
