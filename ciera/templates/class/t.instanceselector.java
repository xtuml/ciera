    public ${type_name} ${self.name}() throws XtumlException {
.if ( multiplicity_many )
        ${type_name} $l{type_name} = new ${type_name}Impl();
.end if
        IRelationshipSet R$t{self.rel_num}set = population().${selector_name}().get\
.if ( self->formalizer )
Participating\
.else
Formalizing\
.end if
( getInstanceId() );
.if ( unconditional )
        if ( R$t{self.rel_num}set.isEmpty() ) throw new ModelIntegrityException( "Unconditional association has no related instances." );
.else
  .if ( not multiplicity_many )
        if ( R$t{self.rel_num}set.isEmpty() ) return ${type_name}Impl.EMPTY_$u_{type_name};
  .end if
.end if
.if ( multiplicity_many )
        for ( IRelationship r$t{self.rel_num} : R$t{self.rel_num}set ) $l{type_name}.add( population().${target_class_name}_instances().getByInstanceId( r$t{self.rel_num}.get\
  .if ( self->formalizer )
Part\
  .else
Form\
  .end if
() ) );
        return $l{type_name}.toImmutableSet();
.else
        if ( 1 == R$t{self.rel_num}set.size() ) return population().${target_class_name}_instances().getByInstanceId( R$t{self.rel_num}set.iterator().next().get\
  .if ( self->formalizer )
Part\
  .else
Form\
  .end if
() );
        else throw new ModelIntegrityException( "Association with multiplicity 'one' has more than one related instance." );
.end if
    }
