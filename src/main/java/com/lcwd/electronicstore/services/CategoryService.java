package com.lcwd.electronicstore.services;

import java.util.List;

import com.lcwd.electronicstore.dtos.CategoryDto;

public interface CategoryService {
	
	public CategoryDto create(CategoryDto categoryDto);
	
	public CategoryDto update(String categoryID,CategoryDto categoryDto);
	
	public void delete(String categoryId);
	
	
	public List<CategoryDto> getAll();
	
	public CategoryDto getCategoryById(String categoryId);
	
	//public List<CategoryDto> searchByTitle(String title);

}
