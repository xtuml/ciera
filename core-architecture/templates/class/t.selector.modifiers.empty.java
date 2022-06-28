.if (multiplicity_many)
@Override
void add${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot create relationship on empty instance", getDomain(), this);
}

@Override
void remove${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot delete relationship on empty instance", getDomain(), this);
}

.elif (is_super)
@Override
void setR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    throw new EmptyInstanceException("Cannot create relationship on empty instance", getDomain(), this);
}

@Override
void clearR${rel_num}_Subtype(R${rel_num}Subtype inst) {
    throw new EmptyInstanceException("Cannot delete relationship on empty instance", getDomain(), this);
}

.else
@Override
void set${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot create relationship on empty instance", getDomain(), this);
}

@Override
void clear${self.name}(${inst_type_name} inst) {
    throw new EmptyInstanceException("Cannot delete relationship on empty instance", getDomain(), this);
}

.end if
