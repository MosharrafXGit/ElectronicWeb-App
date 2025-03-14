package com.lcwd.electronicstore.services;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Optional;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lcwd.electronicstore.dtos.CategoryDto;
import com.lcwd.electronicstore.dtos.ProductDto;
import com.lcwd.electronicstore.entities.Category;
import com.lcwd.electronicstore.repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
//	
//	@Value("${images/category/}")
//	private String imageUploadPath;

	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		
		String categoryId = UUID.randomUUID().toString();
		
		categoryDto.setCategoryId(categoryId);
		
		Category category = mapper.map(categoryDto, Category.class);
		
		Category savedCategory = categoryRepo.save(category);
		
		CategoryDto categoryDto1 = mapper.map(savedCategory, CategoryDto.class);
		
		
		return categoryDto1;
	}

	@Override
	public CategoryDto update(String categoryID, CategoryDto categoryDto) {
		
	
		
		Category category = categoryRepo.findById(categoryID).orElseThrow(()->new RuntimeException());
		
		category.setDescription(categoryDto.getDescription());
		category.setTitle(categoryDto.getTitle());
		category.setCoverImage(categoryDto.getCoverImage());

		Category savedCategory = categoryRepo.save(category);
		
		CategoryDto newCategoryDto = mapper.map(savedCategory, CategoryDto.class);
		
		
		return newCategoryDto;
	}

	@Override
	public void delete(String categoryId) {
		
		Category category =  categoryRepo.findById(categoryId).orElseThrow(()-> new RuntimeException());
		
		categoryRepo.delete(category);
		
	}

	@Override
	public List<CategoryDto> getAll() {
		
		
		List<Category> category = categoryRepo.findAll();
		
		List<CategoryDto> categoryDto = category.stream().map(singleCategory-> mapper.map(singleCategory, CategoryDto.class)).collect(Collectors.toList());
		
		
		return categoryDto;
	}

	@Override
	public CategoryDto getCategoryById(String categoryId) {
		
		Category category =categoryRepo.findById(categoryId).orElseThrow(()-> new RuntimeException());
	
		return mapper.map(category, CategoryDto.class);
		
		//Chat GPT
		
		

	}
//
//	@Override
//	public List<CategoryDto> searchByTitle(String title) {
//		
//		List<Category> category = categoryRepo.findByTitle(title).orElseThrow(()-> new RuntimeException());
//		
//		List<Category> categoryDtos= category.stream().map(singleCategory -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());
//		
//		return categoryDtos;
//	}

}
