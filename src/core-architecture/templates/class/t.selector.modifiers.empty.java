.if (multiplicity_many)
@Override
public void add${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot create relationship on empty instance", getDomain(), this);
}

@Override
public void remove${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot delete relationship on empty instance", getDomain(), this);
}

.elif (is_super)
@Override
public void setR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    throw new EmptyInstanceException("Cannot create relationship on empty instance", getDomain(), this);
}

@Override
public void clearR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    throw new EmptyInstanceException("Cannot delete relationship on empty instance", getDomain(), this);
}

.else
@Override
public void set${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot create relationship on empty instance", getDomain(), this);
}

@Override
public void clear${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot delete relationship on empty instance", getDomain(), this);
}

.end if
