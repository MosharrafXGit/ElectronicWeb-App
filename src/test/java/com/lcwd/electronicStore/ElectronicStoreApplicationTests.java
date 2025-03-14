package com.lcwd.electronicStore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lcwd.electronicstore.ElectronicStoreApplication;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.repository.UserRepo;
import com.lcwd.electronicstore.security.JwtHelper;

//import com.lcwd.electronicstore.entities.User;
//import com.lcwd.electronicstore.repository.UserRepo;
//import com.lcwd.electronicstore.security.JwtHelper;

@SpringBootTest 

class ElectronicStoreApplicationTests {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private JwtHelper jwtHelper;

	@Test
	void contextLoads() {
	}

	@Test
    void testToken()
	{
		
	   User user =	userRepo.findByEmail("imam@gmail.com").get();
   
	   String token = jwtHelper.generateToken(user);
 		System.out.println(token);
 	System.out.println("TEsting");
	}
	
}
