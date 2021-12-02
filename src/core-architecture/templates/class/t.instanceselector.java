.if (multiplicity_many)
    @Override
    public void add${self.name}(${inst_type_name} inst) {
        ${self.name}_set.add(inst);
    }

    @Override
    public void remove${self.name}(${inst_type_name} inst) {
        ${self.name}_set.remove(inst);
    }

.else
    @Override
    public void set${self.name}(${inst_type_name} inst) {
        ${self.name}_inst = inst;
    }

.end if
    @Override
    public ${type_name} ${self.name}() {
.if (multiplicity_many)
        return Collections.unmodifiableSortedSet(${self.name}_set);
.else
        return Collections.unmodifiableSortedSet(${self.name}_inst);
.end if
    }
