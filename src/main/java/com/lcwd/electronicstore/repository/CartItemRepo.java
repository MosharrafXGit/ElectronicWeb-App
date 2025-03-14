package com.lcwd.electronicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.CartItem;


@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer>{
	
	

}
