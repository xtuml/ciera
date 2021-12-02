.if (is_getter)
    @Override
    public ${type_name} ${name}() {
        if (isAlive()) {
  .if (attribute_derivation != "")
            ${attribute_derivation}\
  .end if
            return ${self.attribute_name};
        } else {
            throw new InstancePopulationException("Cannot get attribute of deleted instance");
        }
    }
.else
    @Override
    public void ${name}(${type_name} ${self.attribute_name}) {
        if (isAlive()) {
  .if (primitive)
            if (${self.attribute_name} != this.${self.attribute_name}) {
  .else
    .if (is_array)
            if (ArrayUtil.inequality(${self.attribute_name}, this.${self.attribute_name})) {
    .else
            if (!${self.attribute_name}.equals(this.${self.attribute_name})) {
    .end if
  .end if
                this.${self.attribute_name} = ${self.attribute_name};
${propagations}\
            }
        } else {
            throw new InstancePopulationException("Cannot set attribute of deleted instance");
        }
    }
.end if
