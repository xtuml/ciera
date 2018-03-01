.if ( getter )
    public ${type_name} get${capital_name}() throws XtumlException {
        checkLiving();
        if ( this instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
${attribute_derivation}
        return ${attribute_name};
    }
.else
    public void set${capital_name}( ${type_name} ${attribute_name} ) throws XtumlException {
        checkLiving();
        if ( this instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
  .if ( primitive )
        if ( ${attribute_name} != this.${attribute_name} ) {
  .else
        if ( !${attribute_name}.equals( this.${attribute_name} ) ) {
  .end if
            this.${attribute_name} = ${attribute_name};
${attribute_propagations}\
        }
    }
.end if
