package com.lcwd.electronicstore.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class CategoryDto {
	
	
	private String categoryId;
	
	@Size(min=3, max=15)
	private String title;
	
	@Size(min=5, max=30)
	private String description;
	
	@NotBlank(message = "Cover Image Required")
	private String coverImage;
	

}
