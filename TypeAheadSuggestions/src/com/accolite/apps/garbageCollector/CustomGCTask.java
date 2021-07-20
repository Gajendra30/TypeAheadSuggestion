package com.accolite.apps.garbageCollector;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

public class CustomGCTask implements Runnable{
	
private Reference root;
private Set<Integer> releaseObj;
private BlockingQueue refQ;

public CustomGCTask(Reference root, Set<Integer> releaseObj, BlockingQueue referenceQueue) {
	this.root = root;
	this.releaseObj = releaseObj;
	this.refQ = referenceQueue;
}
	
@Override
public void run() {
	
	System.out.println("Starting CustomGC Task thread");
	Set<Integer> markSetBit = new HashSet<>();
	mark(root, markSetBit);
	sweep(root, markSetBit);
	
}

private Reference sweep(Reference root, Set<Integer> markSet) {
	Object obj = root.getObject();

	int hashCode = System.identityHashCode(obj);

	LinkedList<Reference> deleteReferences = new LinkedList<>();
	for (Reference reference : root.getReferences()) {
		if (sweep(reference, markSet) == null)
			deleteReferences.add(reference);
	}

	addObject(deleteReferences);
	
	root.getReferences().removeAll(deleteReferences);

	if (markSet.contains(hashCode))
		return root;
	return null;
}

//add release reference to queue to finalize the object
private void addObject(LinkedList<Reference> deleteReferences) {
	for (Reference reference : deleteReferences) {
		try {
			if (reference.getObject().getClass().getDeclaredMethod("finalize") == null)
				continue;
			refQ.add(reference.getObject());
		} catch (NoSuchMethodException e) {
		}
	}
}

// mark the used reference
private void mark(Reference root, Set<Integer> markSet) {

	Object obj = root.getObject();
	int hashCode = System.identityHashCode(obj);
	if (releaseObj.contains(hashCode)) {
		return;
	} else if (!markSet.add(hashCode)) {
		return;
	}
	for (Reference reference : root.getReferences()) {
		mark(reference, markSet);
	}
}
}
