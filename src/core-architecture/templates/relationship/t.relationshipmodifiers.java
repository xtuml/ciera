.if (is_subsuper)
public void relate_${rel_name}(${self.part_name}.R${self.num}Subtype form, ${self.part_name} part) {
.else
public void relate_${rel_name}(${self.form_name} form, ${self.part_name} part) {
.end if
    ${relationship_setters}\
    ${attribute_propagations}\
}

.if (part_uncond_one)
  .if (is_subsuper)
public void unrelate_${rel_name}(${self.part_name}.R${self.num}Subtype form) {
  .else
public void unrelate_${rel_name}(${self.form_name} form) {
  .end if
    unrelate_${rel_name}(form, form.${part_selector_name}());
}

.end if
.if (form_uncond_one)
public void unrelate_${rel_name}(${self.part_name} part) {
    unrelate_${rel_name}(part.${form_selector_name}(), part);
}

.end if
.if (is_subsuper)
public void unrelate_${rel_name}(${self.part_name}.R${self.num}Subtype form, ${self.part_name} part) {
.else
public void unrelate_${rel_name}(${self.form_name} form, ${self.part_name} part) {
.end if
    ${relationship_unsetters}\
}
