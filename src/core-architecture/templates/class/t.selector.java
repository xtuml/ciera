public ${type_name} ${self.name}() {
.if (multiplicity_many)
    return ${self.name}_set.stream();
.else
  .if (is_super)
    return R${rel.num}_subtype instanceof ${type_name} ? (${type_name}) R${rel.num}_subtype : ${type_name}.EMPTY;
  .else
    return ${self.name}_inst;
  .end if
.end if
}

