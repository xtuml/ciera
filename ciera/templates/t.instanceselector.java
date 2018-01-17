    public ${type_name} ${name}() throws XtumlException {
        return ${name}( null );
    }

    public ${type_name} ${name}( IWhere condition ) throws XtumlException {
.//
.// one to other binary
.if ( 0 == selection_type )
  .if ( is_many )
        ${type_name} $l{type_name} = new ${type_name}();
  .end if
        IRelationshipSet R${rel_num}set = ((IBinaryRelationshipSet)getContext().getRelationshipSet( ${rel_num} )).getByOneId( getInstanceId() );
  .if ( unconditional )
        if ( R${rel_num}set.isEmpty() ) throw new ModelIntegrityException( "Unconditional association has no related instances." );
  .end if
        Iterator<IRelationship> iter = R${rel_num}set.iterator();
  .if ( multiplicity_many )
        while ( iter.hasNext() ) {
    .if ( is_many )
            IModelInstance candidate = getContext().getInstanceSet( $l{type_name}.getKeyLetters() ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOther() );
            if ( null == condition || condition.passes( candidate ) ) $l{type_name}.add( candidate );
        }
        return $l{type_name};
    .else
            IModelInstance candidate = getContext().getInstanceSet( ${type_name}.KEY_LETTERS ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOther() );
            if ( null == condition || condition.passes( candidate ) ) return (${type_name})candidate;
        }
        return ${type_name}.EMPTY_$u_{type_name};
    .end if
  .else
        if ( 1 == R${rel_num}set.size() ) {
            IModelInstance candidate = getContext().getInstanceSet( ${type_name}.KEY_LETTERS ).getByInstanceId( ((IBinaryRelationship)iter.next()).getOther() );
            if ( null == condition || condition.passes( candidate ) ) return (${type_name})candidate;
            else return ${type_name}.EMPTY_$u_{type_name};
        }
        else throw new ModelIntegrityException( "Association with multiplicity 'one' has more than one related instance." );
  .end if
.//
.// other to one binary
.elif ( 1 == selection_type )
        return null;
.// link to one assoc
.elif ( 2 == selection_type )
        return null;
.// link to other assoc
.elif ( 3 == selection_type )
        return null;
.// one to other assoc
.elif ( 4 == selection_type )
        return null;
.// one to link assoc
.elif ( 5 == selection_type )
        return null;
.// other to one assoc
.elif ( 6 == selection_type )
        return null;
.// other to link assoc
.elif ( 7 == selection_type )
        return null;
.// sub to super subsuper
.elif ( 8 == selection_type )
        return null;
.// super to sub subsuper
.elif ( 9 == selection_type )
        return null;
.else
        // Code generation error
.end if
    }
