package com.lcwd.electronicstore.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	@Qualifier("customUserDetailService") // Force Spring to use this specific bean
	private UserDetailsService userDetailsService;


	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String requestHeader = request.getHeader("Authorization");
		
		System.out.println(requestHeader);
		
		
		
		String username = null;
		
		String token = null;
		
		
		//Authorization : Bearer fnioqbfbdjskaksjfd
		if(requestHeader != null && requestHeader.startsWith("Bearer"))
		{
			//All ok
			token = requestHeader.substring(7);
			
			try {
				username = jwtHelper.getUsernameFromToken(token);
				System.out.println("Token Usernmae"+ username);
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Illegal argument while fetching the expression!!");
			}
			catch(ExpiredJwtException ex)
			{
				System.out.println("Given Jwt is Expired"+ ex.getMessage());
			}
			catch(MalformedJwtException ex)
			{
				System.out.println("Some changes has done in token !! Invalid token"+ex.getMessage());
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else
		{
			System.out.println("Invalid Bearer");
		}
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			//authentication null
			UserDetails userDetails =userDetailsService.loadUserByUsername(username);
			
			//validate token
			if(username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token))
			{
				//token valid
				
				//set authentication inside security context
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			
		}
		
		filterChain.doFilter(request, response);
		
	}
	
	

}
