public void relate_${self.name}(${self.form_name} form, ${self.part_name} part) {
    if (!form.isEmpty()) {
        if (!part.isEmpty()) {
            ${relationship_setters}\
            ${attribute_propagations}\
        } else {
            throw new EmptyInstanceException("Cannot relate empty instances.", this, part);
        }
    } else {
        throw new EmptyInstanceException("Cannot relate empty instances.", this, form);
    }
}

public void unrelate_${self.name}(${self.form_name} form, ${self.part_name} part) {
    if (!form.isEmpty()) {
        if (!part.isEmpty()) {
            ${relationship_unsetters}\
        } else {
            throw new EmptyInstanceException("Cannot unrelate empty instances.", this, part);
        }
    } else {
        throw new EmptyInstanceException("Cannot unrelate empty instances.", this, form);
    }
}

