package com.lcwd.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.Cart;
import com.lcwd.electronicstore.entities.User;


@Repository
public interface CartRepo extends JpaRepository<Cart, String>{
	
	Optional<Cart>  findByUser(User user);

}
