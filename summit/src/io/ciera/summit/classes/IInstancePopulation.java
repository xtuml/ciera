package io.ciera.summit.classes;

import java.util.Map;

import io.ciera.summit.exceptions.XtumlException;

public interface IInstancePopulation {

	public Map<String, IInstanceSet> initializeInstanceSets();
    public IInstanceSet getInstanceSet( String keyLetters ) throws XtumlException;
    public boolean addInstance( IModelInstance instance ) throws XtumlException;
    public boolean removeInstance( IModelInstance instance ) throws XtumlException;

	public Map<Integer, IRelationshipSet> initializeRelationshipSets();
    public IRelationshipSet getRelationshipSet( int relNum ) throws XtumlException;
    public boolean addRelationship( IRelationship relationship ) throws XtumlException;
    public boolean removeRelationship( IRelationship relationship ) throws XtumlException;

    public void deleteObjectInstance( IModelInstance instance ) throws XtumlException;

}
