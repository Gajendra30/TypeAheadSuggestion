package com.accolite.apps.garbageCollector;

import com.accolite.apps.garbageCollector.Model.Cart;
import com.accolite.apps.garbageCollector.Model.CartItem;
import com.accolite.apps.garbageCollector.Model.Dummy;

public class Main {
	
	public static void testGC() {
		try {
			Dummy d1 = new Dummy();
			CustomGC.get(d1);
			CustomGC.release(d1);
			CustomGC.gc();
		} catch (Exception e) {
		}
	}
	
	public static void testGCHierachy() {
		try {
			CartItem cartItem = new CartItem();
			Cart cart = new Cart(cartItem);
			Cart cart1 = new Cart(cartItem);
			CustomGC.get(cart);
			CustomGC.get(cart1);
			CustomGC.release(cart1);
			CustomGC.gc();
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		testGC();
		System.out.println("Creating cartItem and cart then release it from references..");
		testGCHierachy();
	}

}
