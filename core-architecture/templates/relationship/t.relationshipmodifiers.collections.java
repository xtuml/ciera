.if (is_subsuper)
void relate_${rel_name}(Iterable<${self.part_name}.R${self.num}Subtype> forms, ${self.part_name} part) {
    forms.forEach(form -> this.relate_${rel_name}(form, part));
}

void relate_${rel_name}(${self.part_name}.R${self.num}Subtype form, Iterable<${self.part_name}> parts) {
    parts.forEach(part -> this.relate_${rel_name}(form, part));
}

void relate_${rel_name}(Iterable<${self.part_name}.R${self.num}Subtype> forms, Iterable<${self.part_name}> parts) {
    forms.forEach(form -> parts.forEach(part -> this.relate_${rel_name}(form, part)));
}

void unrelate_${rel_name}(Iterable<${self.part_name}.R${self.num}Subtype> forms, ${self.part_name} part) {
    forms.forEach(form -> this.unrelate_${rel_name}(form, part));
}

void unrelate_${rel_name}(${self.part_name}.R${self.num}Subtype form, Iterable<${self.part_name}> parts) {
    parts.forEach(part -> this.unrelate_${rel_name}(form, part));
}

void unrelate_${rel_name}(Iterable<${self.part_name}.R${self.num}Subtype> forms, Iterable<${self.part_name}> parts) {
    forms.forEach(form -> parts.forEach(part -> this.unrelate_${rel_name}(form, part)));
}
.else
void relate_${rel_name}(Iterable<${self.form_name}> forms, ${self.part_name} part) {
    forms.forEach(form -> this.relate_${rel_name}(form, part));
}

void relate_${rel_name}(${self.form_name} form, Iterable<${self.part_name}> parts) {
    parts.forEach(part -> this.relate_${rel_name}(form, part));
}

void relate_${rel_name}(Iterable<${self.form_name}> forms, Iterable<${self.part_name}> parts) {
    forms.forEach(form -> parts.forEach(part -> this.relate_${rel_name}(form, part)));
}

void unrelate_${rel_name}(Iterable<${self.form_name}> forms, ${self.part_name} part) {
    forms.forEach(form -> this.unrelate_${rel_name}(form, part));
}

void unrelate_${rel_name}(${self.form_name} form, Iterable<${self.part_name}> parts) {
    parts.forEach(part -> this.unrelate_${rel_name}(form, part));
}

void unrelate_${rel_name}(Iterable<${self.form_name}> forms, Iterable<${self.part_name}> parts) {
    forms.forEach(form -> parts.forEach(part -> this.unrelate_${rel_name}(form, part)));
}
.end if
