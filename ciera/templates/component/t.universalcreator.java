    @Override
    public IModelInstance createObjectInstance( String keyLetters ) throws XtumlException {
        switch( keyLetters ) {
${creations}            default:
                throw new InstancePopulationException( "Class with key letters '" + keyLetters + "' not supported." );
        }
    }
