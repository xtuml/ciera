package io.ciera.summit.classes;

public interface IRelationship {
	
	public int getNumber();
	public void delete();
	public IRelationshipSet toSet();

}
