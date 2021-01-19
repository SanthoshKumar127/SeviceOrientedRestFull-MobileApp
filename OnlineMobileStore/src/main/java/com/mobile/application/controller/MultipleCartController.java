
package com.mobile.application.controller;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobile.application.dto.CartDto;
import com.mobile.application.dto.ItemDto;
import com.mobile.application.exception.CartNotfoundException;
import com.mobile.application.exception.ItemNotfoundException;
import com.mobile.application.exception.StockOverException;
import com.mobile.application.exception.UserNotfoundException;
import com.mobile.application.model.Cart;
import com.mobile.application.model.Item;
import com.mobile.application.model.User;
import com.mobile.application.repository.CartRepository;
import com.mobile.application.repository.ItemRepository;
import com.mobile.application.repository.UserServiceImpl;

@Controller
@RequestMapping("/User")
@ResponseBody
public class MultipleCartController {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private UserServiceImpl userRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Value("${item_size}")
	private int size;
	Logger log = LoggerFactory.getLogger(AccessoriesController.class);

	/**
	 * maps particular Item
	 * 
	 * @param modelType
	 * @return
	 */

//Using Model Mapper
	@GetMapping(value = "/addcart/{modelType}")
	public ItemDto getData1(@PathVariable("modelType") int modelType) {

		Item itemDetails = itemRepository.findByModel(modelType);
		if (Objects.isNull(itemDetails)) {
			log.warn("Enter correct model number");
			log.error("error found in addCart");	
			throw new ItemNotfoundException("model: " + modelType);
		}
		log.info("MultipleCartController getData1() response{}", itemDetails);

		ItemDto dta = modelMapper.map(itemDetails, ItemDto.class);
		return dta;

	}

	/**
	 * @param cart
	 * @param page
	 * @return
	 */
	@PostMapping(value = "/saveCart")
	public Page<CartDto> saveToCart(@RequestBody CartDto cartDto,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";

		Cart cart = modelMapper.map(cartDto, Cart.class);
		Item itemDetails = itemRepository.findByModel(cart.getModel());
		if (Objects.isNull(itemDetails)) {
			log.warn("Cart Data Incorect for saveCart");
			throw new ItemNotfoundException("model: " + cart.getModel());
		}
		Page<Cart> newCart = null;
		if (itemDetails.getQuantity_available() < cart.getQuantity()) {
			log.error("error found in SaveCart");			
			throw new StockOverException("model: " + cart.getModel());
		}
		User userList = userRepository.findById(cart.getId());
		if (Objects.isNull(userList)) {
			log.error("User Data Incorect for saveCart");
			throw new UserNotfoundException("user id: " + cart.getId());
		}
		Pageable pageable = PageRequest.of(pageNumber, 15, Sort.by("cartid").descending());
		cartRepository.save(cart);
		newCart = cartRepository.findAllById(cart.getId(), pageable);
		log.info("MultipleCartController saveToCart() response{}", newCart);

		if (Objects.isNull(newCart)) {
			log.error("Error while saveCart");
			
			throw new CartNotfoundException("No cart Found");
		}
		return newCart.map(product -> {
			return modelMapper.map(product, CartDto.class);
		});
	}

	/**
	 * All cart values of particular user
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getcart/{id}")
	public Page<CartDto> getCartAll(@PathVariable Integer id,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";
		Page<Cart> cart = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		if (Objects.isNull(id)) {
			log.warn("Cart Data Incorect for getCart");
			throw new UserNotfoundException("user id: " + id);
		}
		cart = cartRepository.findAllById(id, pageable);
		log.info("MultipleCartController getCartAll() response{}", cart);

		if (Objects.isNull(cart)) {
			log.error("Error found in get cart");		
			throw new CartNotfoundException("Cart Not found");
		}
		return cart.map(product -> {
			return modelMapper.map(product, CartDto.class);
		});
	}

	/**
	 * Removing user desired product from cart
	 * 
	 * @param id
	 * @param cartid
	 * @return
	 */

	@DeleteMapping("/remove/{id}/{cartid}")
	public Page<CartDto> remove(@PathVariable Integer id, @PathVariable("cartid") Integer cartid,
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "sortBy", required = false) String sort) {
		if (Objects.isNull(pageNumber))
			pageNumber = 0;
		if (Objects.isNull(size))
			size = 50;
		if (Objects.isNull(sort))
			sort = "cartid";
		Page<Cart> cartDetails = null;
		Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(sort).descending());
		if (id == null || cartid == null) {
			log.warn("Cart Data Incorect for remove cart");
			throw new CartNotfoundException("id or cartid is not correct");}
		cartRepository.deleteById(cartid);
		cartDetails = cartRepository.findAllById(id, pageable);
		log.info("MultipleCartController remove() response{}", cartDetails);

		if (Objects.isNull(cartDetails)) {
			log.error("Error found in remove cart");
			throw new CartNotfoundException("No cart Found");
		}
		return cartDetails.map(product -> {
			return modelMapper.map(product, CartDto.class);
		});
	}
}