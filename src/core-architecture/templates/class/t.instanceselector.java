.if (multiplicity_many)
public void add${self.name}(${inst_type_name} inst) {
    ${self.name}_set.add(inst);
}

public void remove${self.name}(${inst_type_name} inst) {
    ${self.name}_set.remove(inst);
}

.else
  .if (is_super)
public void set${self.name}(${inst_type_name} inst) {
    R${rel.num}_subtype = inst;
}

  .else
public void set${self.name}(${inst_type_name} inst) {
    ${self.name}_inst = inst;
}

  .end if
.end if
public ${type_name} ${self.name}() {
.if (multiplicity_many)
    return new ${type_name}(${self.name}_set);
.else
  .if (is_super)
    return (${type_name}) R${rel.num}_subtype;
  .else
    return ${self.name}_inst;
  .end if
.end if
}

