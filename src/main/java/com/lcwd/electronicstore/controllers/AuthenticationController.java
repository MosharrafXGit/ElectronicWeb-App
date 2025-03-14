package com.lcwd.electronicstore.controllers;

import org.apache.catalina.connector.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.electronicstore.dtos.JwtRequest;
import com.lcwd.electronicstore.dtos.JwtResponse;
import com.lcwd.electronicstore.dtos.RefreshTokenDto;
import com.lcwd.electronicstore.dtos.UserDto;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.security.JwtHelper;
import com.lcwd.electronicstore.services.RefreshTokenService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	
	//Metod to generate token
	@PostMapping("/generate-token")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)
	{
		//System.out.println("username{}, password{}"+ request.getUsername()+""+ request.getPassword());
		
		this.doAuthneticate(request.getEmail(),request.getPassword());
		
		User user = (User) userDetailsService.loadUserByUsername(request.getEmail());
		//.. generate token
		String token = jwtHelper.generateToken(user);
		
		RefreshTokenDto refreshToken = refreshTokenService.createRefreshToken(user.getEmail());
		
		JwtResponse jwtResponse = JwtResponse.builder()
				.token(token)
				.user(modelMapper.map(user, UserDto.class))
				.refreshToken(refreshToken)
				.build();
		
		return ResponseEntity.ok(jwtResponse);
		
	}

	private void doAuthneticate(String email, String password) {
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(email, password)
	        );
	    } catch (BadCredentialsException ex) {
	        throw new RuntimeException("Invalid username or password!");
	    } catch (Exception ex) {
	        throw new RuntimeException("Authentication failed: " + ex.getMessage());
	    }
	}


}
