.if ( is_getter )
.else
    @Override
    public void ${name}( ${type_name} ${self.attribute_name} ) throws XtumlException {
        for ( ${self.class_name} $l{self.class_name} : this ) $l{self.class_name}.${name}( ${self.attribute_name} );
    }
.end if
