package com.lcwd.electronicstore.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JwtHelper {

    // Token validity: 5 hours
    public static final long TOKEN_VALIDITY = 5 * 60 * 60 * 1000;

    // Secret key (should be stored securely, not hardcoded)
    private static final String SECRET_KEY = "kjcndijjkdvjbvjkdbvjfjbvjskbkjvfdjfbbvfkasjjasbjkv";



    // Extract Username from Token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    // Extract Claims using a resolver
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

 // Retrieve All Claims from JWT
  
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()  // Correct method
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    // Check if Token is Valid
    public boolean validateToken(String token, String username) {
        final String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if Token is Expired
    public boolean isTokenExpired(String token) {
        final Date  expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
		
		return getClaimFromToken(token, Claims::getExpiration);
	}

//	// Convert Secret Key to Key Object
//    private SecretKey getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
    
    public String generateToken(UserDetails userDetails)
    {
    	Map<String, Object> claims = new HashMap<>();
    	return doGenerateToken(claims, userDetails.getUsername());
    	
    }

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		
		return 
				Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY ))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
		
	}
}
