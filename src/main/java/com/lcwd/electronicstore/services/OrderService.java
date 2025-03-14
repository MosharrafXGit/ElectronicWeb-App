package com.lcwd.electronicstore.services;

import java.util.List;

import com.lcwd.electronicstore.dtos.CreateOrderRequest;
import com.lcwd.electronicstore.dtos.OrderDto;

public interface OrderService {
	
	//create Order
	
	OrderDto createOrder(CreateOrderRequest orderDto);
	
	
	//Remove Order
	
	void removeOrder(String orderId);
	
	//Get Orders of User
	
	List<OrderDto> getOrderOfUser(String userId);
	
	
	//Get Orders
	
	List<OrderDto> getOrders();
	
	
	
	//Other Method related to Order
	
	

}
