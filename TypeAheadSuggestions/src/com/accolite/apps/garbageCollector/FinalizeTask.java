package com.accolite.apps.garbageCollector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;

public class FinalizeTask<T> implements Runnable {
	
	private BlockingQueue<T> referenceQueue;

	public FinalizeTask(BlockingQueue<T> referenceQueue) {
		this.referenceQueue = referenceQueue;
	}

	@Override
	public void run() {
		System.out.println("Finalizing Task");

		while (true) {
			T object = referenceQueue.remove();
			try {
				Method finalize = object.getClass().getDeclaredMethod("finalize");

				if (finalize == null)
					continue;

				finalize.invoke(object);
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
