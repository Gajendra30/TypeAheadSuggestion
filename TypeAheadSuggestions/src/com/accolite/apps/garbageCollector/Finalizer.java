package com.accolite.apps.garbageCollector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;

public class Finalizer<T> implements Runnable {
	
	private BlockingQueue<T> referenceQueue;

	public Finalizer(BlockingQueue<T> referenceQueue) {
		this.referenceQueue = referenceQueue;
	}

	@Override
	public void run() {
		System.out.println("Finalizing Task");

		while (true) {
			T obj = referenceQueue.remove();
			try {
				Method finalize = obj.getClass().getDeclaredMethod("finalize");

				if (finalize == null)
					continue;

				finalize.invoke(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
	}

}
