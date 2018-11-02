.if ( is_getter )
    public ${type_name} ${name}() throws XtumlException;
.else
    public void ${name}( ${type_name} ${self.attribute_name} ) throws XtumlException;
.end if
