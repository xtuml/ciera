    public ${type_name} ${self.name}() throws XtumlException {
.if ( multiplicity_many )
        ${type_name} $l{type_name} = new ${type_name}();
.end if
        Set<IRelationship> R$t{self.rel_num}set = ((${self.relationship_cast}Set)population().getRelationshipSet( $t{self.rel_num} )).getBy$c{self.src_class}Id( getInstanceId() );
.if ( unconditional )
        if ( R$t{self.rel_num}set.isEmpty() ) throw new ModelIntegrityException( "Unconditional association has no related instances." );
.else
  .if ( not multiplicity_many )
        if ( R$t{self.rel_num}set.isEmpty() ) return ${type_name}.EMPTY_$u_{type_name};
  .end if
.end if
.if ( multiplicity_many )
        for ( IRelationship r$t{self.rel_num} : R$t{self.rel_num}set ) $l{type_name}.add( population().${target_class_name}_instances().getByInstanceId( ((${self.relationship_cast})r$t{self.rel_num}).get$c{self.dst_class}() ) );
        return $l{type_name}.toImmutableSet();
.else
        if ( 1 == R$t{self.rel_num}set.size() ) return population().${target_class_name}_instances().getByInstanceId( ((${self.relationship_cast})R$t{self.rel_num}set.iterator().next()).get$c{self.dst_class}() );
        else throw new ModelIntegrityException( "Association with multiplicity 'one' has more than one related instance." );
.end if
    }
