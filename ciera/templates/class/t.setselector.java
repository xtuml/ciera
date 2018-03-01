    public ${type_name} ${self.name}() throws XtumlException {
        return ${self.name}( null );
    }

    public ${type_name} ${self.name}( IWhere condition ) throws XtumlException {
.if ( returns_set )
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        for ( IModelInstance $l{self.selector_class_name} : this ) {
.if ( returns_set )
            $l{type_name}.add\
  .if ( multiplicity_many )
All\
  .end if
( ((${self.selector_class_name})$l{self.selector_class_name}).${self.selector_name}( condition ) );
        }
        return (${type_name})$l{type_name}.toImmutableSet();
.else
            ${type_name} candidate = ((${self.selector_class_name})$l{self.selector_class_name}).${self.selector_name}( condition );
            if ( !(candidate instanceof IEmptyInstance) ) return candidate;
        }
        return ${type_name}.EMPTY_$u_{type_name};
.end if
    }
