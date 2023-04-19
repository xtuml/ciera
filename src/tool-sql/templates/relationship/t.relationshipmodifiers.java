    public void relate_${self.name}( ${self.form_name} form, ${self.part_name} part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
${cardinality_check}        if ( ${self.name}_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
${relationship_setters}${attribute_propagations}        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_${self.name}( ${self.form_name} form, ${self.part_name} part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
.if ( simplesym )
        if (( ${self.name}_extent.remove( ${self.name}_extent.get( form.getInstanceId(), part.getInstanceId() ) ) )
           || ( ${self.name}_extent.remove( ${self.name}_extent.get( part.getInstanceId(), form.getInstanceId() ) ) )) {
.else
        if ( ${self.name}_extent.remove( ${self.name}_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
.end if
${relationship_unsetters}        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
