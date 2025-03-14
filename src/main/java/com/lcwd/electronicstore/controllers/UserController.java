package com.lcwd.electronicstore.controllers;



import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lcwd.electronicstore.dtos.ApiResponseMessage;
import com.lcwd.electronicstore.dtos.ImageResponse;
import com.lcwd.electronicstore.dtos.UserDto;
import com.lcwd.electronicstore.services.FileService;
import com.lcwd.electronicstore.services.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${user.profile.image.path}")
	private String imageUploadPath;
	
	@PostMapping("/create")
	public ResponseEntity<UserDto>  createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto userDto1 =userService.createUser(userDto);
		return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId)
	{
		UserDto updatedUserDto = userService.updateUser( userId , userDto);
		
		return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
		
	}
	
	
//	@DeleteMapping("/delete/{userId}")
//	public ResponseEntity<String> deleteUser(@PathVariable String userId)
//	{
//		userService.deleteUser(userId);
//		return new ResponseEntity<>("User Deleted Succesfully",HttpStatus.OK);
//	}
	
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
	{
		userService.deleteUser(userId);
		ApiResponseMessage message=ApiResponseMessage.builder()
				.message("User Deleted Succesfully!!")
				.success(true)
				.status(HttpStatus.OK)
				.build();
		return new ResponseEntity<>(message,HttpStatus.OK);
	}
	
	@GetMapping("/allUsers")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
	}
	
	@GetMapping("/singleUser/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable String userId)
	{
		
		//return new ResponseEntity<UserDto> (userService.getUserByID(userId),HttpStatus.OK);
		UserDto user =userService.getUserByID(userId);
		return new ResponseEntity<UserDto> (user, HttpStatus.OK);
	}
	
	
	@GetMapping("/findByEmail/{email}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String email)
	{
		UserDto userDto = userService.getUserByEmail(email);
		return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
	}
	
	@GetMapping("/findByEmailAndPassword/{email}/{password}")
	public ResponseEntity<UserDto> findByEmailName(@PathVariable String email, @PathVariable String password)
	{
		UserDto userDto = userService.findByNameAndPassword(email, password);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
		
	}
	
	@PostMapping("/image/{userId}")
	public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage") MultipartFile image, @PathVariable String userId) throws IOException
	{
		String imageName = fileService.uploadFile(image, imageUploadPath);
		
		UserDto user = userService.getUserByID(userId);
		
		user.setImageName(imageName);
		
		UserDto userDto = userService.updateUser(userId, user);
		
		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
		
		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/image/{userId}")
	public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException
	{
		UserDto user =userService.getUserByID(userId);
		InputStream resource =fileService.getResource(imageUploadPath, user.getImageName());
		
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
	}
	

}
