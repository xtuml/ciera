package io.ciera.cairn.classes;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.types.IUniqueId;

public class Relationship implements IRelationship {
	
	private IUniqueId form;
	private IUniqueId part;
	
	public Relationship( IUniqueId form, IUniqueId part ) {
		this.form = form;
		this.part = part;
	}

	@Override
	public IUniqueId getForm() {
		return form;
	}

	@Override
	public IUniqueId getPart() {
		return part;
	}

}
