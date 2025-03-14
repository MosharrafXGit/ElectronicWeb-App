package com.lcwd.electronicstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.electronicstore.dtos.AddItemToCartRequest;
import com.lcwd.electronicstore.dtos.ApiResponseMessage;
import com.lcwd.electronicstore.dtos.CartDto;
import com.lcwd.electronicstore.services.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {
	
	
	@Autowired
	private CartService cartService;
	
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@PostMapping("/{userId}")
	public ResponseEntity<CartDto> addItemToCar(@PathVariable String userId,@RequestBody AddItemToCartRequest request)
	{
	
		CartDto cartDto = cartService.addItemToCart(userId,request);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable String userId,@PathVariable int itemId)
	{
		cartService.removeItemFromCart(userId, itemId);
		ApiResponseMessage response = ApiResponseMessage.builder()
							.message("Item is removed")
							.success(true)
							.status(HttpStatus.OK)
							.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	//clear cart
	
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId)
	{
		cartService.clearCart(userId);
		ApiResponseMessage response = ApiResponseMessage.builder()
							.message("Now cart is blank")
							.success(true)
							.status(HttpStatus.OK)
							.build();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	//Add items to cart
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId)
	{
	
		CartDto cartDto = cartService.getCartByUser(userId);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}
	
	

}
