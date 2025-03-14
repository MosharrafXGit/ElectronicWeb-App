package com.lcwd.electronicstore.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.electronicstore.dtos.CreateOrderRequest;
import com.lcwd.electronicstore.dtos.OrderDto;
import com.lcwd.electronicstore.entities.Cart;
import com.lcwd.electronicstore.entities.CartItem;
import com.lcwd.electronicstore.entities.Order;
import com.lcwd.electronicstore.entities.OrderItem;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.exception.BadApiRequest;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.CartRepo;
import com.lcwd.electronicstore.repository.OrderRepo;
import com.lcwd.electronicstore.repository.UserRepo;

@Service
public  class OrderServiceImpl implements OrderService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private OrderRepo orderRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Autowired
	private CartRepo cartRepo;
	
	
	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto) {
		
		String userId= orderDto.getUserId();
		String cartId= orderDto.getCartId();
		
		//fetch by User
		User user =userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not Found Exception"));
		
		//fetch by Cart
		Cart cart =cartRepo.findById(cartId).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found Exception"));
		
		
		 List<CartItem> cartItems=cart.getItems();
		 
		 if(cartItems.isEmpty())
		 {
			 throw new BadApiRequest("Invalid Number of items in cart!!");
		 }
			 
		 
		Order order = Order.builder()
		 			.billingName(orderDto.getBillingName())
		 			.billingPhone(orderDto.getBillingPhone())
		 			.billingAddress(orderDto.getBillingAddress())
		 			.orderedDate(new Date())
		 			.deliveredDate(new Date())
		 			.paymentStatus(orderDto.getPaymentStatus())
		 			.orderStatus(orderDto.getOrderStatus())
		 			.orderId(UUID.randomUUID().toString())
		 			.user(user)
		 			.build();
		 
		 
		 //orderItems,amount
		 
		AtomicReference<Integer> orderAmount =new AtomicReference<>(0);
		
		List<OrderItem> orderItems = cartItems.stream().map(cartItem -> 
		{
		 
		OrderItem orderItem = OrderItem.builder()
			.quantity(cartItem.getQuantity())
			.product(cartItem.getProduct())
			.totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
			.order(order)
			.build();
		 
		orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
		 
		return orderItem;
		}).collect(Collectors.toList());
		
		order.setOrderItems(orderItems);
		order.setOrderAmount(order.getOrderAmount());
		
		//Clear Cart
		
		cart.getItems().clear();
		
		cartRepo.save(cart);
		Order savedOrder = orderRepo.save(order);
		
		
		return modelMapper.map(savedOrder, OrderDto.class);
		
	}
	

	@Override
	public void removeOrder(String orderId) {
		
		Order order=orderRepo.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("User Not found"));
		
		orderRepo.delete(order);
		
	}

	@Override
	public List<OrderDto> getOrderOfUser(String userId) {

		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
		List<Order> orders = orderRepo.findByUser(user);
		List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
		return orderDtos;
	}

	@Override
	public List<OrderDto> getOrders() {

		List<Order> orders =orderRepo.findAll();
		List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(orders, OrderDto.class)).collect(Collectors.toList());
		return orderDtos;
	}
	
	

}
