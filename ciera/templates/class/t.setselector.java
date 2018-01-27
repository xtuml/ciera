    public ${type_name} ${name}() throws XtumlException {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) throws XtumlException {
.if ( is_many )
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        for ( IModelInstance $l{selector_class_name} : this ) {
.if ( is_many )
            $l{type_name}.add\
  .if ( multiplicity_many )
All\
  .end if
( ((${selector_class_name})$l{selector_class_name}).${selector_name}( condition ) );
        }
        return (${type_name})$l{type_name}.toImmutableSet();
.else
            ${type_name} candidate = ((${selector_class_name})$l{selector_class_name}).${selector_name}( condition );
            if ( !(candidate instanceof IEmptyInstance) ) return candidate;
        }
        return ${type_name}.EMPTY_$u_{type_name};
.end if
    }
