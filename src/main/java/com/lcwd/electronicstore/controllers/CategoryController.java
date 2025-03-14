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

import com.lcwd.electronicstore.dtos.CategoryDto;
import com.lcwd.electronicstore.dtos.ImageResponse;
import com.lcwd.electronicstore.dtos.ProductDto;
import com.lcwd.electronicstore.dtos.UserDto;
import com.lcwd.electronicstore.entities.Category;
import com.lcwd.electronicstore.services.CategoryService;
import com.lcwd.electronicstore.services.FileService;
import com.lcwd.electronicstore.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	
	@Autowired
	private FileService fileService;
	
//	@Value("${images/category/}")
//	private String imageUploadPath;
//	
	@PostMapping("/create")
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
	{
		CategoryDto categoryDto1= categoryService.create(categoryDto);
		
		return new ResponseEntity<CategoryDto> (categoryDto1,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@PathVariable String categoryId, @RequestBody CategoryDto categoryDto)
	{
		CategoryDto categoryDto1 =categoryService.update(categoryId, categoryDto);
		return new ResponseEntity<CategoryDto> (categoryDto1,HttpStatus.OK);
		
	}
	
	
	@DeleteMapping("/delete/{categoryId}")
	public ResponseEntity<String> deleteCategory (@PathVariable String categoryId)
	{
		
		categoryService.delete(categoryId);
		
		return new ResponseEntity<String>("Category Deleted Successfully",HttpStatus.OK);
		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAll()
	{
		List<CategoryDto> categoryDtos= categoryService.getAll();
		
		return new ResponseEntity<List<CategoryDto>> (categoryDtos,HttpStatus.OK);
	}
	
//	@GetMapping("/searchByTitle/{title}")
//	public ResponseEntity<CategoryDto> searchByTitle(@PathVariable String title)
//	{
//		CategoryDto categoryDto=categoryService.searchByTitle(title);
//		
//		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);
//	}
	
//	
//
//	@PostMapping("/image/{categoryId}")
//	public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage") MultipartFile image, @PathVariable String categoryId) throws IOException
//	{
//		String imageName = fileService.uploadFile(image, imageUploadPath);
//		
//		CategoryDto category = categoryService.getCategoryById(categoryId);
//		
//		category.setCoverImage(imageName);
//		
//		CategoryDto categoryDto = categoryService.update(categoryId, category);
//		
//		ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
//		
//		return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
//		
//	}
//	
//	
//	@GetMapping("/image/{categoryId}")
//	public void serveUserImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException
//	{
//		CategoryDto category =categoryService.getCategoryById(categoryId);
//		InputStream resource =fileService.getResource(imageUploadPath, category.getCoverImage());
//		
//		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
//		org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
//	}
//	

	
	// CREATE PRODUCT WITH CATEGORY
	@PostMapping("/{categoryId}/product")
	public ResponseEntity<ProductDto> createProductWithCategory(
			@PathVariable("categoryId") String categoryId,
			@RequestBody ProductDto dto
			)
	{
		
		ProductDto productWithCategory=productService.createWithCategory(dto, categoryId);
		return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
		
	}
	
	
	//Update Category of product
	@PutMapping("/{categoryId}/product/{productId}")
	public ResponseEntity<ProductDto> updateCategoryOfProduct(
			@PathVariable String categoryId,
			@PathVariable String productId
){
		
		ProductDto productDto = productService.updateCategory(productId, categoryId);
		
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
	}
	
	
	//Get Product of Categories
	@GetMapping("/{categoryId}/product")
	public ResponseEntity<List<ProductDto>> getProductOfCategory(
			@PathVariable String categoryId
			)
	{
		List<ProductDto> response = productService.getAllOfCategory(categoryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
}
