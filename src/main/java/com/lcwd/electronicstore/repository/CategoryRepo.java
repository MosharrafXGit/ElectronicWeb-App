package com.lcwd.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.Category;



@Repository
public interface CategoryRepo extends JpaRepository<Category, String >{
	
	
	public Optional<Category> findByTitle(String Keywords);
	

}
