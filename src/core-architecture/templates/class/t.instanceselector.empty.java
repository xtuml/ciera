.if (multiplicity_many)
@Override
public ${type_name} ${self.name}() {
    return new ${type_name}();
}

.else
@Override
public ${type_name} ${self.name}() {
    return ${type_name}.EMPTY;
}

.end if
