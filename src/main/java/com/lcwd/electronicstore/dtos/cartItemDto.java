package com.lcwd.electronicstore.dtos;


import com.lcwd.electronicstore.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class cartItemDto {
	
	
	private int cartItemId;
	
	private ProductDto product;
	
	private int quantity;
	
	private int totalPrice;
	
	

}
