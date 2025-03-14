package com.lcwd.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User,String>{
	
	//Custom Repository

	public Optional<User> findByEmail(String email);
	
	public Optional<User> findByEmailAndPassword(String email, String paassword );
//	
//	public Optional<User> findByNameContaining(String kerywords);
	
	
	
	

}
