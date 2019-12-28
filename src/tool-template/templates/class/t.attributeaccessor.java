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
    .if ( is_string )
        if ( StringUtil.inequality( ${self.attribute_name}, this.${self.attribute_name} ) ) {
    .else
        if ( ${self.attribute_name}.inequality( this.${self.attribute_name} ) ) {
    .end if
  .end if
            final ${type_name} oldValue = this.${self.attribute_name};
            this.${self.attribute_name} = ${self.attribute_name};
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "${self.attribute_name}", oldValue, this.${self.attribute_name}));
${propagations}        }
    }
.end if
