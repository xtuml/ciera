public ${inst_type} ${self.name}(${other_class_name} $l{other_class_name}) {
.if (other_many)
    return $l{other_class_name}.${other_selector_name}()\
.else
    return Optional.of($l{other_class_name}.${other_selector_name}())\
.end if
.if (multiplicity_many)
..filter(${self.name}_set::contains)\
.else
..filter(${self.name}_inst::equals)\
.end if
.if (other_many)
..findAny()\
.end if
..orElse(${inst_type}.EMPTY);
}

public Stream<${inst_type}> ${self.name}(Stream<${other_class_name}> $l{other_class_name}s) {
    return $l{other_class_name}s\
.if (other_many)
..flatMap(${other_class_name}::${other_selector_name})\
.else
..map(${other_class_name}::${other_selector_name})\
.end if
.if (multiplicity_many)
..filter(${self.name}_set::contains);
.else
..filter(${self.name}_inst::equals);
.end if
}

public Stream<${inst_type}> ${self.name}(Collection<${other_class_name}> $l{other_class_name}s) {
    return ${self.name}($l{other_class_name}s.stream());
}

