package com.accolite.apps.garbageCollector.Model;

public class Cart {
	
@SuppressWarnings("unused")
private CartItem item;

public Cart(CartItem item) {
	this.item = item;
}

public void finalized() {
	System.out.println("destroy Cart ");
}

}
