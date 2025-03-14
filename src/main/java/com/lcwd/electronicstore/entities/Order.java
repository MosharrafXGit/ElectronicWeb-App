package com.lcwd.electronicstore.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Entity
@Table(name ="orders")
@JsonIgnoreProperties({"orderItems"})
public class Order {
	
	@Id
	private String orderId;
	
	//Pending, Delivering , Delivered
	//enum
	private String orderStatus;
	
	//Not Paid , Paid
	//enum
	//boolean - false=>NOT-PAID true=>PAID
	private String paymentStatus;
	
	private int orderAmount;
	
	@Column(length = 1000)
	private String billingAddress;
	
	
	private String billingPhone;
	
	
	private String billingName;
	
	private Date orderedDate;
	
	private Date deliveredDate;
	
	@ManyToOne( fetch = FetchType.EAGER)
	@JoinColumn(name="user_id")
	private User user;
	
	
	@OneToMany(mappedBy = "order" , fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
	
	
	
	
	
	
	
	
	
	

}
