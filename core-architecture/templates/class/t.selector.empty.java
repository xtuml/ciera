.if (multiplicity_many)
@Override
${type_name} ${self.name}() {
    return Stream.of();
}

.else
@Override
${type_name} ${self.name}() {
    return ${type_name}.EMPTY;
}

.end if
