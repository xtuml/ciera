    public void relate_${self.name}( ${self.form_name} form, ${self.part_name} part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) {
            if ( !form.isEmpty() )  System.out.printf( "form: %s\n", form.getInstanceId() );
            if ( !part.isEmpty() )  System.out.printf( "part: %s\n", part.getInstanceId() );
            throw new EmptyInstanceException( "Cannot relate empty instances." );
        }
${cardinality_check}        if ( ${self.name}_extent.add( new Relationship( form.getInstanceId(), part.getInstanceId() ) ) ) {
${relationship_setters}${attribute_propagations}        }
        else throw new ModelIntegrityException( "Instances could not be related." );
    }

    public void unrelate_${self.name}( ${self.form_name} form, ${self.part_name} part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
        if ( ${self.name}_extent.remove( ${self.name}_extent.get( form.getInstanceId(), part.getInstanceId() ) ) ) {
${relationship_unsetters}        }
        else throw new ModelIntegrityException( "Instances could not be unrelated." );
    }
