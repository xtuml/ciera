.if (is_subsuper)
public void relate_${rel_name}(${self.part_name}.R${self.num}Subtype form, ${self.part_name} part) {
.else
public void relate_${rel_name}(${self.form_name} form, ${self.part_name} part) {
.end if
    if (form.notEmpty() && part.notEmpty()) {
        if (form.isActive() && part.isActive()) {
            ${relationship_setters}\
            ${attribute_propagations}\
        } else {
            throw new DeletedInstanceException("Cannot relate deleted instances", this, Stream.of(form, part).filter(inst -> !inst.isActive()).toArray(ObjectInstance[]::new));
        }
    } else {
        throw new EmptyInstanceException("Cannot relate empty instances", this, Stream.of(form, part).filter(ObjectInstance::isEmpty).toArray(ObjectInstance[]::new));
    }
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
    if (form.notEmpty() && part.notEmpty()) {
        if (form.isActive() && part.isActive()) {
            ${relationship_unsetters}\
        } else {
            throw new DeletedInstanceException("Cannot unrelate deleted instances", this, Stream.of(form, part).filter(inst -> !inst.isActive()).toArray(ObjectInstance[]::new));
        }
    } else {
        throw new EmptyInstanceException("Cannot unrelate empty instances", this, Stream.of(form, part).filter(ObjectInstance::isEmpty).toArray(ObjectInstance[]::new));
    }
}

