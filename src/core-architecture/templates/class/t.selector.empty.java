.if (multiplicity_many)
@Override
public ${type_name} ${self.name}() {
    return Stream.of();
}

.else
@Override
public ${type_name} ${self.name}() {
    return ${type_name}.EMPTY;
}

.end if
