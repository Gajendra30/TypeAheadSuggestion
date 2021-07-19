package com.accolite.apps.garbageCollector.Model;

public class User {
	@SuppressWarnings("unused")
	private Cart cart;

	public User(Cart cart) {
		this.cart = cart;
	}

	public void finalized() {
		System.out.println("destroy User ");
	}

}
