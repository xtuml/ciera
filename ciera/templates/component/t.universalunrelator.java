    @Override
    public void unrelate( int relNum, IModelInstance one, IModelInstance other, IModelInstance link ) throws XtumlException {
        if ( null == one || null == other || null == link ) throw new BadArgumentException( "Null instances passed." );
        if ( one instanceof IEmptyInstance || other instanceof IEmptyInstance || link instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
${associative_unrelates}\
.if ( "" != associative_unrelates )
        else \
.end if
throw new InstancePopulationException( "Relationship '" + relNum + "' between '" + one.getClass().getName() + "', '" + other.getClass().getName() + "', and '" + link.getClass().getName() + "' not supported." );
    }
    @Override
    public void unrelate( int relNum, IModelInstance one, IModelInstance other ) throws XtumlException {
        if ( null == one || null == other  ) throw new BadArgumentException( "Null instances passed." );
        if ( one instanceof IEmptyInstance || other instanceof IEmptyInstance ) throw new EmptyInstanceException( "Cannot unrelate empty instances." );
${other_unrelates}\
.if ( "" != other_unrelates )
        else \
.end if
throw new InstancePopulationException( "Relationship '" + relNum + "' between '" + one.getClass().getName() + "' and '" + other.getClass().getName() + "' not supported." );
    }
