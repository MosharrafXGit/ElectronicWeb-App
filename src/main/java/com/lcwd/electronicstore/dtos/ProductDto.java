package com.lcwd.electronicstore.dtos;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
	
	
	String productId;
	String title;
	private String description;
	private int price;
	private int discountedPrice;
	private int quantity;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date addedDate;
	private boolean live;
	private boolean stock;
	
	private CategoryDto category;
	

}
