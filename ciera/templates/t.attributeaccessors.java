    public synchronized ${attr_type_name} get${attr_camel_case_name}() throws XtumlException {
        checkLiving();
.if ( is_referential_attr )
        // TODO: ${attr_name} = some selection here
.end if
        return ${attr_name};
    }
.if ( ( not is_referential_attr ) and ( not attr_is_unique ) )

    public synchronized void set${attr_camel_case_name}( ${attr_type_name} ${attr_name} ) throws XtumlException {
        checkLiving();
        this.${attr_name} = ${attr_name};
    }
.end if
