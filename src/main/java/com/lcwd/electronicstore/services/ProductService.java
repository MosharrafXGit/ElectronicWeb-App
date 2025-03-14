package com.lcwd.electronicstore.services;

import java.util.List;

import com.lcwd.electronicstore.dtos.ProductDto;
import com.lcwd.electronicstore.entities.Product;

public interface ProductService {
	
	//create
	public ProductDto create(ProductDto productDto);
	
	//update
	public ProductDto update(ProductDto productDto, String productId);
	
	//delete
	public void delete(String productId);
	
	//getSingle
	public ProductDto getSingleProduct(String ProductId);
	
	//getAll
	public List<ProductDto> getAll();
	
	//getAll Live
	public List<ProductDto> getAllLive();
	
	
	//SearchProduct
	List<ProductDto> searchByTitle(String subTitle);

	
	// create product with category
	public ProductDto createWithCategory(ProductDto productDto, String categoryId);
	
	
	//Update category of Dto
	public ProductDto updateCategory(String productId, String categoryId);
	
	
	public List<ProductDto> getAllOfCategory(String categoryId);
	

}
