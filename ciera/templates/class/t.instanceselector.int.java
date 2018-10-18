.if ( multiplicity_many )
    default public void add${self.name}( ${inst_type_name} inst ) {}
    default public void remove${self.name}( ${inst_type_name} inst ) {}
.else
    default public void set${self.name}( ${inst_type_name} inst ) {}
.end if
    public ${type_name} ${self.name}() throws XtumlException;
