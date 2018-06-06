        for ( ${self.class_name} $l{self.class_name}_inst : population.${self.class_name}_instances() ) {
            out.print( "INSERT INTO ${self.class_key_letters} VALUES(" );
${attribute_serializers}            out.println( ");" );
        }
