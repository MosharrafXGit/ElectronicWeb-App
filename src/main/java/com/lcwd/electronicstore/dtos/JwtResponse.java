package com.lcwd.electronicstore.dtos;

import com.lcwd.electronicstore.entities.RefreshToken;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
	
	private String token;
	
	UserDto user;
	
	//private String jwtToken;
	
	
	private RefreshTokenDto refreshToken;

}
