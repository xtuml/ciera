    public void relate_${self.name}( ${self.form_name} form, ${self.part_name} part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot relate empty instances." );
${cardinality_check}
${relationship_setters}${attribute_propagations}
    }

    public void unrelate_${self.name}( ${self.form_name} form, ${self.part_name} part ) throws XtumlException {
        if ( null == form || null == part ) throw new BadArgumentException( "Null instances passed." );
        if ( form.isEmpty() || part.isEmpty() ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
${relationship_unsetters}
    }
