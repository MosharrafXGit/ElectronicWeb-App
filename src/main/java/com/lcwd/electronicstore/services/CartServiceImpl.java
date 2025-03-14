package com.lcwd.electronicstore.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.lcwd.electronicstore.dtos.AddItemToCartRequest;
import com.lcwd.electronicstore.dtos.CartDto;
import com.lcwd.electronicstore.entities.Cart;
import com.lcwd.electronicstore.entities.CartItem;
import com.lcwd.electronicstore.entities.Product;
import com.lcwd.electronicstore.entities.User;
import com.lcwd.electronicstore.exception.BadApiRequest;
import com.lcwd.electronicstore.exception.ResourceNotFoundException;
import com.lcwd.electronicstore.repository.CartItemRepo;
import com.lcwd.electronicstore.repository.CartRepo;
import com.lcwd.electronicstore.repository.ProductRepo;
import com.lcwd.electronicstore.repository.UserRepo;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	
		
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CartItemRepo cartItemRepo;
	
	
	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		
		int quantity = request.getQuantity();
		String productId = request.getProductId();
		

		//if cart items already present ; then update
		if(quantity<=0)
		{
			throw new BadApiRequest("Requested quantity is not valid");
		}
		
		//fetch the product
		Product product = productRepo.findById(productId).orElseThrow(()-> new ResourceNotFoundException());
		
		
		//fetch the product from db
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User Not found "));
		
		Cart cart = null;
		
		try {
			cart = cartRepo.findByUser(user).get();
		}
		catch(NoSuchElementException e)
		{
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString()); // Manually setting a unique cartId
			cart.setCreatedAt(new Date());
		}
		
		//perform cart operation
		
		
		
		AtomicReference<Boolean> updated = new AtomicReference<>(false);
		
		
		List<CartItem> items  = cart.getItems();
		
	   items = items.stream().map(item ->{
		
			if(item.getProduct().getProductId().equals(productId))
			{
				//item already present in cart
				item.setQuantity(quantity);
				item.setTotalPrice(quantity * product.getPrice());
				updated.set(true);
			}
			
			
			return item;
			
		}).collect(Collectors.toList());
	   
	   
	//   cart.setItems(updatedItems);
	   
	
		
	   
	   //create items
	   
	   if(!updated.get())
	   {

			CartItem cartItem =CartItem.builder()
				.quantity(quantity)
				.totalPrice(quantity*product.getPrice())
				.cart(cart)
				.product(product)
				.build();
			
			
			cart.getItems().add(cartItem);
	   }
	   
		cart.setUser(user);

		Cart updatedCart = cartRepo.save(cart);
		
		return modelMapper.map(updatedCart, CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userID, int cartItem) {

		//conditions
		
		
		CartItem cartItem1 = cartItemRepo.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart Item not found eception"));
		cartItemRepo.delete(cartItem1);
		
		
	}

	@Override
	public void clearCart(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException());
		Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
		cart.getItems().clear();
		cartRepo.save(cart);		
		
	}

	@Override
	public CartDto getCartByUser(String userId) {


		User user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException());
		Cart cart = cartRepo.findByUser(user).orElseThrow(()-> new ResourceNotFoundException("Cart not found"));
		

		return modelMapper.map(cart, CartDto.class);
	}

	

}
