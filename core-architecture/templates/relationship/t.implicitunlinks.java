.if (is_subsuper)
public void unrelate_${rel_name}(${self.part_name}.R${self.num}Subtype form) {
.else
public void unrelate_${rel_name}(${self.form_name} form) {
.end if
  .if (part_selector_is_many)
    form.${part_selector_name}().forEach(part -> this.unrelate_${rel_name}(form, part));
  .else
    this.unrelate_${rel_name}(form, form.${part_selector_name}());
  .end if
}

public void unrelate_${rel_name}(${self.part_name} part) {
.if (form_selector_is_many)
    part.${form_selector_name}().forEach(form -> this.unrelate_${rel_name}(form, part));
.else
    this.unrelate_${rel_name}(part.${form_selector_name}(), part);
.end if
}

public void unrelate_${rel_name}(Iterable<?> insts) {
    insts.forEach(inst -> {
.if (is_subsuper)
        if (inst instanceof ${self.part_name}.R${self.num}Subtype) {
            this.unrelate_${rel_name}((${self.part_name}.R${self.num}Subtype) inst);
.else
        if (inst instanceof ${self.form_name}) {
            this.unrelate_${rel_name}((${self.form_name}) inst);
.end if
        } else if (inst instanceof ${self.part_name}) {
            this.unrelate_${rel_name}((${self.part_name}) inst);
        } else {
            throw new IllegalArgumentException("Invalid type to unrelate: " + inst.getClass().getSimpleName());
        }
    });
}
