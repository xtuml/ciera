.if ( is_getter )
    public ${type_name} get${capital_name}() throws XtumlException;
.else
    public void set${capital_name}( ${type_name} ${self.attribute_name} ) throws XtumlException;
.end if
