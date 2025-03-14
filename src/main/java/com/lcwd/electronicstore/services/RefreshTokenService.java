package com.lcwd.electronicstore.services;


import com.lcwd.electronicstore.dtos.RefreshTokenDto;
import com.lcwd.electronicstore.entities.RefreshToken;

public interface RefreshTokenService {
	
	//create token
	public RefreshTokenDto createRefreshToken(String username);
	
	
	
	
	//find token
	public RefreshTokenDto findByToken(String token);
	
	
	//verify
	public RefreshTokenDto verifyRefreshToken(RefreshTokenDto refreshTokenTokenDto);
	
	
	

}
