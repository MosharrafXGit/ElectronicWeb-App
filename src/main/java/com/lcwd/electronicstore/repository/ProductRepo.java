package com.lcwd.electronicstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.Category;
import com.lcwd.electronicstore.entities.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
	
	//search
	
	List<Product> findByTitleContaining(String subTitle);
	
	List<Product> findByLive(boolean live);
	
	List<Product> findByCategory(Category category);

}
