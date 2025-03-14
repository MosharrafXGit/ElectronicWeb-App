package com.lcwd.electronicstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.Order;
import com.lcwd.electronicstore.entities.User;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {
	
	 List<Order> findByUser(User user);

}
