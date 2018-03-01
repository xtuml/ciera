    public ${type_name} ${self.name}() throws XtumlException {
        return ${self.name}( null );
    }

    public ${type_name} ${self.name}( IWhere condition ) throws XtumlException {
.if ( returns_set )
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        Set<IRelationship> R$t{self.rel_num}set = ((${self.relationship_cast}Set)getContext().getRelationshipSet( $t{self.rel_num} )).getBy$c{self.src_class}Id( getInstanceId() );
.if ( unconditional )
        if ( R$t{self.rel_num}set.isEmpty() ) throw new ModelIntegrityException( "Unconditional association has no related instances." );
.else
  .if ( not returns_set )
        if ( R$t{self.rel_num}set.isEmpty() ) return ${type_name}.EMPTY_$u_{type_name};
  .end if
.end if
.if ( returns_set )
        for ( IRelationship r$t{self.rel_num} : R$t{self.rel_num}set ) {
            IModelInstance candidate = getContext().getInstanceSet( $l{type_name}.getKeyLetters() ).getByInstanceId( ((${self.relationship_cast})r$t{self.rel_num}).get$c{self.dst_class}() );
            if ( null == condition || condition.passes( candidate ) ) $l{type_name}.add( candidate );
        }
        return (${type_name})$l{type_name}.toImmutableSet();
.else
  .if ( multiplicity_many )
        for ( IRelationship r$t{self.rel_num} : R$t{self.rel_num}set ) {
            IModelInstance candidate = getContext().getInstanceSet( ${type_name}.KEY_LETTERS ).getByInstanceId( ((${self.relationship_cast})r$t{self.rel_num}).get$c{self.dst_class}() );
  .else
        if ( 1 == R$t{self.rel_num}set.size() ) {
            IModelInstance candidate = getContext().getInstanceSet( ${type_name}.KEY_LETTERS ).getByInstanceId( ((${self.relationship_cast})R$t{self.rel_num}set.iterator().next()).get$c{self.dst_class}() );
  .end if
            if ( \
  .if ( "subtype" == self->dst_class )
candidate instanceof ${type_name} && ( \
  .end if
null == condition || condition.passes( candidate )\
  .if ( "subtype" == self->dst_class )
 )\
  .end if
 ) return (${type_name})candidate;
  .if ( multiplicity_many )
        }
        return ${type_name}.EMPTY_$u_{type_name};
  .else
            else return ${type_name}.EMPTY_$u_{type_name};
        }
        else throw new ModelIntegrityException( "Association with multiplicity 'one' has more than one related instance." );
  .end if
.end if
    }
