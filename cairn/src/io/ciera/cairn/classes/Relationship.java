package io.ciera.cairn.classes;

import io.ciera.summit.classes.IRelationship;
import io.ciera.summit.classes.UniqueId;

public class Relationship implements IRelationship {
	
	private UniqueId form;
	private UniqueId part;
	
	public Relationship( UniqueId form, UniqueId part ) {
		this.form = form;
		this.part = part;
	}

	@Override
	public UniqueId getForm() {
		return form;
	}

	@Override
	public UniqueId getPart() {
		return part;
	}

}
