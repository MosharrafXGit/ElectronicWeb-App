package com.lcwd.electronicstore.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.electronicstore.dtos.ApiResponseMessage;
import com.lcwd.electronicstore.dtos.ProductDto;
import com.lcwd.electronicstore.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	//create
	@PostMapping("/create")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto product)
	{
	  ProductDto createdProduct=productService.create(product);
	  return new ResponseEntity<ProductDto>(createdProduct, HttpStatus.CREATED);
		
	}
	
	//update
	@PutMapping("/update/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId, @RequestBody ProductDto product )
	{
		ProductDto updateProduct= productService.update(product, productId);
		
		return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK);
		
	}
	
	//delete
	@DeleteMapping("/delete/{productId}")
	public ResponseEntity<ApiResponseMessage> deleteProduct(@PathVariable String productId)
	{
		productService.delete(productId);
		
		ApiResponseMessage message = ApiResponseMessage.builder()
										.message("Product Deleted Successfully")
										.success(true)
										.status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(message,HttpStatus.OK);
		
	}
	
	//getSingle
	@GetMapping("/getSingle/{productId}")
	public ResponseEntity<ProductDto> getSingleProduct(@PathVariable String productId)
	{
		ProductDto singleProduct= productService.getSingleProduct(productId);
		return new ResponseEntity<ProductDto>(singleProduct,HttpStatus.OK);
	}
	
	//getAll
	@GetMapping("/getAllProduct")
	public ResponseEntity<List<ProductDto>> getAllProduct()
	{
		 List<ProductDto> prodcuts= productService.getAll();
		 return new ResponseEntity<List<ProductDto>>(prodcuts,HttpStatus.OK);
	}
	
	//get all live
	@GetMapping("/getAllLive")
	public ResponseEntity<List<ProductDto>> getAallLive()
	{
		List<ProductDto> products = productService.getAllLive();
		return new ResponseEntity<List<ProductDto>>(products,HttpStatus.OK);
	}
	
	//search by Title
	@GetMapping("/searchByTitle/{query}")
	public ResponseEntity<List<ProductDto>> searchByTitle(@PathVariable String query)
	{
		 List<ProductDto> products = productService.searchByTitle(query);	
		 return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
	}
	
	

	
	
	
}
