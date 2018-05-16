package io.ciera.summit.classes;

import java.util.Map;

import io.ciera.summit.exceptions.XtumlException;

public interface IInstancePopulation {

    public boolean addInstance( IModelInstance instance ) throws XtumlException;
    public boolean removeInstance( IModelInstance instance ) throws XtumlException;

	public Map<Integer, IRelationshipSet> initializeRelationshipSets();
    public IRelationshipSet getRelationshipSet( int relNum ) throws XtumlException;
    public boolean addRelationship( IRelationship relationship ) throws XtumlException;
    public boolean removeRelationship( IRelationship relationship ) throws XtumlException;

    public IModelInstance create( String keyLetters ) throws XtumlException;
    public void relate( int relNum, IModelInstance one, IModelInstance other ) throws XtumlException;
    public void relate( int relNum, IModelInstance one, IModelInstance other, IModelInstance link ) throws XtumlException;
    public void unrelate( int relNum, IModelInstance one, IModelInstance other ) throws XtumlException;
    public void unrelate( int relNum, IModelInstance one, IModelInstance other, IModelInstance link ) throws XtumlException;
    public void deleteObjectInstance( IModelInstance instance ) throws XtumlException;

}
