.if ( is_getter )
    @Override
    public ${type_name} ${name}() throws XtumlException {
        checkLiving();
${attribute_derivation}        return ${self.attribute_name};
    }
.else
    @Override
    public void ${name}( ${type_name} ${self.attribute_name} ) throws XtumlException {
        checkLiving();
  .if ( primitive )
        if ( ${self.attribute_name} != this.${self.attribute_name} ) {
  .else
        if ( !${self.attribute_name}.equality( this.${self.attribute_name} ) ) {
  .end if
            this.${self.attribute_name} = ${self.attribute_name};
${propagations}        }
    }
.end if
