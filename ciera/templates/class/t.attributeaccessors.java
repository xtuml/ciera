    public ${type_name} get${capital_name}() throws XtumlException {
        checkLiving();
        if ( this instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
${referential_getters}
${attribute_processor}
        return ${name};
    }

    public void set${capital_name}( ${type_name} ${name} ) throws XtumlException {
        checkLiving();
        if ( this instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
${referential_setters}
        this.${name} = ${name};
    }
