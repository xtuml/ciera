.if ( self->is_class_based )
    // public ${modifiers}${type_name} ${self.name}(${parameter_list}) throws XtumlException;
.else
    public ${modifiers}${type_name} ${self.name}(${parameter_list}) throws XtumlException;
.end if
