    @Override
    public ${type_name} ${self.name}() throws XtumlException {
        ${type_name} $l{type_name} = new ${type_name}Impl();
        for ( ${self.selector_class_name} $l{self.selector_class_name} : this ) $l{type_name}.add\
.if ( multiplicity_many )
All\
.end if
( $l{self.selector_class_name}.${self.selector_name}() );
        return $l{type_name};
    }
