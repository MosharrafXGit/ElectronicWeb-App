package com.lcwd.electronicstore.services;

import com.lcwd.electronicstore.dtos.AddItemToCartRequest;
import com.lcwd.electronicstore.dtos.CartDto;

public interface CartService {
	
	
	//add items to cart
	
	
	
	//case1: cart for user is not available: we will create the cart 
	
	
	
	//case2: cart available add the items to cart
	
	
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	//remove Item from cart
	void removeItemFromCart(String userId, int cartItem);
	
	// Remove all item from cart
	void clearCart(String userId);
	
	CartDto getCartByUser(String userId);

	

}
