package com.lcwd.electronicstore.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lcwd.electronicstore.entities.CartItem;
import com.lcwd.electronicstore.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	
	
	private String cartId;
	private Date createdAt;
	private UserDto user;
	private List<cartItemDto> items = new ArrayList<>();

}
