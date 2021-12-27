.if (multiplicity_many)
public void add${self.name}(${inst_type_name} inst) {
    ${self.name}_set.add(inst);
}

public void remove${self.name}(${inst_type_name} inst) {
    ${self.name}_set.remove(inst);
}

.else
public void set${self.name}(${inst_type_name} inst) {
    ${self.name}_inst = inst;
}

.end if
public ${type_name} ${self.name}() {
.if (multiplicity_many)
    return new ${type_name}(${self.name}_set);
.else
    return ${self.name}_inst;
.end if
}

