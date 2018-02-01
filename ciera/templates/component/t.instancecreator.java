    public ${class_name} ${name}() throws XtumlException {
        ${class_name} new${class_name} = new ${class_name}( this );
        if ( addInstance( new${class_name} ) ) return new${class_name};
        else {
            new${class_name}.delete();
            throw new InstancePopulationException( "Error creating instance of ${class_name}." );
        }
    }
