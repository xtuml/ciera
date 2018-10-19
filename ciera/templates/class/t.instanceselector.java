.if ( multiplicity_many )
    private ${type_name} ${self.name}_set;
    @Override
    public void add${self.name}( ${inst_type_name} inst ) {
        ${self.name}_set.add(inst);
    }
    @Override
    public void remove${self.name}( ${inst_type_name} inst ) {
        ${self.name}_set.remove(inst);
    }
.else
    private ${type_name} ${self.name}_inst;
    @Override
    public void set${self.name}( ${inst_type_name} inst ) {
        ${self.name}_inst = inst;
    }
.end if
    @Override
    public ${type_name} ${self.name}() throws XtumlException {
.if ( multiplicity_many )
        return ${self.name}_set;
.else
        return ${self.name}_inst;
.end if
    }
