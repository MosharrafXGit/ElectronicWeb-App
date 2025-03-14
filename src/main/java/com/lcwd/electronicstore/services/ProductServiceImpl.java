package com.lcwd.electronicstore.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lcwd.electronicstore.dtos.CategoryDto;
import com.lcwd.electronicstore.dtos.ProductDto;
import com.lcwd.electronicstore.entities.Category;
import com.lcwd.electronicstore.entities.Product;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.CategoryRepo;
import com.lcwd.electronicstore.repository.ProductRepo;


@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public ProductDto create(ProductDto productDto) {
		
		String productId =UUID.randomUUID().toString();
		
		productDto.setProductId(productId);
		
	
		
		Product product = modelMapper.map(productDto,Product.class );
		
		product.setAddedDate(new Date());
		
		Product savedProduct = productRepo.save(product);
		
		
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public ProductDto update(ProductDto productDto, String productId) {
		
		//fetch the product of given id
		Product product = productRepo.findById(productId).orElseThrow(()-> new RuntimeException());
		
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		product.setDiscountedPrice(productDto.getDiscountedPrice());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());
		
		Product savedProduct = productRepo.save(product);

		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public void delete(String productId) {

		Product product = productRepo.findById(productId).orElseThrow(()-> new RuntimeException());
		
		productRepo.delete(product);
		
	}

	@Override
	public ProductDto getSingleProduct(String productId) {

		Product product = productRepo.findById(productId).orElseThrow(()-> new RuntimeException());
		
		
		return modelMapper.map(product, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAll() {

		List<Product> products = productRepo.findAll();
		
		List<ProductDto> productDto = products.stream().map(singleProduct -> modelMapper.map(singleProduct, ProductDto.class)).collect(Collectors.toList());
		
		return productDto;
	}

	@Override
	public List<ProductDto> getAllLive() {
		
		List<Product> products = productRepo.findByLive(true);
		List<ProductDto> productDto = products.stream().map(singleProduct -> modelMapper.map(singleProduct, ProductDto.class)).collect(Collectors.toList());
		return productDto;
	}

	@Override
	public List<ProductDto> searchByTitle(String subTitle) {
		
		List<Product> products = productRepo.findByTitleContaining(subTitle);
		List<ProductDto> productDto = products.stream().map(singleProduct -> modelMapper.map(singleProduct, ProductDto.class)).collect(Collectors.toList());
		
		return productDto;
		
		

	}

	@Override
	public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
		
		//fetch  the category from db
		Category category=categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category Not found"));
		
		//product id
		String productId =UUID.randomUUID().toString();
		productDto.setProductId(productId);
		
		Product product = modelMapper.map(productDto, Product.class);
		
		
		
		
		//added
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product savedProduct = productRepo.save(product);
		
		
		return modelMapper.map(savedProduct, ProductDto.class);
		
		
	}

	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		
		//product fetch operation
		
		Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product of given I dnot found"));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category of given Id not found"));		
		product.setCategory(category);
		Product savedProduct=productRepo.save(product);
		
		return modelMapper.map(savedProduct, ProductDto.class);
	}

	@Override
	public List<ProductDto> getAllOfCategory(String categoryId) {
		
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category of given Id not found"));		
		List<Product> product = productRepo.findByCategory(category);
		
		List <ProductDto> productDto =product.stream()
				.map(singleProduct -> modelMapper.map(singleProduct, ProductDto.class))
				.collect(Collectors.toList());
		
		return productDto;
	}



}
