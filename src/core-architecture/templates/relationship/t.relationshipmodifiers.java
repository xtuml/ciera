    public void relate_${self.name}(${self.form_name} form, ${self.part_name} part) {
        if (!form.isEmpty() && !part.isEmpty()) {
${relationship_setters}${attribute_propagations}        } else {
            throw new EmptyInstanceException("Cannot relate empty instances.");
        }
    }

    public void unrelate_${self.name}(${self.form_name} form, ${self.part_name} part) {
        if (!form.isEmpty() && !part.isEmpty()) {
${relationship_unsetters}        } else {
            throw new EmptyInstanceException("Cannot unrelate empty instances.");
        }
    }
