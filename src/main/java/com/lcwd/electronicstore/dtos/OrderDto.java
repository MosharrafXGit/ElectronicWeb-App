package com.lcwd.electronicstore.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lcwd.electronicstore.entities.OrderItem;
import com.lcwd.electronicstore.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class OrderDto {
	
	
		
		
		private String orderId;
		private String orderStatus="PENDING";
		private String paymentStatus="NOTPAID";
		private int orderAmount;
		private String billingAddress;
		private String billingPhone;
		private String billingName;
		private Date orderedDate;
		private Date deliveredDate;
		private UserDto userDto;
		
		
		private List<OrderItemDto> orderItems = new ArrayList<>();

}
