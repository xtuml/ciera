    @Override
    public void relate( int relNum, IModelInstance one, IModelInstance other, IModelInstance link ) throws XtumlException {
        switch( relNum ) {
${associative_relates}            default:
                throw new InstancePopulationException( "Relationship with number '" + relNum + "' not supported." );
        }
    }
    @Override
    public void relate( int relNum, IModelInstance one, IModelInstance other ) throws XtumlException {
        switch( relNum ) {
${other_relates}            default:
                throw new InstancePopulationException( "Relationship with number '" + relNum + "' not supported." );
        }
    }
