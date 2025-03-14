package com.lcwd.electronicstore.services;

import java.time.Instant;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.lcwd.electronicstore.dtos.RefreshTokenDto;
import com.lcwd.electronicstore.entities.RefreshToken;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.RefreshTokenRepo;
import com.lcwd.electronicstore.repository.UserRepo;

@Service
public class RefreshTokenServiecImpl implements RefreshTokenService{
	
	
	private UserRepo userRepo;
	
	private RefreshTokenRepo refreshTokenRepo;
	
	private ModelMapper modelMapper;
	
	
	  // No-argument constructor
    public RefreshTokenServiecImpl() {
        // Initialization code, if necessary
    }
	
	

	public RefreshTokenServiecImpl(ModelMapper modelMapper) {
		super();
		this.modelMapper = modelMapper;
	}

	public RefreshTokenServiecImpl(UserRepo userRepo, RefreshTokenRepo refreshTokenRepo) {
		super();
		this.userRepo = userRepo;
		this.refreshTokenRepo = refreshTokenRepo;
	}

	@Override
	public RefreshTokenDto createRefreshToken(String username) {
		 
		User user =userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException());
		
		 RefreshToken refreshToken =RefreshToken.builder()
				 .user(user)
				 .token(UUID.randomUUID().toString())
				 .expirayDate(Instant.now())
				 .build();
		 
		RefreshToken saved = refreshTokenRepo.save(refreshToken);
		return this.modelMapper.map(saved, RefreshTokenDto.class);
	}

	@Override
	public RefreshTokenDto findByToken(String token) {
		
    RefreshToken refreshToken = refreshTokenRepo.findByToken(token).orElseThrow(()-> new ResourceNotFoundException("Token Not found in DB"));

		return this.modelMapper.map(refreshToken, RefreshTokenDto.class);
	}

	@Override
	public RefreshTokenDto verifyRefreshToken(RefreshTokenDto token)  {

		var refreshToken = modelMapper.map(token, RefreshToken.class);
		
		if(token.getExpirayDate().compareTo(Instant.now())<0)
		{
			refreshTokenRepo.delete(refreshToken);
			throw new RuntimeException("Refresh Token Expired");
		}
		
		return token;
	}

	
	
	

}
