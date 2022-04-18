.if (multiplicity_many)
public void add${self.name}(${inst_type_name} inst) {
    if (isActive()) {
        if (!${self.name}_set.add(inst)) {
            throw new InvalidRelationshipException("Relationship R${rel_num} already exists for instance", getDomain(), this, inst);
        }
    } else {
        throw new DeletedInstanceException("Cannot create relationship on deleted instance", getDomain(), this);
    }
}

public void remove${self.name}(${inst_type_name} inst) {
    if (isActive()) {
        if (!${self.name}_set.remove(inst)) {
            throw new InvalidRelationshipException("Relationship R${rel_num} does not exist between instances", getDomain(), this, inst);
        }
    } else {
        throw new DeletedInstanceException("Cannot delete relationship on deleted instance", getDomain(), this);
    }
}

.elif (is_super)
public void setR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    if (isActive()) {
        if (R${rel_num}_subtype == null) {
            R${rel_num}_subtype = inst;
        } else {
            throw new InvalidRelationshipException("Relationship R${rel_num} already exists for instance", getDomain(), this, inst, R${rel_num}_subtype);
        }
    } else {
        throw new DeletedInstanceException("Cannot create relationship on deleted instance", getDomain(), this);
    }
}

public void clearR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    if (isActive()) {
        if (inst.equals(R${rel_num}_subtype)) {
            R${rel_num}_subtype = null;
        } else {
            throw new InvalidRelationshipException("Relationship R${rel_num} does not exist between instances", getDomain(), this, inst, R${rel_num}_subtype);
        }
    } else {
        throw new DeletedInstanceException("Cannot delete relationship on deleted instance", getDomain(), this);
    }
}

.else
public void set${self.name}(${inst_type_name} inst) {
    if (isActive()) {
        if (${self.name}_inst.isEmpty()) {
            ${self.name}_inst = inst;
        } else {
            throw new InvalidRelationshipException("Relationship R${rel_num} already exists for instance", getDomain(), this, inst, ${self.name}_inst);
        }
    } else {
        throw new DeletedInstanceException("Cannot create relationship on deleted instance", getDomain(), this);
    }
}

public void clear${self.name}(${inst_type_name} inst) {
    if (isActive()) {
        if (${self.name}_inst.equals(inst)) {
            ${self.name}_inst = ${inst_type_name}.EMPTY;
        } else {
            throw new InvalidRelationshipException("Relationship R${rel_num} does not exist between instances", getDomain(), this, inst, ${self.name}_inst);
        }
    } else {
        throw new DeletedInstanceException("Cannot delete relationship on deleted instance", getDomain(), this);
    }
}

.end if
