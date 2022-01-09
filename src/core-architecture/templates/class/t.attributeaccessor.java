.if (is_getter)
public ${type_name} ${name}() {
    if (isAlive()) {
  .if (attribute_derivation != "")
        ${attribute_derivation}
  .end if
        return ${self.attribute_name};
    } else {
        throw new DeletedInstanceException("Cannot get attribute '${self.attribute_name}' of deleted instance", getDomain(), this);
    }
}

.else
public void ${name}(${type_name} ${self.attribute_name}) {
    if (isAlive()) {
  .if (primitive)
        if (${self.attribute_name} != this.${self.attribute_name}) {
  .else
        if (!${self.attribute_name}.equals(this.${self.attribute_name})) {
  .end if
            this.${self.attribute_name} = ${self.attribute_name};
  .if (propagations != "")
            ${propagations}\
  .end if
        }
    } else {
        throw new DeletedInstanceException("Cannot set attribute '${self.attribute_name}' of deleted instance", getDomain(), this);
    }
}

.end if
