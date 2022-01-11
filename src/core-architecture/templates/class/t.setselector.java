public ${type_name} ${self.name}() {
    return new ${type_name}(stream()\
.if (multiplicity_many)
..flatMap($l{self.class_name} -> $l{self.class_name}.${self.inst_selector_name}().stream())\
.else
..map($l{self.class_name} -> $l{self.class_name}.${self.inst_selector_name}())\
.end if
..collect(Collectors.toSet()));
}

