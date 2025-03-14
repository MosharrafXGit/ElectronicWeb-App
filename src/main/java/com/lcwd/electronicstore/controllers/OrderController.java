package com.lcwd.electronicstore.controllers;

import java.util.List;

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

import com.lcwd.electronicstore.dtos.ApiResponseMessage;
import com.lcwd.electronicstore.dtos.CreateOrderRequest;
import com.lcwd.electronicstore.dtos.OrderDto;
import com.lcwd.electronicstore.services.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	
	@Autowired
	private OrderService orderService;
	
	
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@PostMapping
	public ResponseEntity<OrderDto> createOrder (@Valid @RequestBody CreateOrderRequest request)
	{
		OrderDto order = orderService.createOrder(request);
		
		return new ResponseEntity<> (order, HttpStatus.CREATED);
		
	}
	

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{orderId}")
	public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId)
	{
		orderService.removeOrder(orderId);
		
		ApiResponseMessage responeMessage = ApiResponseMessage.builder().status(HttpStatus.OK)
									.message("order is removed!!")
									.success(true)
									.build();
		return new ResponseEntity<>(responeMessage,HttpStatus.OK);
		
	}
	
	
	//get order of the user
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@GetMapping("/users/{userId}")
	public ResponseEntity<List<OrderDto>> getOrderOfUser(@PathVariable String userId)
	{
		
		List<OrderDto> ordersOfUser = orderService.getOrderOfUser(userId);
		return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<OrderDto>> getOrders(@PathVariable String userId)
	{
		
		List<OrderDto> orders = orderService.getOrders();
		return new ResponseEntity<>(orders,HttpStatus.OK);
		
	}

}
