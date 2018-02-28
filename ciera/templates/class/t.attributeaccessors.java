    public ${type_name} get${capital_name}() throws XtumlException {
        checkLiving();
        if ( this instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
${attribute_derivation}
        return ${name};
    }

    public void set${capital_name}( ${type_name} ${name} ) throws XtumlException {
        checkLiving();
        if ( this instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
${referential_checks}
.if ( primitive )
        if ( ${name} != this.${name} ) {
.else
        if ( !${name}.equals( this.${name} ) ) {
.end if
            this.${name} = ${name};
${referential_setters}
        }
    }
