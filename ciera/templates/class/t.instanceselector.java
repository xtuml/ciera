    public ${type_name} ${name}() throws XtumlException {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) throws XtumlException {
.if ( is_many )
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        Set<IRelationship> R${rel_num}set = ((${relationship_cast}Set)getContext().getRelationshipSet( ${rel_num} )).getBy$c{src_class}Id( getInstanceId() );
.if ( unconditional )
        if ( R${rel_num}set.isEmpty() ) throw new ModelIntegrityException( "Unconditional association has no related instances." );
.end if
.if ( is_many )
        for ( IRelationship r${rel_num} : R${rel_num}set ) {
            IModelInstance candidate = getContext().getInstanceSet( $l{type_name}.getKeyLetters() ).getByInstanceId( ((${relationship_cast})r${rel_num}).get$c{dst_class}() );
            if ( null == condition || condition.passes( candidate ) ) $l{type_name}.add( candidate );
        }
        return (${type_name})$l{type_name}.toImmutableSet();
.else
  .if ( multiplicity_many )
        for ( IRelationship r${rel_num} : R${rel_num}set ) {
            IModelInstance candidate = getContext().getInstanceSet( ${type_name}.KEY_LETTERS ).getByInstanceId( ((${relationship_cast})r${rel_num}).get$c{dst_class}() );
  .else
        if ( 1 == R${rel_num}set.size() ) {
            IModelInstance candidate = getContext().getInstanceSet( ${type_name}.KEY_LETTERS ).getByInstanceId( ((${relationship_cast})R${rel_num}set.iterator().next()).get$c{dst_class}() );
  .end if
            if ( \
  .if ( "subtype" == dst_class )
candidate instanceof ${type_name} && ( \
  .end if
null == condition || condition.passes( candidate )\
  .if ( "subtype" == dst_class )
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
