    public ${self.type_name} ${self.name}() throws XtumlException {
        return ${self.name}( null );
    }

    public ${self.type_name} ${self.name}( IWhere condition ) throws XtumlException {
.if ( self->returns_set )
        if ( null == condition ) return (${self.type_name})getInstanceSet( "${self.class_key_letters}" ).toImmutableSet();
        ${self.type_name} $l{self.type_name} = new ${self.type_name}();
.end if
        for ( IModelInstance candidate : getInstanceSet( "${self.class_key_letters}" ) ) {
            if ( null == condition || condition.passes( candidate ) ) \
.if ( self->returns_set )
$l{self.type_name}.add( candidate );
        }
        return (${self.type_name})$l{self.type_name}.toImmutableSet();
.else
return (${self.type_name})candidate;
        }
        return ${self.type_name}.EMPTY_$u_{self.type_name};
.end if
    }
