.if (multiplicity_many)
public void add${self.name}(${inst_type_name} inst) {
    ${self.name}_set.add(inst);
}

public void remove${self.name}(${inst_type_name} inst) {
    ${self.name}_set.remove(inst);
}

.elif (is_super)
public void setR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    R${rel_num}_subtype = inst;
}

public void clearR${rel_num}_Subtype() {
    R${rel_num}_subtype = null;
}

.else
public void set${self.name}(${inst_type_name} inst) {
    ${self.name}_inst = inst;
}

public void clear${self.name}() {
    ${self.name}_inst = ${inst_type_name}.EMPTY;
}

.end if
