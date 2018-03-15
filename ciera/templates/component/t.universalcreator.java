    @Override
    public IModelInstance create( String keyLetters ) throws XtumlException {
${creations}        throw new InstancePopulationException( "Class with key letters '" + keyLetters + "' not supported." );
    }
