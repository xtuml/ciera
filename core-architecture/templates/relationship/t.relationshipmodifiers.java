.if (is_subsuper)
public void relate_${rel_name}(${self.part_name}.R${self.num}Subtype form, ${self.part_name} part) {
    part.setR${self.num}_Subtype(form);
  .if (part_is_many)
    form.add${part_selector.name}(part);
  .else
    form.set${part_selector.name}(part);
  .end if
    ${attribute_propagations}\
}

public void unrelate_${rel_name}(${self.part_name}.R${self.num}Subtype form, ${self.part_name} part) {
    part.clearR${self.num}_Subtype(form);
  .if (part_is_many)
    form.remove${part_selector.name}(part);
  .else
    form.clear${part_selector.name}(part);
  .end if
}
.else
public void relate_${rel_name}(${self.form_name} form, ${self.part_name} part) {
  .if (form_is_many)
    part.add${form_selector.name}(form);
  .else
    part.set${form_selector.name}(form);
  .end if
  .if (part_is_many)
    form.add${part_selector.name}(part);
  .else
    form.set${part_selector.name}(part);
  .end if
    ${attribute_propagations}\
}

public void unrelate_${rel_name}(${self.form_name} form, ${self.part_name} part) {
  .if (form_is_many)
    part.remove${form_selector.name}(form);
  .else
    part.clear${form_selector.name}(form);
  .end if
  .if (part_is_many)
    form.remove${part_selector.name}(part);
  .else
    form.clear${part_selector.name}(part);
  .end if
}
.end if
