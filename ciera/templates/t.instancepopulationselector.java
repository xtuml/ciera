    public ${type_name} ${name}() throws XtumlException {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) throws XtumlException {
.if ( is_many )
        if ( null == condition ) return (${type_name})getInstanceSet( "${class_key_letters}" ).toImmutableSet();
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        Iterator<IModelInstance> iter = getInstanceSet( "${class_key_letters}" ).iterator();
        while ( iter.hasNext() ) {
            IModelInstance candidate = iter.next();
            if ( null == condition || condition.passes( candidate ) ) \
.if ( is_many )
$l{type_name}.add( candidate );
        }
        return (${type_name})$l{type_name}.toImmutableSet();
.else
return (${type_name})candidate;
        }
        return ${type_name}.EMPTY_$u_{type_name};
.end if
    }
