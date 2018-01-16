package io.ciera.summit.classes;

import java.util.Map;

import io.ciera.summit.exceptions.XtumlException;

public interface IInstancePopulation {

    public IInstanceSet getInstanceSet( String keyLetters );
    public IModelInstance createObjectInstance( String keyLetters ) throws XtumlException;
    public void deleteObjectInstance( IModelInstance instance ) throws XtumlException;
    public Map<String, Class<?>> getClasses();
    public IRelationshipSet getRelationshipSet( int relNum );
    public void relateAcross( int relNum, IModelInstance ... instances ) throws XtumlException;
    public void unrelateAcross( int relNum, IModelInstance ... instances ) throws XtumlException;
    public Map<Integer, Class<?>> getRelationships();

}
