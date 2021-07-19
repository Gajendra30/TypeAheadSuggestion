package com.accolite.apps.garbageCollector;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Reference {
	
	private Object object;
	private Set<Reference>  references;
	
	public Reference(Object object) {
		this.object = object;
		references = new HashSet<>();
	}
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}

	public Set<Reference> getReferences() {
		return references;
	}

	 public void addReference(Reference reference) {
	        this.references.add(reference);
	 }

	@Override
	public int hashCode() {
		return Objects.hash(object);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reference other = (Reference) obj;
		return Objects.equals(object, other.object);
	}
}
