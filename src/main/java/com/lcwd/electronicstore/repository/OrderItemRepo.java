package com.lcwd.electronicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.OrderItem;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem, Integer>{

}
