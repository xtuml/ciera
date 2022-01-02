public void relate_${self.name}(${self.form_name} form, ${self.part_name} part) {
    if (!form.isEmpty() && !part.isEmpty()) {
        if (form.isAlive() && part.isAlive()) {
            ${relationship_setters}\
            ${attribute_propagations}\
        } else {
            throw new DeletedInstanceException("Cannot relate deleted instances.", this, Stream.of(form, part).filter(inst -> !inst.isAlive()).toArray(ObjectInstance[]::new));
        }
    } else {
        throw new EmptyInstanceException("Cannot relate empty instances.", this, Stream.of(form, part).filter(inst -> inst.isEmpty()).toArray(ObjectInstance[]::new));
    }
}

public void unrelate_${self.name}(${self.form_name} form, ${self.part_name} part) {
    if (!form.isEmpty() && !part.isEmpty()) {
        if (form.isAlive() && part.isAlive()) {
            ${relationship_unsetters}\
        } else {
            throw new DeletedInstanceException("Cannot unrelate deleted instances.", this, Stream.of(form, part).filter(inst -> !inst.isAlive()).toArray(ObjectInstance[]::new));
        }
    } else {
        throw new EmptyInstanceException("Cannot unrelate empty instances.", this, Stream.of(form, part).filter(inst -> !inst.isEmpty()).toArray(ObjectInstance[]::new));
    }
}

