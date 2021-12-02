    @Override
    public ${type_name} ${self.name}() {
        return new ${type_name}(this.stream()\
.if (multiplicity_many)
.flatMap($l{self.selector_class_name} -> $l{self.selector_class_name}.${self.selector_name}().stream())\
.else
.map($l{self.selector_class_name} -> $l{self.selector_class_name}.${self.selector_name}())\
.end if
.collect(Collectors.toSet()));
    }

