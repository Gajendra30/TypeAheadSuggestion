package com.accolite.apps.garbageCollector;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class CustomGC {
	
	private static Reference root = new Reference(new Object());
	private static Map<Integer, Reference> listOfReferences = new HashMap<>();
	private static ExecutorService gcExecutorService = Executors.newSingleThreadExecutor();
	private static ExecutorService finalizeExecutor = Executors.newSingleThreadExecutor();
	private static BlockingQueue<Object> refObjects = new ArrayBlockingQueue<>(10000);
	private static Set<Integer> releaseObj = new HashSet<>();

	private CustomGC() {
	}

	static {
		Finalizer<Object> finalizeTask = new Finalizer<>(refObjects);
		finalizeExecutor.submit(finalizeTask);
	}

	public static Reference get(Object object) {
		return createReferences(object, 0);
	}
	
	private static Reference createReferences(Object object, int count) {

		if (object == null) {
			return null;
		}
		int hashCode = object.hashCode();
		Reference reference = listOfReferences.get(hashCode);
		if (reference == null) {
			System.out.println("create reference for Object:" + object.getClass().getName());

			reference = new Reference(object);
		}
		if (count == 0) {
			root.addReference(reference);
		}
		for (Field field : object.getClass().getDeclaredFields()) {
			if (field instanceof Object) {
				try {
					System.out.println("create reference for Object:" + field.getName());
					field.setAccessible(true);
					reference.addReference(createReferences(field.get(object), ++count));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return reference;
	}
	
	
	//release  the reference from the graph 
		public static synchronized void release(Object object) {
			System.out.println("Initializing......");
			if (object == null)
				return;
			releaseObj.add(object.hashCode());
		}

		// call gc for running the clean up process
		public static synchronized void gc() {
			System.out.println("GC Call Started !!!");
			gcExecutorService.submit(new CustomGCTask(root, new HashSet<Integer>(releaseObj), refObjects));

			releaseObj.clear();
			System.out.println("Clean Up Done !!!");
		}

}
