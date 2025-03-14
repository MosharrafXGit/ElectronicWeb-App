package com.lcwd.electronicstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lcwd.electronicstore.entities.RefreshToken;
import com.lcwd.electronicstore.entities.User;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer>{
	
	Optional<RefreshToken> findByToken(String token);
	Optional<RefreshToken> findByUser(User user);

}
