package com.lcwd.electronicstore.services;

import java.util.List;

import com.lcwd.electronicstore.dtos.UserDto;
import com.lcwd.electronicstore.entities.User;

public interface UserService {
	
	//create
	
	public UserDto createUser(UserDto userDto);
	
	//update
	
	public UserDto updateUser(String userId,UserDto userDto);
	
	
	//delete
	
	public void deleteUser(String userId);
	
	//getAllUser
	
	public List<UserDto> getAllUser();
	
	//getUserById
	
	public UserDto getUserByID(String usreID);
	
	//getUserByEmail
	
	public UserDto getUserByEmail(String email);
	
	//Find By Name And Password
	
	public UserDto findByNameAndPassword(String Email, String password);
	
	

}
