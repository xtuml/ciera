        ${test} ( "${key_letters}".equals( keyLetters ) ) {
            ${self.class_name} new${self.class_name} = new ${self.class_name}( this );
            if ( addInstance( new${self.class_name} ) ) return new${self.class_name};
            else {
                new${self.class_name}.delete();
                throw new InstancePopulationException( "Error creating instance of ${self.class_name}." );
            }
        }
