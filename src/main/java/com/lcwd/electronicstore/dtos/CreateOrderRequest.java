package com.lcwd.electronicstore.dtos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CreateOrderRequest {

	@NotBlank(message = "Cart id is required")
	private String cartId;
	
	@NotBlank(message = "cart id is required")
	private String userId;
	
	@NotBlank(message = "cart id is required")
	private String orderStatus="PENDING";
	
	private String paymentStatus="NOTPAID";
	@NotBlank(message = "Address is required")
	private String billingAddress;
	@NotBlank(message = "Phone number is required")
	private String billingPhone;
	@NotBlank(message = "Billing number is required")
	private String billingName;


	

	
}
