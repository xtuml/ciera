    public ${type_name} get${capital_name}() throws XtumlException {
        checkLiving();
.if ( is_referential )
        // TODO check referentials match
.end if
        return ${name};
    }

    public void set${capital_name}( ${type_name} ${name} ) throws XtumlException {
        checkLiving();
.if ( is_referential )
  .if ( is_primitive_type )
        if ( ${name} != get${capital_name}() ) {
  .else
        if ( !${name}.equals( get${capital_name}() ) ) {
  .end if
            this.${name} = ${name};
            // TODO propagate referentials
        }
.else
        this.${name} = ${name};
.end if
    }
