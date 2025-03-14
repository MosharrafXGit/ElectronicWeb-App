package com.lcwd.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role,String>{
	
	Optional<Role> findByName(String name);

}
