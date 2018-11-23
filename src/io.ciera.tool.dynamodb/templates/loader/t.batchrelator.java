    public void batch_relate_${self.rel_name}() throws XtumlException {
        for ( ${self.form_name} form : population.${self.form_name}_instances() ) {
.if ( self.unconditional )
            population.relate_${self.rel_name}( form, population.${self.part_name}_instances().getById$t{self.id_num}(new InstanceIdentifier(${attribute_comparisons})));
.else
            ${self.part_name} part = population.${self.part_name}_instances().getById$t{self.id_num}(new InstanceIdentifier(${attribute_comparisons}));
            if ( !part.isEmpty() ) population.relate_${self.rel_name}( form, part );
.end if
        }
    }
