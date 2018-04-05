    @Override
    public void relate( int relNum, IModelInstance one, IModelInstance other, IModelInstance link ) throws XtumlException {
        if ( null == one || null == other || null == link ) throw new BadArgumentException( "Null instances passed." );
        if ( one instanceof IEmptyInstance || other instanceof IEmptyInstance || link instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot relate empty instances." );
${associative_relates}        \
.if ( "" != associative_relates )
else \
.end if
throw new InstancePopulationException( "Relationship '" + relNum + "' between '" + one.getClass().getName() + "', '" + other.getClass().getName() + "', and '" + link.getClass().getName() + "' not supported." );
    }
    @Override
    public void relate( int relNum, IModelInstance one, IModelInstance other ) throws XtumlException {
        if ( null == one || null == other  ) throw new BadArgumentException( "Null instances passed." );
        if ( one instanceof IEmptyInstance || other instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot relate empty instances." );
${other_relates}        \
.if ( "" != other_relates )
else \
.end if
throw new InstancePopulationException( "Relationship '" + relNum + "' between '" + one.getClass().getName() + "' and '" + other.getClass().getName() + "' not supported." );
    }
