package com.lcwd.electronicstore.dtos;

import org.springframework.http.HttpStatus;

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
public class ImageResponse {
	
	
	private String imageName;
	private String message;
	private boolean success;
	private HttpStatus status;
	

}
