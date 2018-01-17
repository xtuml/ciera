    public ${type_name} ${name}() {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) {
.if ( is_many )
        // TODO shortcut for when the condition is null
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        Iterator<IModelInstance> iter = getInstanceSet( "${class_key_letters}" ).iterator();
        while ( iter.hasNext() ) {
            IModelInstance candidate = iter.next();
            if ( null == condition || condition.passes( candidate ) ) \
.if ( is_many )
$l{type_name}.add( candidate );
.else
return (${type_name})candidate;
.end if
        }
.if ( is_many )
        return $l{type_name};
.else
        return ${type_name}.empty${type_name};
.end if
    }
