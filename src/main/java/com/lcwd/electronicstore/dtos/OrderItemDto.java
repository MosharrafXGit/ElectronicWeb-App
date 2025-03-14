package com.lcwd.electronicstore.dtos;

import com.lcwd.electronicstore.entities.Order;
import com.lcwd.electronicstore.entities.Product;

import jakarta.persistence.Entity;
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
public class OrderItemDto {
	
	
	private int orderItem;
	private int quantity;
	private int totalPrice;
	private Product product;
	private Order order;

}
