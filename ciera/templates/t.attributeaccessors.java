    public synchronized ${attr_type_name} get${attr_camel_case_name}() throws XtumlException {
        checkLiving();
.if ( is_referential_attr )
        // TODO check referentials match
.end if
        return ${attr_name};
    }

    public synchronized void set${attr_camel_case_name}( ${attr_type_name} ${attr_name} ) throws XtumlException {
        checkLiving();
.if ( is_referential_attr )
  .if ( is_primitive_type )
        if ( ${attr_name} != get${attr_camel_case_name}() ) {
  .else
        if ( !${attr_name}.equals( get${attr_camel_case_name}() ) ) {
  .end if
            this.${attr_name} = ${attr_name};
            // TODO propagate referentials
        }
.else
        this.${attr_name} = ${attr_name};
.end if
    }
