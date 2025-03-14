package com.lcwd.electronicstore.dtos;


import java.util.List;

import com.lcwd.electronicstore.entities.Role;
import com.lcwd.electronicstore.validate.ImageNameValid;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	
	
	private String userId;
	
	@Size(min=3,max=10,message ="Invalid userName")
	private String userName;
	
	
	@Pattern(regexp ="^[a-zA-Z0-9._%±]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,}$" , message ="Invalid Email")
	@NotBlank(message ="Not Valid") 
	@Email(message = "Invalid User Email")
	private String email;
	
	@NotBlank(message = "Password Required")
	private String password; 
	
	@Size(min=3,max =6,message ="Invalid Gender")
	private String gender;
	
	@NotBlank(message = "Write Something about yourself")
	private String about;
	
	@ImageNameValid
	private String imageName;
	
	
	private List<RoleDto> roles;

}
